#include <stdio.h>
#include<stdbool.h>

FILE* open_file()
{
    FILE* f = fopen("int_file.samuel", "r+");

    if(f == NULL)
    {
        int zero = 0;
        for(int i = 0; i < 10; i++)
        {
            fwrite(&zero, 1, sizeof(zero), f);
        }
    }

    return f;
}

void manipulate_file(int value, int pos, FILE* file)
{
    int old_value;
    int new_value;

    fseek(file, pos*sizeof(int), SEEK_SET);

    fread(&old_value, 1, sizeof(old_value), file);

    new_value = old_value + value;

    fwrite(&new_value, 1, sizeof(value), file);
}

int display_file(int value, FILE* file)
{
    int result;

    fseek(file, value*sizeof(int), SEEK_SET);

    fread(&result, 1, sizeof(result), file);

    return result;
}

void default_value(int pos, FILE* file)
{
    int zero = 0;

    fseek(file, pos*sizeof(int), SEEK_SET);

    fwrite(&zero, 1, sizeof(int), file);
}

int whole_program(FILE* file)
{
    char n;
    int value;
    int pos;

    while(true)
    {
        switch(n)
        {
            case '?':
                scanf("%d", &value);
                display_file(value, file);
                break;

            case '+':
                scanf("%d %d", &value, &pos);
                manipulate_file(value, pos, file);
                break;

            case 'r':
                scanf("%d", &value);
                default_value(value, file);
                break;

            case 'q':
                break;
        }
    }
}

void close_file(FILE* file)
{
    fclose(file);
}

int main(void)
{   
    FILE* file = open_file();
    whole_program(file);
    close_file(file);
    return 0;
}