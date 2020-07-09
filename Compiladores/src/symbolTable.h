#define SIZE 211

/* Estruturas da Hashtable */
typedef struct hash_cell cell;
typedef struct hashtable hash;
/* Tipo de dados da Symbol Table */
typedef struct st_data_ *ST_Data;
/* Tipo Symbol Table */
typedef struct symbol_table ST;

/* Symbol Table Inicial */
ST *SymbolTable;
bool STinitialized;
/* Funcoes da hashtable */
/*
int getHash(char *id);
void hashtableInsert(hash *ht, char *id, ST_Data data);
ST_Data hashtableGetValue(hash *ht, char *id);
hash *newHashtable();
*/

/* Funcoes da Symbol Table */ 
int ST_insert(char *id, ST_Data data);
ST_Data ST_lookup(char *id);
ST_Data ST_lookup_local(char *id);
int ST_new_scope();
int ST_discard();
void initSymbolTable();
ST_Data newVar(t_type varType);
ST_Data newFunction(t_argdefs arglist, t_type returnType);

struct st_data_
{
    enum {ST_VAR, ST_FUNC, ST_TYPE} kind;

    union
    {
        struct 
        {
            t_type type; /*por corrigir, pode ser mt coisa */
            enum {VARloc, VARarg} varKind;
            /* mais tarde precisamos de mais info */
        } var;
        struct
        {
            t_type returnType;/* tipo de retorno */
            t_argdefs argList; /* lista de tipos de argumentos */
            char *id;
            /* mais tarde precisamos de mais info */
        } func;
        t_type newType; /* por corrigir, ainda é mt abstrato */
    }u;
};
