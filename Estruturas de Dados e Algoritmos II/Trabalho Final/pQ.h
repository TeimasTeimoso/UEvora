#include "hashTableAeroportos.h"

#define MAXSIZE 200001
#define FRONT 1

typedef struct min_heap
{
    int occupied;
    Aeroporto *array[MAXSIZE];
} min_heap;

min_heap *new_heap(); 
void destroyHeap(min_heap *h);
int parent(int pos);
int leftChild(int pos);
int rightChild(int pos);
bool isLeaf(min_heap *h, int pos);
void swap(min_heap *h, int fpos, int spos);
void minHeapify(min_heap *h, int pos);
void insert(min_heap *h, Aeroporto *element);
void minHeap(min_heap *h);
Aeroporto *pop(min_heap *h);
bool emptyHeap(min_heap *h);
void update(min_heap *h, Aeroporto *element);