#include <stdio.h>

/*
Funcao que preenche o array com o input
Usando um ciclo for para ler cada valor
*/
void cria_array(int n, int v[])
{
    int m;

    for(int i = 0; i < n; i++)
    {
        scanf("%d", &m);
        v[i] = m;
    }
}


void resultado(int pontos_alex, int pontos_bela)
{
    if(pontos_alex > pontos_bela)
    {
        printf("Alex ganha com %d contra %d\n", pontos_alex, pontos_bela); 
    }
    else if(pontos_alex < pontos_bela)
    {
        printf("Bela ganha com %d contra %d\n", pontos_bela, pontos_alex); 
    }
    else
        printf("Alex e Bela empatam a %d\n", pontos_alex);
}


void jogo(int n, int v[n])
{
    int numero_de_jogadas = 1;
    int inicio = 0;
    int fim = n-1;
    int pontos_alex = 0;
    int pontos_bela = 0;

    //Da condicao de paragem
    while(numero_de_jogadas <= n)
    {
        //Casos possiveis da Bela
        if(numero_de_jogadas%2 == 0)
        {   
            //Jogadas par da bela (retirar maior)
            if((numero_de_jogadas/2)%2 == 0)
            {
                if(v[inicio] > v[fim])
                {
                    pontos_bela = pontos_bela + v[inicio];
                    inicio++;
                }
                else
                {
                    pontos_bela = pontos_bela + v[fim];
                    fim--;
                }
                
            }
            //Jogadas impar da bela (retirar mais pequena)
            else
            {
                if(v[inicio] < v[fim])
                {
                    pontos_bela = pontos_bela +v[inicio];
                    inicio++;
                }
                else
                {
                    pontos_bela = pontos_bela +v[fim];
                    fim--;
                }
            }
        }   
        //Casos do Alex  
        else
        {
            if(v[inicio] > v[fim])
            {
                pontos_alex = pontos_alex + v[inicio];
                inicio++;
            }
            else
            {
                pontos_alex = pontos_alex + v[fim];
                fim--;
            } 
        }

        numero_de_jogadas++;
            
    }

    resultado(pontos_alex, pontos_bela);
}


int main(void)
{
    int input;

    scanf("%d", &input);
    int array[input];

    cria_array(input, array);
    jogo(input, array);
}