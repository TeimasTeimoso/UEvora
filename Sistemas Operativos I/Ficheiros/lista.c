#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "lista.h"

//Construtor do nó
node *node_new(PCB *value)
{
    node *node = malloc(sizeof( node));

    node->element = value;
    node->next = NULL;

    return node;
}

//Cria a nova lista
list *list_new()
{
    list *list = malloc(sizeof(list));

    list->head = NULL;
    list->tail = NULL;

    return list;
}

//Verifica s ea lista esta vazia
bool list_empty(list *list){

    if(list->head == NULL)
        return true;
    
    return false;
}

//Insere elementos na lista
void list_insert(list *list, PCB *value, char state[8])
{
    node *node = node_new(value);
    strcpy(node->element->program_state, state);

    if(list->head == NULL)
    {
        list->head = node;
        list->tail = node;
    }
    else
    {
        //Logo, o node vai ser inserido a seguir ao current
        list->tail->next = node;
        //E o node passa a ser a nova tail
        list->tail = node;
    }

}

//Retorna o tamanho da lista
int list_size(list *list)
{
    node *current = list->head;
    int size = 0;

    if(list_empty(list))
        return size;

    while(current != NULL)
    {
        size++;
        current = current->next;
    }

    return size;
}


//Verifica se elementos existem na lista
bool list_find(list *list, int value)
{
    int pos = 0;
    node *current = list->head;

    while(current != NULL)
    {
        if(current->element->P_ID == value)
            return true;
        pos++;
        current = current->next;
    }

    return false;
}

//Remove elemetos na lista
PCB *list_remove(list *list)
{
    node *current = list->head;

    PCB *enqueued = current->element;

    list->head = current->next;

    free(current);

    return enqueued;
}

// print dos processos num dado instante
void print_todos(int instante, list *lista)
{
    node *current = lista->head;

    printf("%3d", instante);

    while(current != NULL){
        if(strcmp(current->element->program_state, "sleep") == 0 || current->element->finished == true)
           printf(" |         ");
        else
        {
            printf(" | %8s", current->element->program_state);
            //return;
        }

        current = current->next;

        //PROBLEMA NO PRINT

    }
    printf("\n");
}

/*
void print_mem(int instante, list *lista, int mem[MEMSIZE])
{
    node *current = lista->head;

    printf("%d\n", instante);
    while(current != NULL)
    {
        printf("%3d ", current->element->P_ID);
        for(int i = 0; i<10; i++)
            printf(" | %3d", mem[current->element->mem_start+i]);
        printf("\n");
        current = current->next;
    }
    printf("\n");
}
*/

// incrementa a variavel do tempo que o processo esta no estado
void mais_tempo(list *estado)
{
    node *node = estado->head;
    for (int i = 0; i < list_size(estado); i++)
    {
        node->element->time_spent++;
        node = node->next;
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
