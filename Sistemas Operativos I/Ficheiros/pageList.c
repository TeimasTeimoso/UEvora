#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include "pageList.h"

/*Inicializa a tabela completa de paginacao */
pageSet *new_pageSet(void)
{
    pageSet *p = malloc(sizeof(pageSet));

    p->listOfPages[MEMSIZE/PAGE_SIZE] = false;

    p->free = MEMSIZE/PAGE_SIZE;

    return p;
}
//Acrescentado n of page
pageNode *new_page(int value, int index)
{
    pageNode *node = malloc(sizeof(pageNode));

    node->element = value;
    node->nOfPage = index;
    node->next = NULL;
    node->previous = NULL;

    return node;
}

//Cria a nova lista
pageList *new_pageList()
{
    pageList *list = malloc(sizeof(pageList));

    list->head = NULL;
    list->tail = NULL;

    return list;
}

void pageList_insert(pageList *list, int value, int index)
{
    pageNode *node = new_page(value, index);

    pageNode *current = list->head;

    if(list->head == NULL)
    {
        list->head = node;
        list->tail = node;
        list->tail->previous = NULL;
    }
    else
    {
        while (current->next != NULL)
            current = current->next;

        current->next = node;
        current->next->previous = current;
        current = current->next;

        list->tail = current;
        
    }
    if(list->head->element == 1)
        list->head->previous = NULL;
}

//Remove elemetos na lista
int pageList_remove(pageList *list)
{
    pageNode *current = list->head;

    int enqueued = current->element;

    list->head = current->next;

    free(current);

    return enqueued;
}

bool pageList_Next(pageList *list)
{
    if(list->head->next != NULL)
    {
        pageNode *previous = list->head;
        pageNode *current = list->head->next;
        list->head = current;
        list->head->previous = previous;

        return true;
    }

    return false;
}

bool pageList_Previous(pageList *list)
{
    if(list->head->previous != NULL)
    {
        pageNode *next = list->head;
        pageNode *current = list->head->previous;
        list->head = current;
        list->head->next = next;

        return true;
    }

    return false;
}