#include <stdlib.h>
#include <stdio.h>
#include "list.h"

//Cria uma um array de listas ligadas
void init_array(int size, struct list *array[size])
{
    for(int i = 0; i < size; i++)
    {
        struct list *list = list_new();
        array[i] = list;
    }
}

//Procura se existe um caminho
void verify_connections(int nmb_of_connections, int size, struct list *array[size])
{
    bool check_connection = true;
    int detector, linked_detector;

    scanf("%d", &detector);

    int firstDetector = detector;

    //Percorre o input procurando de existe ligacao entre 
    //O detector e o linked_detector e vai
    //Atualizando ambos
    //Caso nao exista ligacao entre um dos sensores
    //A flag passa a false e da print de no
    for(int i = 0; i < nmb_of_connections; i++)
    {
        scanf("%d", &linked_detector);
        if(!list_find(array[detector], linked_detector))
        {
            check_connection = false;
        }
        detector = linked_detector; 
    }
    if(!check_connection)
        printf("no [%d..%d]\n",firstDetector, linked_detector);
    else
        printf("yes [%d..%d]\n",firstDetector,linked_detector);
}

void operation_choice(int size, struct list *array[size])
{
    char choice;
    int pos, linked_detector, nmb_of_connections;
    while(scanf(" %c", &choice) != EOF)
    {
        switch (choice)
        {
            case '+':
                scanf("%d %d", &pos, &linked_detector);
                list_insert(array[pos], linked_detector);
                break;

            case '-':
                scanf("%d %d", &pos, &linked_detector);
                list_remove(array[pos], linked_detector);
                break;

            case '?':
                scanf("%d", &nmb_of_connections);
                verify_connections(nmb_of_connections, size, array);
                break;

            default:
                break;
        }
    }
}

int main(void)
{
    int nmb_of_detectors;

    scanf("%d", &nmb_of_detectors);

    struct list *detectors[nmb_of_detectors];
    init_array(nmb_of_detectors, detectors);

    operation_choice(nmb_of_detectors, detectors);

    return 0;
}