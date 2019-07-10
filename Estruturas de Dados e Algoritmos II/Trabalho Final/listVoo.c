#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "list.h"

Voo *new_voo(char cod[CODEMAXVOO])
{
    Voo *node = malloc(sizeof(Voo));

    strcpy(node->codVoo, cod);

    node->next = NULL;

    return node;
}

listaVoos *new_listaVoos()
{
    listaVoos *lista = malloc(sizeof(listaVoos));

    lista->head = NULL;

    return lista;
}

void addVoo(listaVoos *lista, char cod[CODEMAXVOO])
{
    Voo *novoVoo = new_voo(cod);

    if(lista->head == NULL)
    {
        lista->head = novoVoo;
        lista->head->next = NULL;
    }
    else
    {
        novoVoo->next = lista->head;
        lista->head = novoVoo;
    }
}

void delVoo(listaVoos *lista, char cod[CODEMAXVOO])
{
    Voo *anterior = NULL;
    Voo *atual = lista->head;

    //Caso o nó esta na cabeça
    if(atual != NULL && strcmp(atual->codVoo, cod) == 0)
    {
        lista->head = atual->next;
        free(atual);
        return;
    }

    while(atual != NULL && strcmp(atual->codVoo, cod) != 0)
    {
        anterior = atual;
        atual = atual->next;
    }

    anterior->next = atual->next;
    free(atual);
}

void listaVoosDestroy(listaVoos *lista)
{
    Voo *atual = lista->head;
    Voo *temp = NULL;

    while(atual != NULL)
    {
        temp = atual->next;
        free(atual);
        atual = temp;
    }
    free(lista);
}