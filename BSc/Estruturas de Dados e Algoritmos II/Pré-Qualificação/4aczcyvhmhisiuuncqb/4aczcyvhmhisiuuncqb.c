#include <stdio.h>
#include <stdbool.h>

#define INPUTMAXSIZE 1101
#define ALPHARRAYSIZE 26
#define FIRSTLETTER 97

/*
Cria um array de boleanos (a false) com 26 posicoes
Equivalentes aos 26 caracteres do alfabeto
*/
void default_alphabetic_arrayc(int size, char a[size])
{
    for(int i = 0; i < size; i++)
    {
        a[i] = false;
    }
}


/*
Converte o char para o seu codigo ascii
E subtrai-lhe o valor do primeiro char alfabetico(a)
De modo a encontar a posicao no array alfabetico
*/
int find_char(int pos, int size, char a[size])
{
    int ascii_code = a[pos];

    return ascii_code-FIRSTLETTER;
}

/*
Verifica se o caracter atual já foi encontrado, caso tenha 
Sido da print do mesmo e mete o array alfabetico em default. 
Caso contrario da flag a esse char no array, colocando a true
*/
void verify_char(int size, int alphasize, char a[size], char alpha[alphasize])
{
    for(int i = 0; a[i] != '\0'; i++)
    {   
        int pos = find_char(i, size, a);

         if(alpha[pos])
        {
            printf("%c",a[i]);
            default_alphabetic_arrayc(alphasize, alpha);
        }
        else
        {
            alpha[pos] = true;
        }    
    }
    printf("\n");
}


/*
Recebe o input e executa as intrucoes dentro do ciclo,
Linha a linha, até que o input acabe
É importante colocar o array alfabetico em default apos o fim
De uma linha para assegurar que nao misturamos chars de varias linhas
*/
void get_input(int size, int alphasize, char a[size], char alpha[ALPHARRAYSIZE])
{  
    while(scanf("%s", a) != EOF)
    {
        verify_char(size, alphasize, a, alpha);
        default_alphabetic_arrayc(alphasize, alpha);
    }
}

int main(void)
{
    char alphabetic_array[ALPHARRAYSIZE];
    char input[INPUTMAXSIZE];

    default_alphabetic_arrayc(ALPHARRAYSIZE, alphabetic_array);
    get_input(INPUTMAXSIZE, ALPHARRAYSIZE, input, alphabetic_array);

    return 0;
}