#include <stdio.h>

#define NPOSICOESARRAY 100000


void preenche_array(int a[NPOSICOESARRAY])
{
    for(int i = 0; i < NPOSICOESARRAY; i++)
    {
        a[i] = 2+2*i;
    }
}

int procura(int n, int s, int v[s])
{
    for(int i = 0; i < s; i++)
    {
        if(v[i] == n)
            return i;
    }
    return -1;
}

int main(void)
{
    int array[NPOSICOESARRAY];

    preenche_array(array);
    for (int i = 1; i <= NPOSICOESARRAY; ++i)
    {
        int p = procura(2 * i, NPOSICOESARRAY, array);

        if (p == -1)
            printf("Não encontrou %d\n", 2*i);
        else if(array[p] != 2 * i)
            printf("Encontrou %d na posição errada: %d\n", 2*i, p);
    }

    printf("Terminei!\n");
    
    return 0;
}