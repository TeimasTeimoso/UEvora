#include <stdlib.h>
#include <stdio.h>
#include "apt.h"
#include "symbolTable.h"

/* Posicao da hashtable */
typedef struct hash_cell
{
    char *key;
    ST_Data value;
} cell;

/* hashtable */
typedef struct hashtable
{
    int size;
    cell *table[SIZE];
} hash;

/* Symbol Table */
typedef struct symbol_table
{
    hash *currentScope;
    hash *nextScope;
} ST;

/* djb2*/
int getHash(char *id)
{
    int hash = 5381;
    int c;

    while((c = *id++))
        hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
    return hash % SIZE;
}

void hashtableInsert(hash *ht, char *id, ST_Data data)
{
    int pos;
    cell *nc = malloc(sizeof(cell));

    nc->key = id;
    nc->value = data;

    pos = getHash(id);

    ht->table[pos] = nc;
}

ST_Data hashtableGetValue(hash *ht, char *id)
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

void destroyCells(hash *ht)
{
    for(int i = 0; i < ht->size; i++)
    {
        if(ht->table[i])
            free(ht->table[i]);
    }
}

int ST_insert(char *id, ST_Data data)
{

    hashtableInsert(SymbolTable->currentScope, id, data);
    return 1; 
}

ST_Data ST_lookup(char *id)
{
    ST_Data result;

    //printf("ID:%s\n", id);
    
    result = hashtableGetValue(SymbolTable->currentScope, id);

    /* Se não encontrar o simbolo no scope atual,
        e o scope seguinte existir, procura nele */
    if(!result && SymbolTable->nextScope)
    {
       return hashtableGetValue(SymbolTable->nextScope, id);
    }

    
    return result;   
}

ST_Data ST_lookup_local(char *id)
{
    //printf("ID local:%s\n", id);
    return hashtableGetValue(SymbolTable->currentScope, id);
}

int ST_new_scope()
{
    SymbolTable->nextScope = SymbolTable->currentScope;
    SymbolTable->currentScope = newHashtable();

    return 0;
}

int ST_discard()
{
    hash *tmp = malloc(sizeof(hash));
    tmp = SymbolTable->currentScope;
    SymbolTable->currentScope = SymbolTable->nextScope;
    SymbolTable->nextScope = NULL;
    destroyCells(tmp);
    free(tmp); 

    return 0;
}

void initSymbolTable()
{
    SymbolTable = malloc(sizeof(ST));

    SymbolTable->currentScope = newHashtable();/* code */
    SymbolTable->nextScope = NULL;
}

ST_Data newVar(t_type varType)
{
    ST_Data tmp = malloc(sizeof(ST_Data));

    tmp->u.var.type = varType;
    tmp->u.var.varKind = VARloc;
    tmp->kind = ST_VAR;

    return tmp;
}

ST_Data newFunction(t_argdefs arglist, t_type returnType)
{
    ST_Data tmp = malloc(sizeof(ST_Data));

    tmp->u.func.argList = arglist;
    tmp->u.func.returnType = returnType;
    tmp->kind = ST_FUNC;

    return tmp;
}


/*
int main(void)
{
    ST_Data s = malloc(sizeof(ST_Data));
    ST_Data z = malloc(sizeof(ST_Data));

    s->kind = ST_FUNC;
    z->kind = ST_VAR;

    initSymbolTable();

    ST_insert("teimas", s);

    ST_new_scope();

    ST_insert("ruben", z);

    ST_discard();

    ST_insert("zé", z);

    ST_Data a = ST_lookup_local("zé");

    ST_new_scope();

    ST_insert("nem sei", s);

    ST_Data result = ST_lookup_local("teimas");



    if(result)
    {
        if(result->kind == ST_FUNC)
            printf("Passado com 10\n");
        else
        {
            printf("Chumbadissimo\n");
        }
    }
    else
    {
        printf("Mal o menos\n");
    }
    

    free(s);
    free(z);
    free(SymbolTable);

    return 0;
}
*/
