#include "djikstra.h"

//Função que colada a flag a false de todos os aeroportos que "participaram" no algoritmo de dijkstra
void resetAux(Aux *aux)
{
    for (int i = 0; i < aux->ocupacao; i++)
    {
        aux->array[i]->flag = false;
        aux->array[i]->predecessor = NULL;
    }

    aux->ocupacao = 0; 
}


//Função que inicializa o Aeroporto de partida com a flag a false para o algoritmo de dijkstra
void ininitializeSingleSourcePartida(Aeroporto *partida, short horasChegada, short minutosChegada)
{
    partida->distance = 0;
    partida->predecessor = NULL;
    partida->horasChegada = horasChegada;
    partida->minutosChegada = minutosChegada;
    partida->flag = true;
}

//Função que inicializa um Aeroporto com a flag a false para o algoritmo de dijkstra
void ininitializeSingleSourceA(Aeroporto *a)
{
    a->distance = INFINITY;
    a->predecessor = NULL;
    a->flag = true;
}


//Funçõa que da os minutos de espera num aeroporto para apanhar o voo
short diferencaHoras(Aeroporto *Apartida, VooHash *voo)
{
    int mins;
    mins = voo->horas*MIN1H + voo->minutos;
    mins = mins - (Apartida->horasChegada*MIN1H + Apartida->minutosChegada);
    if (mins < 0)
    {
        mins += MIN24H;
    }
    return mins;
}


//Função que calcula a hora de chegada ao aeropordo de destino no seu gmt a partir do gmt do aeroporto de partida, e do tempo do voo
void assertGMT(Aeroporto *aDestino, Aeroporto *aPartida, VooHash *voo)
{
    short gmtH = aDestino->gmtHoras - aPartida->gmtHoras;
    short gmtM = aDestino->gmtMinutos - aPartida->gmtMinutos;

    aDestino->minutosChegada = voo->minutos + (voo->duracao % MIN1H) + gmtM;
    while (aDestino->minutosChegada >= MIN1H)
    {
        aDestino->minutosChegada -= MIN1H;
        gmtH ++;
    }

    //talvez faça falta :3
    while (aDestino->minutosChegada < 0)
    {
        aDestino->minutosChegada += MIN1H;
        gmtH --;
    }

    //neste caso não se adiciona a duração do voo pois é dado em minutos e descontado no ciclo while em cima
    aDestino->horasChegada = voo->horas + (voo->duracao / MIN1H) + gmtH;

    while (aDestino->horasChegada >= H24)
    {
        aDestino->horasChegada -= H24;
    }

    while (aDestino->horasChegada < 0)
    {
        aDestino->horasChegada += H24;
    }
}


//Função que aplica o relax
bool relax(Aeroporto *aPartida, Aeroporto *aDestino, VooHash *voo, int diff)
{
    if ((aPartida->distance + voo->duracao + diff) < aDestino->distance)
    {
        aDestino->distance = aPartida->distance + voo->duracao + diff;
        aDestino->predecessor = aPartida;

        assertGMT(aDestino, aPartida, voo);
        strcpy(aDestino->codeVOO, voo->codigo);

        return true;
    }
    return false;
    
}


//Função que aplica o alguritmo de Dijkstra
void dijkstra(HTVoos *hV, HashAeroportos *hA, char *aeroportoPartida, char *aeroportoDestino, short horasChegada, short minutosChegada, Aux *aux)
{
    min_heap *pQ = new_heap();

    Aeroporto *partida = findAeroporto(hA, aeroportoPartida);
    ininitializeSingleSourcePartida(partida, horasChegada, minutosChegada);
    insert(pQ, partida);
    Voo *vTemp;
    VooHash *vHashTemp;
    Aeroporto *AeroportoTemp;

    while (!emptyHeap(pQ))
    {
        partida = pop(pQ);
        if (strcmp(partida->codigo, aeroportoDestino) == 0)
        {
            destroyHeap(pQ);
            return;
        }
        
        vTemp = partida->lista->head;
        while (vTemp != NULL)
        {
            vHashTemp = findVoo(hV, vTemp->codVoo);
            AeroportoTemp = findAeroporto(hA, vHashTemp->destino);

            if (!AeroportoTemp->flag)
            {
                ininitializeSingleSourceA(AeroportoTemp);
                insert(pQ, AeroportoTemp);
                aux->array[aux->ocupacao] = AeroportoTemp;
                aux->ocupacao ++;
            }

            if (diferencaHoras(partida, vHashTemp) >= 30 || partida->distance == 0)
            {
                if(relax(partida, findAeroporto(hA,vHashTemp->destino), vHashTemp, diferencaHoras(partida, vHashTemp)))
                {
                    update(pQ, findAeroporto(hA,vHashTemp->destino));
                }
            }
            else if (diferencaHoras(partida, vHashTemp) < 30 && partida->distance != 0)
            {
                int diff = diferencaHoras(partida, vHashTemp) + MIN24H;
                if(relax(partida, findAeroporto(hA,vHashTemp->destino), vHashTemp, diff))
                {
                    update(pQ, findAeroporto(hA,vHashTemp->destino));
                }
            }
            
            vTemp = vTemp->next;
        }
    }
    destroyHeap(pQ);
}


//Função que faz print dos voos de forma recurseiva. A partir do aeroporto de destido, chega ao aeroporto de partida e faz prin dos voos por ordem
//Tem como argumento a hash table onde estão os Voos e o Aeroporto de destino
void printVoos(Aeroporto *chegada, HTVoos *hV)
{
    if (chegada->predecessor != NULL)
    {
        printVoos(chegada->predecessor, hV);

    }
    else
    {
        return;
    }
    
    printf("%-6s %-4s %-4s %02d:%02d %02d:%02d\n",chegada->codeVOO, chegada->predecessor->codigo, chegada->codigo, findVoo(hV, chegada->codeVOO)->horas, findVoo(hV, chegada->codeVOO)->minutos, chegada->horasChegada, chegada->minutosChegada); 
}

