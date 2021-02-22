#include <stdio.h>

/*
//Recursciva
int fibonnaci(int n)
{
    if(n == 0)
        return 0;
    else if(n == 1)
        return 1;
    return fibonnaci(n-1) + fibonnaci(n-2);
}
*/

//Iterativa
int fibonnaci(int n)
{
    int i = 0;
    int penultimo = 1;
    int ultimo = 0;

    while(i < n)
    {
        int proximo = penultimo + ultimo;
        ultimo = penultimo;
        penultimo = proximo;
        
        i++;
    }

    return ultimo;
}


int main(void)
{
    for(int i = 0; i < 19; i++)
    {
        printf("%d ", fibonnaci(i));
    }
    printf("%d\n", fibonnaci(19));

    return 0;
}