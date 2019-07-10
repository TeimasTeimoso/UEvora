#include <stdio.h>

int main(void)
{
    int n, m;
    float a, b;
    
    //Le input
    scanf("%d", &n);

    //Da print
    printf("%d\n", n);

    //Le 2 inteiros
    scanf("%d + %d =", &n, &m);

    //Printa a soma
    printf("%d + %d = %d\n", n, m, n+m);

    //Valor real
    scanf("%f", &a);

    //Print real
    printf("%f\n", a);

    //Le coordenada x y
    scanf(" (%f, %f)", &a, &b);

    //? nao funciona
    //Printa x e y
    printf("x = %.3f, y = %.3f\n", a, b);

    return 0;
}