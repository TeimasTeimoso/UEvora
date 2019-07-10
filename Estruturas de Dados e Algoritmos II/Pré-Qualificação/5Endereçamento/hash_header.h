/*
É usado o tamanho 170003 pois para garantir
que o fator de carga da hastable é inferior a 60%
Da sua capacidade e é um numero primo
*/
#define SIZE 170003

typedef struct cell Cell;

void hashtable_init(Cell *hash[SIZE]);
int hashcode(int adress);
int find_pos(int adress, Cell *hash[SIZE]);
void insert(int adress, int interface, Cell *hash[SIZE]);
int search(int adress, Cell *hash[SIZE]);
void hashtable_destroy(Cell *hash[SIZE]);