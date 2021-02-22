#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "hashtable.h"
/*
struct dict
{
    char *cadeira;
    int nota;
};
*/
typedef struct hash_cell
{
    char *key;
    ST_Data value;
} cell;

typedef struct hashtable
{
    int size;
    cell *table[SIZE];
} hash;

/* djb2*/
int getHash(char *id)
{
    int hash = 5381;
    int c;

    while((c = *id++))
        hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
    return hash % SIZE;
}

void insert(hash *ht, char *id, ST_Data data)
{
    int pos;
    cell *nc = malloc(sizeof(cell));

    nc->key = id;
    nc->value = data;

    pos = getHash(id);

    ht->table[pos] = nc;
}

ST_Data getValue(hash *ht, char *id)
{
    int pos;
    pos = getHash(id);

    if(ht->table[pos])
        return ht->table[pos]->value;

    return NULL;
}

hash *newHashtable()
{
    hash *ht = malloc(sizeof(hash));

    ht->size = SIZE;

    for(int i = 0; i < ht->size; i++)
    {
        ht->table[i] = NULL;
    }

    return ht;
}

/*
int main(void)
{

    hash *h = newHashtable();

    ST_Data s = malloc(sizeof(ST_Data));

    s->cadeira = "compiladores";
    s->nota = 10;

    insert(h, "teimas", s);

    ST_Data result = getValue(h, "teimas");

    printf("Cadeira: %s \n Nota: %d \n", result->cadeira, result->nota);
    return 0;
}
*/