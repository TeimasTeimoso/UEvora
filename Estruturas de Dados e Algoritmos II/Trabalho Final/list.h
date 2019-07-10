#include <stdbool.h>

#define CODEMAXVOO 7
#define MAXAEROPORTOS 300003

typedef struct Voo
{
    char codVoo[CODEMAXVOO];
    struct Voo *next;
} Voo;

typedef struct listVoos
{
    Voo *head;
} listaVoos;

typedef struct Grafo
{
    int nAeroportos;
    listaVoos *lista[MAXAEROPORTOS];
} Grafo;

listaVoos *new_listaVoos();
Voo *new_voo(char cod[CODEMAXVOO]);
void listaVoosDestroy(listaVoos *lista);
void addVoo(listaVoos *list, char codeVoo[CODEMAXVOO]);
void delVoo(listaVoos *list, char codeVoo[CODEMAXVOO]);