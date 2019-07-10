#include <stdio.h>
#include <stdbool.h>

bool e_primo(int n)
{
    int ultimo_divisor = n/2;

    if(n <= 1)
        return false;
    
    for(int i = 2; i <= ultimo_divisor; i++)
    {
        if(n%i == 0)
        {
            return false;
        }
    }

    return true; 
    
}

void primos(int n, int m)
{
    for(int i = n; i <= m; i++)
    {
        if(e_primo(i))
            printf("%d\n", i);
    }
}

int main(void)
{
    primos(555, 777);

    return 0;
}