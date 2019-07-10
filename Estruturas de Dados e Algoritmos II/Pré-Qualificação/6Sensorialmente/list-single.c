#include <stdio.h>
#include <stdlib.h>
#include "list.h"

//Tipo node
typedef struct node
{
    int element;
    struct node *next;
} node;

//Tipo lista
typedef struct list
{
    struct node *head;
} list;


//Construtor do nó
node *node_new(int value, node *next)
{
    node *node = malloc(sizeof( node));

    if(node != NULL)
    {
        node->element = value;
        node->next = next;
    }

    return node;
}

//Cria a nova lista
list *list_new()
{
    list *list = malloc(sizeof(list));

    if(list != NULL)
        list->head = NULL;

    return list;
}

//Verifica s ea lista esta vazia
bool list_empty(list *list){

    if(list->head == NULL)
        return true;
    
    return false;
}

//Insere elementos na lista
bool list_insert(list *list, int value)
{
    node *node = node_new(value, list->head);

    if(node == NULL)
        return false;

    list->head = node;

    return true;
}

//Verifica se elementos existem na lista
bool list_find(list *list, int value)
{
    int pos = 0;
    node *current = list->head;

    while(current != NULL)
    {
        if(current->element == value)
            return true;
        pos++;
        current = current->next;
    }

    return false;
}

//Remove elemetos na lista
void list_remove(list *list, int value)
{
    node *prev = NULL;
    node *current = list->head;

    //Caso seja a cabeça vai mudar o pointer
    //Para o proximo elemento
    //E dar free da cabeça
    if(current != NULL && current->element == value)
    {
        list->head = current->next;
        free(current);
        return;
    }

    //Enquanto nao for a o delemento vai guardando o no anterior
    //E o proximo de modo a fazer uma ligacao entre eles
    while(current != NULL && current->element != value)
    {
        prev = current;
        current = current->next;
    }

    prev->next = current->next;
    free(current);
}

void list_print(list *list)
{
    node *current = list->head;

    while(current != NULL){
        printf("%d ", current->element);
        current = current->next;
    }
}


void list_destroy(list *list)
{
    node *current = list->head;

    while(current != NULL)
    {
        free(current);
        current = current->next;
    }
    
    free(list);
}
