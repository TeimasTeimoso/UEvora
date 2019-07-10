#include <stdio.h>
#include <string.h>
#include "pQ.h"

//https://www.geeksforgeeks.org/min-heap-in-java/

min_heap *new_heap()
{
    min_heap *Queue = malloc(sizeof(min_heap));

    Queue->occupied = 0;

    return Queue;
}

void destroyHeap(min_heap *h)
{
    free(h);
}

int parent(int pos)
{
    return (pos / 2);
}

int leftChild(int pos)
{
    return (2 * pos);
}

int rightChild(int pos)
{
    return ((2*pos) + 1);
}

void swap(min_heap *h, int fpos, int spos)
{
    Aeroporto *temp = h->array[fpos];
    h->array[fpos] = h->array[spos];
    h->array[spos] = temp;
}

bool emptyHeap(min_heap *h)
{
    return h->occupied == 0;
}

void minHeapify(min_heap *h, int pos)
{
    if((leftChild(pos) <= h->occupied && (h->array[pos]->distance > h->array[leftChild(pos)]->distance)) || (rightChild(pos) <= pos  && (h->array[pos]->distance > h->array[rightChild(pos)]->distance)))
    {
        if((h->array[leftChild(pos)]->distance) < (h->array[rightChild(pos)]->distance))
        {
            swap(h, pos, leftChild(pos));
            minHeapify(h, leftChild(pos));
        }

        else
        {
            swap(h, pos, rightChild(pos));
            minHeapify(h, rightChild(pos));
        }
        
    }
}

void update(min_heap *h, Aeroporto *element)
{
    int current = h->occupied;
    while(current != 1 && h->array[current]->distance < h->array[parent(current)]->distance)
    {
        swap(h, current, parent(current));
        current = parent(current);
    }
}

void insert(min_heap *h, Aeroporto *element)
{
    if(h->occupied >= MAXSIZE)
    {
        return;
    }

    if(emptyHeap(h))
    {
        h->occupied++;
        h->array[h->occupied] = element;
        return;
    }

    h->array[++h->occupied] = element;


    update(h, element);

    /* 

    int current = h->occupied;

    while(current != 1 && h->array[current]->distance < h->array[parent(current)]->distance)
    {
        swap(h, current, parent(current));
        current = parent(current);
    }

    */
}

void minHeap(min_heap *h)
{
    for(int pos = (h->occupied/2); pos >= 1; pos--)
    {
        minHeapify(h, pos);
    } 
}

Aeroporto *pop(min_heap *h)
{
    Aeroporto *popped = h->array[FRONT];
    if (popped == NULL)
    {
        return NULL;
    }
    
    h->array[FRONT] = h->array[h->occupied--];
    minHeapify(h, FRONT);
    return popped;
}