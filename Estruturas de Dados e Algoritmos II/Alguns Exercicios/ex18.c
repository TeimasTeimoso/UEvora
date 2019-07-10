#include <stdio.h>

int my_strlen(char s[])
{
    int counter = 0;

    while(s[counter] != '\0')
    {
        counter++;
    }

    return counter;
}

int main(void)
{
    printf("%d\n",my_strlen(""));

    return 0;
}