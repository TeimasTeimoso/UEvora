#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "processo_instruct.h"

int VAR_DISCO = 0;

PCB *new_PCB(int P_ID)
{
    PCB *process = malloc(sizeof(PCB));

    process->P_ID = P_ID;
    process->PC = 0;
    strcpy(process->program_state, "sleep");
    process->n_of_instruct = 0;
    process->start_time = 0;
    process->time_spent = 0;
    process->finished = false;
    process->instruction_set[50] = 0;
    process->allocated = 0;
    process->variablesPage = 0;
    process->n_paginas = 0;
    process->pageLocation = new_pageList();
    process->block_instruction = 0;
    process->block_var = 0;

    return process;
}

void destroy_PCB(PCB *p)
{
    free(p);
}

void get_instruction(PCB *p, int array[3])
{

    if(p->PC < PAGE_SIZE-2)              //Se tiver a instrucao e 2 valores seguintes estiverem na msm pagina
    {
        array[0] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC;
        array[1] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC + 1;
        array[2] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC + 2;
    }
    else if(p->PC < PAGE_SIZE-1)       //Se tiver a instrucao e 1 valor seguido na msm pagina
    {
        array[0] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC;
        array[1] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC + 1;
        pageList_Next(p->pageLocation);     //Passa à proxima pagina
        p->PC = 0;
        array[2] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC;
        p->PC-=2;                           //Para compensar as 2 instrucoes que ficaram para tras
    }
    else if (p->PC < PAGE_SIZE)                 //Tem a instrucao numa pagina e os valores na proxima
    {
        array[0] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC;
        pageList_Next(p->pageLocation);     //Passa à proxima pagina
        p->PC = 0;
        array[1] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC;
        array[2] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC + 1;
        p->PC--;                                //Para compensar a instrucao que ficou para tras

    }
    else                                        //A intrucao ta fora da pagina
    {
        pageList_Next(p->pageLocation);   //Passa à proxima pagina
        p->PC = 0;
        array[0] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC;
        array[1] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC + 1;
        array[2] = ((p->pageLocation->head->element) * PAGE_SIZE) + p->PC + 2;
    }
}

//Inst 0
void set_x(PCB *p, int mem[MEMSIZE], int i, int j)
{
    mem[(p->variablesPage)*PAGE_SIZE + i] = mem[(p->variablesPage)*PAGE_SIZE+j];
    p->PC+=INSTRUCTION_SIZE;
}

//Inst 1
void set_n(PCB *p, int mem[MEMSIZE], int i, int n)
{
    mem[(p->variablesPage)*PAGE_SIZE + i -1] = n;
    p->PC+=INSTRUCTION_SIZE;
}

//Inst 2
void inc_x(PCB *p, int mem[MEMSIZE], int x)
{
    mem[(p->variablesPage)*PAGE_SIZE + x -1]++;
    p->PC+=INSTRUCTION_SIZE;
}

//Inst 3
void dec_x(PCB *p, int mem[MEMSIZE], int x)
{
    mem[(p->variablesPage)*PAGE_SIZE + x -1]--;
    p->PC+=INSTRUCTION_SIZE;
}

//Inst 4
int back_n(PCB *p, int n)
{
    int backwd = n*INSTRUCTION_SIZE;

    while(backwd > 0)
    {
        
        if(p->PC == 0 && backwd > 0 && p->pageLocation->head->previous != NULL)
        {
            p->pageLocation->head = p->pageLocation->head->previous;
            p->PC = PAGE_SIZE;
        }

        if(p->PC == 0 && backwd > 0 && p->pageLocation->head->previous == NULL)
        {
            printf("Memory Acess Violation (PI: %d)\n", p->P_ID);
            return -1;
        }
        
        p->PC--;
        backwd--;
    }
    return 0;
}

//Inst 5
int forwd_n(PCB *p, int n)
{
    if (p->PC + n*INSTRUCTION_SIZE > p->n_of_instruct*INSTRUCTION_SIZE)
    {
        printf("Memory Acess Violation (PI: %d)\n", p->P_ID);
        return -1;
    }
    else
    {
        if (p->PC + (n*INSTRUCTION_SIZE) < 9)
        {
            p->PC += n*INSTRUCTION_SIZE;
        }
        else
        {
            int forwd = n*INSTRUCTION_SIZE;
            int resto = PAGE_SIZE - 1 - p->PC;
            while (forwd >= PAGE_SIZE - 1 )
            {
                resto = PAGE_SIZE - p->PC;
                forwd -= resto;
                p->PC = 0;
                pageList_Next(p->pageLocation);
            }
            p->PC += forwd;
        }
    }    
    return 0;
}

//Inst 6
int if_x_n(PCB *p, int x, int n)
{
    if(x == 0)
        return forwd_n(p, n);
            
    else
        p->PC+=INSTRUCTION_SIZE;
    
    return 0;
}


// fork (escalonador.c)

// 8 9
void disk_op(PCB *p, int mem[MEMSIZE])
{
    if (p->block_instruction == 8) // escrever no disco
    {
        VAR_DISCO = mem[p->variablesPage + p->block_var -1];
    }
    else // ler do disco
    {
        mem[p->variablesPage + p->block_var -1] = VAR_DISCO;
    }
}

//Inst 10
void print_var(PCB *p, int mem[MEMSIZE],int var)
{
    int valor = mem[p->variablesPage*10 + var -1];
    printf("Process ID: %d \nPos Variavel: %d \nValor Var: %d\n", p->P_ID, var, valor);
    p->PC += INSTRUCTION_SIZE;
}

// Inst 11
void process_exit(PCB *p, pageSet *page, int mem[MEMSIZE])
{
    p->n_paginas--;
    page->listOfPages[p->variablesPage] = false; //Coloca a pagina disponivel
    int variables = p->variablesPage;
    for(int i = (variables*10); i < (variables*10)+10; i++)
    {
        mem[i] = NULL;
    }

    

    while (p->pageLocation->tail != NULL)
    {
        //coisas
        page->listOfPages[p->pageLocation->tail->element] = false;
        int trail = p->pageLocation->tail->element;
        for(int i = (trail*10); i < (trail*10)+10; i++)
        {
            mem[i] = NULL;
        }
        p->n_paginas--;
        p->pageLocation->tail = p->pageLocation->tail->previous;
    }
}
