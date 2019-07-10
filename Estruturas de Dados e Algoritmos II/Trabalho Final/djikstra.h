#include "pQ.h"
#include "hashTableVoos.h"


#define INFINITY 9999999
#define MIN24H 1440
#define MIN1H 60
#define H24 24
#define AEROPORTOS200000 200000

//Estrutura auxiliar utilizada para que o algoritmo seja mais rápido, pois não irá precisar de percurrer a hash toda
typedef struct Aux
{
    int ocupacao;
    Aeroporto *array[AEROPORTOS200000];
}Aux;

void resetAux(Aux *aux);
void ininitializeSingleSourcePartida(Aeroporto *partida, short horasChegada, short minutosChegada);
void ininitializeSingleSourceA(Aeroporto *a);
short diferencaHoras(Aeroporto *Apartida, VooHash *voo);
void assertGMT(Aeroporto *aDestino, Aeroporto *aPartida, VooHash *voo);
bool relax(Aeroporto *aPartida, Aeroporto *aDestino, VooHash *voo, int diff);
void dijkstra(HTVoos *hV, HashAeroportos *hA, char *aeroportoPartida, char *aeroportoDestino, short horasChegada, short minutosChegada, Aux *aux);
void printVoos(Aeroporto *chegada, HTVoos *hV);