#include <stdio.h>

void troca(int* x, int* y)
{
    int temp = *x;

    *x = *y;
    *y = temp;
}

int main(void)
{
  int a, b;

  // lê dois valores inteiros
  printf("insira dois inteiros: "); scanf("%d %d", &a, &b);

  // mostra-os
  printf("a = %d, b = %d\n", a, b);

  troca(&a, &b);	// troca os valores das variáveis

  // mostra os valores depois da troca
  printf("a = %d, b = %d\n", a, b);

  return 0;
}