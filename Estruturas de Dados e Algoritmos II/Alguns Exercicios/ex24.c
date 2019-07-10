#include <stdio.h>

#include "list.h"

#define size 100000

int main(void)
{
    list *l;

    l = list_new();

    for(int i = 2; i <= size; i = i + 2)
    {
        list_insert(l, i);

    }

    for(int i = 2; i <= size; i = i + 2)
    {
        list_find(l, i);
    }

    list_destroy(l);

    return 0;

}
