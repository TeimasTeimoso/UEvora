#include "symbolTable.h"
#define SIZE 211


//typedef struct dict *ST_Data;
typedef struct hash_cell cell;
typedef struct hashtable hash;

int getHash(char *id);
void insert(hash *ht, char *id, ST_Data data);
ST_Data getValue(hash *ht, char *id);
hash *newHashtable();