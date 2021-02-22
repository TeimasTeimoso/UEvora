#include "hashTableVoos.h"

HTVoos *criarHTVoos()
{
    HTVoos *hash = (HTVoos *)malloc(sizeof(HTVoos));
    for(int i = 0; i < MAXVOOS; i++)
    {
        hash->array[i] = NULL;
    }
    hash->numeroVoo = 0;
    return hash;
}

void destroyHashtableVoo(HTVoos *ht)
{
    for(int i = 0; i < MAXVOOS; i++)
    {
        if (ht->array[i] != NULL)
        {
            free(ht->array[i]);
        }
    }
    free(ht);
}


//https://gist.github.com/MohamedTaha98/ccdf734f13299efb73ff0b12f7ce429f
unsigned long hashCodeVoo(char *str) {

    unsigned long hash = 5381;
    int c;
    while ((c = *str++))
        hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
    return hash % MAXVOOS;
}

int hashCodeVoo2(char *code)
{
    int codeR = hashCodeVoo(code);
    codeR = codeR % 3;
    return codeR;
}

int findPosVoos(HTVoos *hashA, char *code)
{
    int hashcode = hashCodeVoo(code);
    int p = 1;
    while (hashA->array[hashcode] != NULL)
    {
        if (strcmp(hashA->array[hashcode]->codigo, code) == 0)
        {
            return hashcode;
        }
        hashcode = (hashcode + hashCodeVoo2(code) + p) % MAXVOOS;
        p ++;
    }
    return hashcode;
}

VooHash *findVoo(HTVoos *hashV, char *code)
{
    if (hashV->array[findPosVoos(hashV, code)] == NULL)
    {
        return NULL;
    }
    
    VooHash *temp = hashV->array[findPosVoos(hashV, code)];    
    return temp;
}

bool isEmptyVoo(HTVoos *hashA)
{
    if (hashA->numeroVoo == 0)
    {
        return true;
    }
    return false;
}

bool insertVoo(HTVoos *hashA, char *code, char *partida, char *destino, short horas, short minutos, short duracao)
{
    int pos = findPosVoos(hashA, code);

    hashA->array[pos] = malloc(sizeof(VooHash));
    strcpy(hashA->array[pos]->codigo, code);
    strcpy(hashA->array[pos]->partida, partida);
    strcpy(hashA->array[pos]->destino, destino);
    hashA->array[pos]->horas = horas;
    hashA->array[pos]->minutos = minutos;
    hashA->array[pos]->duracao = duracao;

    hashA->numeroVoo ++;
    return true;
}

void removeVoo(HTVoos *hashV, char *code)
{
    VooHash *temp = hashV->array[findPosVoos(hashV, code)];
    hashV->array[findPosVoos(hashV, code)] = 0;
    free(temp);
    hashV->numeroVoo --;
}