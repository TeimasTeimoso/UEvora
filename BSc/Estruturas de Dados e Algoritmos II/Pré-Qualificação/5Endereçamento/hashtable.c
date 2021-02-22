#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "hash_header.h"

typedef struct cell
{
    int adress;
    int interface;
} Cell;

/*
inicializa as posicoes da hashtable a NULL
*/
void hashtable_init(Cell *hash[SIZE])
{
    for(int i = 0; i < SIZE; i++)
        hash[i] = NULL;
}

int hashcode(int adress)
{
    return (adress%SIZE);
}

/*
Funcao que verifica se existem colisoes e caso existam
Irá trata-las usando hash quadratica de modo a calcular 
O seu novo hash code
*/
int find_pos(int adress, Cell *hash[SIZE])
{
    int hashPos = hashcode(adress);
    int collision = 1;

    while(hash[hashPos] != NULL && hash[hashPos]->adress != adress)
    {
        hashPos = hashPos + collision*collision; //hash quadratica
        collision++;

        if(hashPos > SIZE-1)
            hashPos = hashPos % SIZE;
    }
    
    return hashPos;
}

/*
Aloca memoria para a celula e passa-lhes
Os valores pretendidos nos respetivos parametros
Colocando depois a celula na prote
*/
void insert(int adress, int interface, Cell *hash[SIZE])
{
    Cell *cell = malloc(sizeof(Cell));

    cell->adress = adress;
    cell->interface = interface;
    
    int hashPos = find_pos(adress, hash);

    hash[hashPos] = cell;
}

/*
Procura pelo endereco de ip com auxilio
Da funcao find_pos e verifica se o valor do endereco
Que esta nessa posicao e igual ao pretendido
Caso seja retorna a sua interface, senao retorna -1
*/
int search(int adress, Cell *hash[SIZE])
{
    int hashPos = find_pos(adress, hash);

    if(hash[hashPos] != NULL && hash[hashPos]->adress == adress)
    {
        return hash[hashPos]->interface;
    }

    return -1;
}

/*
Percorre a hashtable e liberta 
A memória alocada anteriormente
*/
void hashtable_destroy(Cell *hash[SIZE])
{
    for(int i = 0; i < SIZE; i++)
    {
        if(hash[i] != NULL)
            free(hash[i]);
    }
}
