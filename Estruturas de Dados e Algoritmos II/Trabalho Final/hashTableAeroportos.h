#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include<stdbool.h>
#include "list.h"

#define MAXAEROPORTOS 300003 // num. primo
#define CODEMAX 5

typedef struct Aeroporto
{
    char codigo[CODEMAX];
    short gmtHoras;
    short gmtMinutos;

    struct listVoos *lista;

    int distance;
    struct Aeroporto *predecessor;

    short horasChegada;
    short minutosChegada;

    bool flag;

    char codeVOO[CODEMAXVOO];
} Aeroporto;

typedef struct HashAeroportos
{
    int numeroAeroportos;
    Aeroporto *array[MAXAEROPORTOS];
} HashAeroportos;

HashAeroportos *criarHashAeroportos();
unsigned long hashCodeAeroporto(char *str);
int findPos(HashAeroportos *hashA, char *code);
Aeroporto *findAeroporto(HashAeroportos *hashA, char *code);
bool isEmpty(HashAeroportos *hashA);
bool insertAeroporto(HashAeroportos *hashA, char *code, short gmtHoras, short gmtMinutos);
void destroyHashtable(HashAeroportos *ht);