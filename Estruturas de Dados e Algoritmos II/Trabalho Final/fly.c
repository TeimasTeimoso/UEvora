#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include<stdbool.h>
#include "djikstra.h"

#define FILEAEROPORTOS "dbAeroportos.bin"
#define FILEVOOS "dbVoos.bin"

//Estrurura auxuiliar para escrever os aeroportos em disco
typedef struct AeroportoDisco
{
    char aeroporto[CODEMAX];
    short gmtH;
    short gmtM;
}AeroportoDisco;


//Função que abre o ficheiro
FILE *openFile(char *nome)
{
     //Abre a file
    FILE *file = fopen(nome, "r+b");

    if(file != NULL)
        return file;

    //caso nao exista, cria
    file = fopen(nome, "w+b");
    int z = 0;
    fwrite(&z, sizeof(int), 1, file);
    return file;
}


//Função que fecha um ficheiro
void close_file(FILE *file)
{
    fclose(file);
}


//Função que escreve um aeropordo em memória secundária
void escreverAeroportoDisco(FILE *file, char aeroporto[CODEMAX], short gmtH, short gmtM, int nAeroportos)
{
    fseek(file, 0, SEEK_SET);
    fwrite(&nAeroportos, sizeof(int), 1, file);
    AeroportoDisco *temp = malloc(sizeof(AeroportoDisco));
    strcpy(temp->aeroporto, aeroporto);
    temp->gmtH = gmtH;
    temp->gmtM = gmtM;

    fseek(file, sizeof(int) + (nAeroportos-1)*sizeof(AeroportoDisco), SEEK_SET);
    fwrite(temp, sizeof(AeroportoDisco), 1, file);

    free(temp);
}


//Função que lê todos os Aeroportos que existem em memória secundária e os coloca em memória principal
void lerAeroportosDisco(FILE *file, HashAeroportos *hA)
{
    fseek(file, 0, SEEK_SET);
    int nA;
    fread(&nA, sizeof(int), 1, file);

    AeroportoDisco *in = malloc(sizeof(AeroportoDisco));
    for (int i = 0; i < nA; i++)
    {
        fread(in, sizeof(AeroportoDisco), 1, file);
        insertAeroporto(hA, in->aeroporto, in->gmtH, in->gmtM);
    }
    free(in);
}


//Função que mete todos os voos que existem no final do programa e os coloca em memória secundária
void escreverVoosDisco(FILE *file, HTVoos *hV)
{
    fseek(file, 0, SEEK_SET);
    fwrite(&hV->numeroVoo, sizeof(int), 1, file);
    int nV = hV->numeroVoo;

    for (int i = 0; i < MAXVOOS; i++)
    {
        if (nV == 0)
        {
            return;
        }
        
        if (hV->array[i] != NULL)
        {
            fwrite(hV->array[i], sizeof(VooHash), 1, file);
            nV --;
        }
    }
}


//Função que lê todos os voos que existem em memória secundária e mete em memória principal
void lerVoosDisco(FILE *file, HashAeroportos *hA, HTVoos *hV)
{
    fseek(file, 0, SEEK_SET);
    int nV;
    fread(&nV, sizeof(int), 1, file);
    VooHash *temp = malloc(sizeof(VooHash));
    for (int i = 0; i < nV; i++)
    {
        fread(temp, sizeof(VooHash), 1, file);
        insertVoo(hV, temp->codigo, temp->partida, temp->destino, temp->horas, temp->minutos, temp->duracao);
        addVoo(findAeroporto(hA, temp->partida)->lista, temp->codigo);
    }
    free(temp);

}

int main(void)
{   

    Aux *aux = malloc(sizeof(Aux));
    aux->ocupacao = 0;

    HashAeroportos *hA = criarHashAeroportos();
    HTVoos *hV = criarHTVoos();

    FILE *fileA = openFile(FILEAEROPORTOS);
    FILE *fileV = openFile(FILEVOOS);

    lerAeroportosDisco(fileA, hA);
    lerVoosDisco(fileV, hA, hV);


    char operacao[2];
    while(scanf("%s", operacao) != EOF)
    {
        //Add Aeroporto
        if(strcmp(operacao, "AI") == 0)
        {
            char aeroporto[CODEMAX];
            short gmtH, gmtM;
            scanf("%s %hd:%hd", aeroporto, &gmtH, &gmtM);
            if(findAeroporto(hA, aeroporto) == NULL)
            {
                insertAeroporto(hA, aeroporto, gmtH, gmtM);
                printf("+ novo aeroporto %s\n", aeroporto);
                escreverAeroportoDisco(fileA, aeroporto, gmtH, gmtM, hA->numeroAeroportos);
            }
            else
            {
                printf("+ aeroporto %s existe\n", aeroporto);
            }
            
        }
        //Add Voo
        else if(strcmp(operacao, "FI") == 0)
        {
            char codigoVoo[CODEVOO];
            char aeroportoPartida[CODEMAX];
            char aeroportoDestino[CODEMAX];
            short horas;
            short minutos;
            short duracao;
            scanf("%s %s %s %hd:%hd %hd", codigoVoo, aeroportoPartida, aeroportoDestino, &horas, &minutos, &duracao);
            if (findVoo(hV, codigoVoo) == NULL)
            {
                if (findAeroporto(hA, aeroportoPartida)!= NULL)
                {
                    if (findAeroporto(hA, aeroportoDestino)!= NULL)
                    {
                        insertVoo(hV, codigoVoo, aeroportoPartida, aeroportoDestino, horas, minutos, duracao);
                        addVoo(findAeroporto(hA, aeroportoPartida)->lista, codigoVoo);
                        printf("+ novo voo %s\n", codigoVoo);
                    }
                    else
                    {
                        printf("+ aeroporto %s desconhecido\n", aeroportoDestino);
                    }
                }
                else
                {
                    printf("+ aeroporto %s desconhecido\n", aeroportoPartida);
                }
                
            }
            else
            {
                printf("+ voo %s existe\n", codigoVoo);
            }
        }
        //Remoção de voo
        else if(strcmp(operacao, "FD") == 0)
        {
            char codigoVoo[CODEVOO];
            scanf("%s", codigoVoo);
            if (findVoo(hV, codigoVoo) != NULL)
            {
                VooHash *rmAeroporto = findVoo(hV, codigoVoo);
                listaVoos * lista = findAeroporto(hA, rmAeroporto->partida)->lista;
                delVoo(lista, codigoVoo);
                removeVoo(hV, codigoVoo);
                printf("+ voo %s removido\n", codigoVoo);
            }
            else
            {
                printf("+ voo %s inexistente\n", codigoVoo);
            }
        }

        //Procura caminho
        else if(strcmp(operacao, "TR") == 0)
        {
            char AeroportoPartida[CODEMAX];
            char AeroportoDestino[CODEMAX];
            short horas;
            short minutos;
            scanf("%s %s %hd:%hd", AeroportoPartida, AeroportoDestino, &horas, &minutos);
            Aeroporto *partida = findAeroporto(hA, AeroportoPartida);
            Aeroporto *destino = findAeroporto(hA, AeroportoDestino);
            if (partida != NULL)
            {
                if (findAeroporto(hA, AeroportoDestino) != NULL)
                {
                    aux->array[aux->ocupacao] = partida;
                    aux->ocupacao ++;
                    dijkstra(hV, hA, AeroportoPartida, AeroportoDestino, horas, minutos, aux);
                    
                    if (destino->predecessor == NULL)
                    {
                        printf("+ sem voos de %s para %s\n", AeroportoPartida, AeroportoDestino);
                    }
                    else
                    {
                        printf("Voo    De   Para Parte Chega\n");
                        printf("====== ==== ==== ===== =====\n");
                        printVoos(findAeroporto(hA, AeroportoDestino), hV);
                        printf("Tempo de viagem: %d minutos\n", destino->distance);
                    }
                    resetAux(aux);
                }
                else
                {
                    printf("+ aeroporto %s desconhecido\n", AeroportoDestino);
                }
            }
            else
            {
                printf("+ aeroporto %s desconhecido\n", AeroportoPartida);
            }
        }
        else if(strcmp(operacao, "X") == 0)
        {
            escreverVoosDisco(fileV, hV);
            destroyHashtable(hA);
            destroyHashtableVoo(hV);
            break;
        }
    }
    free(aux);

    close_file(fileA);
    close_file(fileV);
    return 0;
}