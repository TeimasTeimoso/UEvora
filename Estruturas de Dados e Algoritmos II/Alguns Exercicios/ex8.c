#include <stdio.h>

void triangulo(int n)
{
    for(int i = 1; i <= n; i++)
    {
        int j = i;
        for(int j = 1; j < i; j++)
        {
            printf("%3d ", i*j);
        }
        printf("%3d\n", i*j);
    }
}
int main(void)
{
    triangulo(10);
    
    return 0;
}