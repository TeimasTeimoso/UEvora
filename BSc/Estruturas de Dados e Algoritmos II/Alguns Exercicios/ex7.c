#include <stdio.h>


void quadrado(int n)
{
    for(int i = 1; i <= n; i++)
    {
        //Pois vai ser usado no ultimo caso, logo tem de ter o valor final
        int j = n;
        for(int j = 1; j < n; j++)
        {
            printf("%3d ", i*j);
        }
        printf("%3d\n", i*j);
    }
}


int main(void)
{
   quadrado(10);
   
   return 0;
}