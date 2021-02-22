#include <stdio.h>

void binario(int n)
{
    if(n != 0)
    {
        binario(n/2);
        printf("%d", n%2);
    }
    printf("\n");
}

int main(void)
{
    binario(3);
    
    return 0;
}