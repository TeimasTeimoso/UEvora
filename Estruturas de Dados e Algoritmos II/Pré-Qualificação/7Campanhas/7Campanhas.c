#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define FILE_NAME "database.bin"
#define STRING_SIZE 56

typedef struct person
{
    int phone1, phone2;
    int income;
    char politics[STRING_SIZE];
    char habits[STRING_SIZE];
    bool exists;

} Person;

//Construtor do tipo person
Person *new_person()
{
    Person *person = malloc(sizeof(Person));

    person->phone1 = 0;
    person->phone2 = 0;
    person->income = 0;
    person->exists = false;

    return person;
}

FILE *open_file()
{
    //Abre a file
    FILE *file = fopen(FILE_NAME, "r+");

    if(file != NULL)
        return file;

    //caso nao exista, cria
    file = fopen(FILE_NAME, "w+");

    return file;
}

void close_file(FILE *file)
{
    fclose(file);
}

//Vai para a posicao necessaria
void seek(FILE *file, int id)
{
    int pos = sizeof(Person) * id;
    fseek(file, pos, SEEK_SET);
}

//Adicona pessoa à base de dados
void add_person(FILE *file, Person *p, int id)
{
    scanf("%d %d", &p->phone1, &p->phone2);
    scanf("%d", &p->income);
    scanf(" %[^\n]s", p->politics);
    scanf(" %[^\n]s", p->habits);

    p->exists = true;

    seek(file, id);

    fwrite(p, sizeof(Person), 1, file);

}

//Procura a pessoa na base de dados
void find_person(FILE *file, Person *p, int id)
{
    seek(file, id);

    //Se o id estiver contido no ficheiro
    if(fread(p, sizeof(Person), 1, file) == 1)
    {
        if(p->exists != false)
        {
            printf("+ SUJEITO %06d\n", id);
            printf("%d %d\n", p->phone1, p->phone2);
            printf("%d\n", p->income);
            printf("%s\n", p->politics);
            printf("%s\n", p->habits);
        }
        else
        {
            printf("+ SUJEITO %06d desconhecido\n", id);
        }
    }
    //Caso esteja a ser lido fora do ficheiro
    else
    {
        printf("+ SUJEITO %06d desconhecido\n", id);
    }
}

void remove_person(FILE *file, Person *p, int id)
{
    seek(file, id);

    //Id esta a ser lido no ficheiro
    if(fread(p, sizeof(Person), 1, file) == 1)
    {
        if(p->exists == false)
        {
            printf("+ SUJEITO %06d desconhecido\n", id);
            return;
        }

        p->exists = false;

        //Aponta para a posicao certa
        //Pois ao usar o fread avancou uma posicao
        seek(file, id);

        fwrite(p, sizeof(Person), 1, file);
    }
    //Caso o id esteja a ser lido fora do ficheiro
    else
    {
        printf("+ SUJEITO %06d desconhecido\n", id);
    }
}

int main(void)
{
    FILE *file = open_file();
    Person *p = new_person();

    int id;
    char choice;

    while(scanf(" %c", &choice) != EOF)
    {
        scanf("%d", &id);
        switch (choice)
        {
            case '+':
                add_person(file, p, id);
                break;
        
            case '?':
                find_person(file, p, id);
                break;

            case '-':
                remove_person(file, p, id);
                break;
                
            default:
                break;
        }
    }

    free(p);

    close_file(file);
}