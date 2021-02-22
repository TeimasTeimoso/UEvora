#include <stdio.h>
#include <math.h>
#include "hash_header.h"

/*
Converte os endereços ip para um unico inteiro
E insere as respetivas routes na hashtable
*/
void save_routs(int route, Cell *hash[SIZE])
{
    int a, b, c, d, interface;

    for(int i = 0; i < route; i++)
    {
        scanf("%d.%d.%d.%d %d", &a, &b, &c, &d, &interface);

        int full_adress = (pow(10, 6) * a) + (pow(10, 3) * b) + c;

        insert(full_adress, interface, hash);
    }
}

/*
Verifica se a interface existe e dá print da mesma,
Caso contrario verifica se existe uma default_route,
Se nao existir irá dar print de "no route"
*/
void print_interface(int full_adress, Cell *hash[SIZE])
{
    int interface = search(full_adress, hash);
    int default_route = search(0, hash);

    if(interface != -1)
        printf("%d\n", interface);
    else if(default_route != -1)
        printf("%d\n", default_route);
    else
        printf("no route\n");
}

/*
Verifica se o endereco existe na hashtable
E caso exista devolve a sua interface
Com o auxilio da funcao print_interface
*/
void verify_routes(Cell *hash[SIZE])
{  
    int a, b, c, d;

    while(scanf("%d.%d.%d.%d", &a, &b, &c, &d) != EOF)
    {
        int full_adress = (pow(10, 6) * a) + (pow(10, 3) * b) + c;
        
        print_interface(full_adress, hash);
        
    }

}

int main(void)
{
    Cell *hashtable[SIZE];

    int nmr_of_routes;

    hashtable_init(hashtable);

    scanf("%d", &nmr_of_routes);

    save_routs(nmr_of_routes, hashtable);

    verify_routes(hashtable);

    hashtable_destroy(hashtable);
    
    return 0;
}