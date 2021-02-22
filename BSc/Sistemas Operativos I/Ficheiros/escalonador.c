#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "lista.h"

#define BLOCK_TIME 3

int ID_PROX_FILHO = MEMSIZE;

/*Vai para a pagina em que o processo pai se encontrava */
void go_to(PCB *filho, int nOfPage)
{
    int counter = 1;

    while(counter != nOfPage)
    {
        counter++;
        filho->pageLocation->head = filho->pageLocation->head->next;
    }
}


/*Inicializa o array da memória */
void reset_array(int mem[MEMSIZE])
{
   for(int i = 0; i < MEMSIZE; i++)
        mem[i] = NULL;
}


int read_input(const char* file_name, int array[MEMSIZE], list *processList)
{

    FILE* file = fopen(file_name, "r");

    char* line = NULL;   
    size_t len = 0;
    char *space = " ";
    char *token;
    int numero_processos = 0;

    while(getline(&line, &len, file) != -1)
    {
        bool instante = false;
        numero_processos++;
        
        PCB *process = new_PCB(numero_processos);
        list_insert(processList, process, "sleep");

        int pos = 0;
        
        for(token = strtok(line, space); token != NULL; token = strtok(NULL, space))
        {
            if (instante == false)
            {
                processList->tail->element->start_time = atoi(token);
                instante = true;
            }
            else
            {
                processList->tail->element->instruction_set[pos] = atoi(token);        //Copia as intrucoes para um array próprio de cada processo
                pos++;
            }
        }
        
        processList->tail->element->n_of_instruct = (pos)/INSTRUCTION_SIZE;
    }

    return numero_processos;
}

void new_(list *processList, list *new, int current_time, int mem[MEMSIZE])
{
    node *current = processList->head;

    while(current != NULL)
    {
        if(current->element->start_time == current_time)
        {
            list_insert(new, current->element, "new");
            return;
        }
        current = current->next;
    }
}

void new_ready(list *new, list *ready, pageSet *pageSet, int mem[MEMSIZE])
{
    if(!list_empty(new) && new->head->element->time_spent >= 1)
    {

        list_insert(ready, list_remove(new), "ready");

        node *current = ready->head;

        /*Percorre toda a lista pois pode haver mais do que um processo no ready */
        while(current != NULL)
        {
            int page = 0;
            int instSetPos = 0;
            int nOfPage = 1;

            if(((pageSet->free) * PAGE_SIZE) > (current->element->n_of_instruct)*INSTRUCTION_SIZE) //Verifica se existem paginas suficientes para passar para ready
            {
                while(current->element->allocated < (current->element->n_of_instruct)*INSTRUCTION_SIZE + N_OF_VARS) //Enquanto nao tiver alocado todas as intruções
                {
                    if((!pageSet->listOfPages[page]) && current->element->allocated == 0) //Aloca as variaveis
                    {   

                        current->element->variablesPage = page;  //Guarda a pagina das variaveis (IMPLEMENTAR)
                        current->element->allocated += 9;       //Incrementa na sua alocacao

                        pageSet->listOfPages[page] = true;          //Coloca a pagina ocupada
                        current->element->n_paginas++;

                        page++;                                 //Passa à proxima pagina
                        pageSet->free--;
                    }
                    else if(!pageSet->listOfPages[page])
                    {
                        pageList_insert(current->element->pageLocation, page, nOfPage); //Guarda a pagina na lista
                        nOfPage++;          //Incrementa o numero da pagina em que ta

                        for(int i = 0; i < PAGE_SIZE; i++)
                        {
                            mem[(page*PAGE_SIZE) + i] = current->element->instruction_set[instSetPos];  //Retira as intrucoes do array do processo e coloca na memória
                            instSetPos++;
                            current->element->allocated++;
                        }

                        pageSet->listOfPages[page] = true; //Coloca a pagina ocupada
                        current->element->n_paginas++;

                        page++;                     //Passa á proxima página
                        pageSet->free--;
                    }
                    else
                    {
                        page++;                     //Caso nenhuma pãgina teja disponivel passa à proxima
                    }  
                }
            }
            current = current->next;
        }
        /* 
        for(int i = 0; i <  MEMSIZE; i++)
        {
            if(i != 0 && i%10 == 0)
                printf("\n");
            printf("%d ", mem[i]);
            if(i == MEMSIZE-1)
            {
                printf("\n");
            }
        }
        printf("\n");  
        */
    }

}

void ready_run(list *ready, list*run)
{
    if(!list_empty(ready))
    {
        list_insert(run, list_remove(ready), "run");
        run->head->element->time_spent = 0;
    }
}


void run_ready(list *run, list *ready)
{
    if(!list_empty(run))
        list_insert(ready, list_remove(run), "ready");
}

void blocked_ready(list *blocked, list *ready, int mem[MEMSIZE])
{
    if(!list_empty(blocked) && blocked->head->element->time_spent == BLOCK_TIME)
    {
        PCB *p = blocked->head->element;
        disk_op(p, mem);
        list_insert(ready, list_remove(blocked), "ready");
    }

}

void run_blocked(list *blocked, list *run)
{
    if(!list_empty(run))
        list_insert(blocked, list_remove(run), "blocked");
}



void exit_(list *run, list *exit)
{
    list_insert(exit, list_remove(run), "exit");
}


void copy_pai_filho(PCB *pai, PCB *filho)
{
    ID_PROX_FILHO++;
    
    filho->n_of_instruct = pai->n_of_instruct;

    for (int i = 0; i < 100; i++)
        filho->instruction_set[i] = pai->instruction_set[i];

    filho->PC = pai->PC;
    filho->P_ID = ID_PROX_FILHO;
    filho->PC += INSTRUCTION_SIZE;
}


int run_(list *new, list *run, list *ready, list *blocked, list *exit, pageSet *pageSet, int mem[MEMSIZE], int current_time, list *processList)
{
    PCB *process = run->head->element;
    int program_counter[3];
    get_instruction(process, program_counter);
    int instruction = mem[program_counter[0]];
    int first_number = mem[program_counter[1]];
    int second_number = mem[program_counter[2]];

    switch (instruction)
    {
// ok
    case 0:
        set_x(run->head->element, mem, first_number, second_number);
        break;

// ok
    case 1: 
        set_n(run->head->element, mem, first_number, second_number);
        break;

// ok
    case 2: 
        inc_x(run->head->element, mem, first_number);
        break;
    
// ok
    case 3:
        dec_x(run->head->element, mem, first_number);
        break;

// +/- ok
    case 4: //Instrucao que pode dar problema    
        if (back_n(run->head->element, first_number) == -1)
        {
            exit_(run, exit);
            process_exit(exit->tail->element, pageSet, mem);
        }
        break;

// ok
    case 5: //Instrucao que pode dar problema  
        if (forwd_n(run->head->element, first_number) == -1)
        {
            exit_(run, exit);
            process_exit(exit->tail->element, pageSet, mem);
        }
        break;

// ok
    case 6: 
        if (if_x_n(run->head->element, first_number, second_number) == -1)
        {
            exit_(run, exit);
            process_exit(exit->tail->element, pageSet, mem);
        }
        break;

    case 7: // fork
        {
        PCB *process = new_PCB(-1);
        list_insert(processList, process, "sleep");

        node *current = processList->head;
        while(current->next != NULL)
            current = current->next;

        PCB *filho = current->element;
        PCB *pai = run->head->element;

        copy_pai_filho(pai, filho);


        list_insert(new, filho, "new");
        new->tail->element->time_spent = 1;
        new_ready(new, ready, pageSet, mem);
        go_to(filho, pai->pageLocation->head->nOfPage);

        mem[pai->variablesPage*10 + first_number-1] = filho->P_ID;
        mem[filho->variablesPage*10 + first_number-1] = 0;

        /* Print da memória
        for(int i = 0; i <  MEMSIZE; i++)
        {
            if(i != 0 && i%10 == 0)
                printf("\n");
            printf("%d ", mem[i]);
            if(i == MEMSIZE-1)
            {
                printf("\n");
            }
        }
        printf("\n");  
        */      

        pai->PC += INSTRUCTION_SIZE;        

        return -1;
        break;
        }

//ok
    case 8: // escrever
        run->head->element->block_var = first_number;
        run->head->element->block_instruction = instruction;
        run->head->element->PC+=INSTRUCTION_SIZE;
        list_insert(blocked, list_remove(run), "blocked");
        blocked->tail->element->time_spent = 0;
        break;

// ok
    case 9: // ler
        run->head->element->block_var = first_number;
        run->head->element->block_instruction = instruction;
        run->head->element->PC+=INSTRUCTION_SIZE;
        list_insert(blocked, list_remove(run), "blocked");
        blocked->tail->element->time_spent = 0;
        break;

// OK
    case 10: // fazer prints
        print_var(run->head->element, mem, first_number);
        break;

// ok
    case 11: 
        exit_(run, exit);
        process_exit(exit->tail->element, pageSet, mem);
        break;
        
    default:
        printf("Isto não é suposto acontecer");
        break;
    }
    return 0;
}

void clear_process(list *processList)
{
    node *current = processList->head;

    while(current != NULL)
    {
        if(strcmp(current->element->program_state, "exit") == 0)
            current->element->finished = true;
        
        current = current->next;
    }
    
}

int main(void)
{
    int mem[MEMSIZE];

    reset_array(mem);
    pageSet *pageTable = new_pageSet(); //Tabela de boleanos de paginas 

    list *processList = list_new();

    list *new = list_new();
    list *ready = list_new();
    list *run = list_new();
    list *blocked = list_new();
    list *exitt = list_new();

    int current_time = 0;
    int n_processos = read_input("input.txt", mem, processList);

    while(list_size(exitt) < n_processos)
    {
        
        if(!list_empty(run) && run->head->element->time_spent == QUANTUM)
            run_ready(run, ready);

        new_ready(new, ready, pageTable, mem);
            
        if(list_empty(run))
            ready_run(ready, run);

        new_(processList, new, current_time, mem);

        print_todos(current_time, processList);
        //print_mem(current_time, processList, mem);

        if(!list_empty(exitt))
            clear_process(exitt);

        if(!list_empty(run))
        {
            if(run_(new, run, ready, blocked, exitt, pageTable, mem, current_time, processList) == -1)
                n_processos++;                
        }
        blocked_ready(blocked, ready, mem);

        current_time++;

        mais_tempo(new); mais_tempo(ready); mais_tempo(run); mais_tempo(blocked);
        
    }

    print_todos(current_time, processList);
    

    list_destroy(new);
    list_destroy(ready);
    list_destroy(run);
    list_destroy(blocked);

    return 0;
}

/* 
for(int i = 0; i <  MEMSIZE; i++)
{
    if(i != 0 && i%10 == 0)
        printf("\n");
    printf("%d ", mem[i]);
    if(i == MEMSIZE-1)
    {
        printf("\n");
    }
}
printf("\n");
*/