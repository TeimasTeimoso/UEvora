#include <stdbool.h>
#include "pageList.h"

#define QUANTUM 4
#define N_OF_VARS 10
#define INSTRUCTION_SIZE 3



typedef struct process_control
{
    int P_ID;        
    int PC;                
    char program_state[8]; 
    int n_of_instruct;
    int start_time;
    int time_spent;
    bool finished;
    int instruction_set[100];
    int allocated;
    int variablesPage;
    int n_paginas;
    pageList *pageLocation;
    int block_instruction;
    int block_var;
} PCB;

PCB *new_PCB(int P_ID);
void get_instruction(PCB *p, int array[3]);
void set_x(PCB *p, int mem[MEMSIZE], int i, int j);
void set_n(PCB *p, int mem[MEMSIZE], int i, int n);
void inc_x(PCB *p, int mem[MEMSIZE], int x);
void dec_x(PCB *p, int mem[MEMSIZE], int x);
int back_n(PCB *p, int n);
int forwd_n(PCB *p, int n);
int if_x_n(PCB *p, int x, int n);
void print_var(PCB *p, int mem[MEMSIZE],int var);
void process_exit(PCB *p, pageSet *page, int mem[MEMSIZE]);
void disk_op(PCB *p, int mem[MEMSIZE]);
