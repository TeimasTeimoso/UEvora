#include <stdio.h>

/* Funcao que conta os algarismos de forma 
recursiva, dividindo o inteiro n sucessivamente 
por 10 e trabalhando com o resultado dessa
divisao inteira */
int conta_algarismos(int n)
{
    int div_inteira = n/10;

    if(div_inteira == 0)
        return 1;

    return 1+conta_algarismos(div_inteira);
}

int main(void)
{
    int input;

    scanf("%d", &input);
    printf("%d\n",conta_algarismos(input));

    return 0;
}