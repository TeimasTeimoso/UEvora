#include "pQ.h"


HashAeroportos *criarHashAeroportos()
{
    HashAeroportos *hash = (HashAeroportos *)malloc(sizeof(HashAeroportos));
    for(int i = 0; i < MAXAEROPORTOS; i++)
    {
        hash->array[i] = NULL;
    }
    hash->numeroAeroportos = 0;
    return hash;
}

void destroyHashtable(HashAeroportos *ht)
{
    for(int i = 0; i < MAXAEROPORTOS; i++)
    {
        if (ht->array[i] != NULL)
        {
            listaVoosDestroy(ht->array[i]->lista);
            free(ht->array[i]);
        }
    }
    free(ht);
}


//https://gist.github.com/MohamedTaha98/ccdf734f13299efb73ff0b12f7ce429f
unsigned long hashCodeAeroporto(char *str) 
{

    unsigned long hash = 5381;
    int c;
    while ((c = *str++))
        hash = ((hash << 5) + hash) + c;
    return hash % MAXAEROPORTOS;

}

//Função hash para o double hashing
int hashCodeAeroporto2(char *code)
{
    int codeR = hashCodeAeroporto(code);
    codeR = codeR % 3;
    return codeR;
}

//Funão que procura a posição do aeroporto ou a primeira posição a null, usando funções de hash
int findPos(HashAeroportos *hashA, char *code)
{
    int hashcode = hashCodeAeroporto(code);
    int p = 1;
    while (hashA->array[hashcode] != NULL)
    {
        if (strcmp(hashA->array[hashcode]->codigo, code) == 0)
        {
            return hashcode;
        }
        hashcode = (hashcode + hashCodeAeroporto2(code) + p) % MAXAEROPORTOS;
        p ++;
    }
    return hashcode;
}

//Função que dado um código de aeroporto, retorna o aeroporto
Aeroporto *findAeroporto(HashAeroportos *hashA, char *code)
{
    if (hashA->array[findPos(hashA, code)] == NULL)
    {
        return NULL;
    }
    
    Aeroporto *temp = hashA->array[findPos(hashA, code)];    
    return temp;
}

//Funçãoq ue diz que a hash esta vazia ou não
bool isEmpty(HashAeroportos *hashA)
{
    if (hashA->numeroAeroportos == 0)
    {
        return true;
    }
    return false;
}


//Função que insere um aeropordo na hash table
bool insertAeroporto(HashAeroportos *hashA, char *code, short gmtHoras, short gmtMinutos)
{
    int pos = findPos(hashA, code);

    hashA->array[pos] = (Aeroporto *)malloc(sizeof(Aeroporto));
    strcpy(hashA->array[pos]->codigo, code);
    hashA->array[pos]->lista = new_listaVoos();
    hashA->array[pos]->gmtHoras = gmtHoras;
    if (gmtHoras < 0)
    {
        hashA->array[pos]->gmtMinutos = 0 - gmtMinutos;
    }
    else
    {
        hashA->array[pos]->gmtMinutos = gmtMinutos;
    }
    hashA->array[pos]->flag = false;
    hashA->numeroAeroportos ++;
    return true;
}