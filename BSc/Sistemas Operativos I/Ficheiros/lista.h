#include <stdbool.h>
#include "processo_instruct.h"

//Tipo node
typedef struct node
{
    PCB *element;
    struct node *next;
} node;

//Tipo lista
typedef struct list
{
    struct node *head;
    struct node *tail;
} list;

struct node *node_new(PCB *value);
struct list *list_new();
bool list_empty(struct list *list);
void list_insert(struct list *list, PCB *value, char state[8]);
int list_size(struct list *list);
PCB *list_remove(struct list *list);
bool list_find(struct list *list, int value);
void list_print(struct list *list);
void list_destroy(struct list *list);
void print_todos(int instante, list *lista);
void print_mem(int instante, list *lista, int mem[MEMSIZE]);
void mais_tempo(list *estado);