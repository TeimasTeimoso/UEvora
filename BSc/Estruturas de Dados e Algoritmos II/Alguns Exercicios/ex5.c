#include <stdio.h>
int maior(int a, int b)
{
    if(a > b)
        return a;
    return b;
}

//Main tem de retornar sempre 0?
int main(void)
{
    int a, b;
    scanf("%d %d", &a, &b);

    printf("%d\n", maior(a, b));

    return 0;
}
