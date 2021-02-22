#include <stdio.h>

//recursivo
/*
int fatorial(int n)
{
    if(n == 0)
        return 1;
    return n * fatorial(n-1);
}
*/

int fatorial(int n)
{
    int fatorial = 1;

    for(int i = n; i > 1; i--)
    {
        fatorial = fatorial * i;
    }
    return fatorial;   
}

int main(void)
{
    printf("%d\n",fatorial(10));
    //Overflow?
    printf("%d\n", fatorial(20));
    return 0;
}