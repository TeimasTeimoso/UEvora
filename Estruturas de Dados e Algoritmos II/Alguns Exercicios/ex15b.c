//Pesquisa Ditómica Recursiva
#include <stdio.h>

#define NPOSICOESARRAY 50000

void preenche_array(int a[NPOSICOESARRAY])
{
    for(int i = 0; i < NPOSICOESARRAY; i++)
    {
        a[i] = 2+2*i;
    }
}

int pesquisa_binaria(int n, int inicio, int fim, int v[])
{
    int meio = (inicio+fim)/2;

    if(inicio > fim)
        return -1;
    else if(n > v[meio])
        return pesquisa_binaria(n, meio+1, fim, v);
    else if(n < v[meio])
        return pesquisa_binaria(n, inicio, meio-1, v);
    return meio;
    
}

int procura(int n, int s, int v[s])
{
    return pesquisa_binaria(n, 0, NPOSICOESARRAY, v);
}



int main(void)
{
    int array[NPOSICOESARRAY];

    preenche_array(array);

    /*
    for (int i = 1; i <= NPOSICOESARRAY; ++i)
    {
        int p = procura(2 * i, NPOSICOESARRAY, array);

        if (p == -1)
            printf("Não encontrou %d\n", 2*i);
        else if(array[p] != 2 * i)
            printf("Encontrou %d na posição errada: %d\n", 2*i, p);
    }
    */

    pesquisa_binaria(11, 0, NPOSICOESARRAY-1, array);
    printf("Terminei!\n");
    
    return 0;
}