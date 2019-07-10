#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include<stdbool.h>

#define MAXVOOS 900003 // num. primo
#define CODEVOO 7
#define CODEMAX 5


typedef struct VooHash
{
    char codigo[CODEVOO];
    char partida[CODEMAX];
    char destino[CODEMAX];
    short horas;
    short minutos;
    short duracao;
} VooHash;

typedef struct HTVoos
{
    int numeroVoo;
    VooHash *array[MAXVOOS];
} HTVoos;

HTVoos *criarHTVoos();
void destroyHashtableVoo(HTVoos *ht);
unsigned long hashCodeVoo(char *str);
int hashCodeVoo2(char *code);
int findPosVoos(HTVoos *hashA, char *code);
VooHash *findVoo(HTVoos *hashV, char *code);
bool isEmptyVoo(HTVoos *hashA);
bool insertVoo(HTVoos *hashA, char *code, char *partida, char *destino, short horas, short minutos, short duracao);
void removeVoo(HTVoos *hashA, char *code);