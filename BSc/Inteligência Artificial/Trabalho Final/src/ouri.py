import random
import time
import sys
from minimax import *
from alfabeta import melhorJogadaAlfaBeta

#https://www.youtube.com/watch?v=l-hh51ncgDI
# ###################################
#   Representacao do Tabuleiro      
#       -------------------
# (0,0) | 5| 4| 3| 2| 1| 0| 
#       | 6| 7| 8| 9|10|11| (0,1)
#       -------------------
#####################################

##### No nosso jogo, o IA é sempre o jogador da fila 0 (zero)
##### Enquanto que o adversário (nós ou outro IA) é o jogador da fila 1

# Funcao que da print do tabuleiro
def mostraTabuleiro(tabuleiro):
    dep = tabuleiro[0]
    casa = tabuleiro[1]

    representacaoTab = ("-------------------------------------\n"
                        "|     | {p5:2d}| {p4:2d}| {p3:2d}| {p2:2d}| {p1:2d}| {p0:2d}|     |\n"
                        "|{d1:2d}   -------------------------  {d2:2} |\n" 
                        "|     | {p6:2d}| {p7:2d}| {p8:2d}| {p9:2d}| {p10:2d}| {p11:2d}|     |\n"
                        "-------------------------------------")

    print(representacaoTab.format(d1 = dep[0], d2 = dep[1],
                            p0 = casa[0], p1 = casa[1], p2=casa[2], p3 = casa[3], p4 = casa[4], p5=casa[5],
                            p6 = casa[6], p7 = casa[7], p8=casa[8], p9 = casa[9], p10 = casa[10], p11=casa[11]))

if __name__ == "__main__":

    tabuleiro = ([0,0],[4,4,4,4,4,4,4,4,4,4,4,4])

    comeca = sys.argv[1]
    dificuldade = sys.argv[2]

    if(dificuldade == "a"):
        profundidade = MINIMAX_FACIL
        profundidadeAB = ALFA_BETA_FACIL

    elif(dificuldade == "b"):
        profundidade = MINIMAX_MEDIO
        profundidadeAB = ALFA_BETA_MEDIO
    else:
        profundidade = MINIMAX_DIFICIL
        profundidadeAB = ALFA_BETA_DIFICIL


    if(comeca == "-s"):
        jogador = 0
    
    elif(comeca =="-p"):
        jogador = 1
    
    if(IAvsIA == False):
        while not terminal(tabuleiro):
            jogador = abs((jogador-1))

            if(VER_PRINTS == True):
                mostraTabuleiro(tabuleiro)

            if(jogador == 0): # jogador IA
                if(VER_PRINTS == True):         
                    startTime = time.time()     

                if(COLDSTART <= 0):
                    #jogadaAI = melhorJogadaMinMax(tabuleiro, jogador, profundidade) # minMax
                    jogadaAI = melhorJogadaAlfaBeta(tabuleiro, jogador, profundidadeAB) # alphaBeta
                
                else: # IA joga random
                    listaRegras = regras(tabuleiro, jogador)
                    jogadaAI = random.choice(listaRegras)
                    COLDSTART -= 1

                jogada(tabuleiro, jogador, jogadaAI)
                print(jogadaAI + 1)

                if(VER_PRINTS == True):         
                    print("Tempo da escolha:", time.time() - startTime, "segundos\n")   

            else: # jogador                
                listaRegras = regras(tabuleiro, jogador)
                listaRegras = [x+1 for x in listaRegras]
                if(listaRegras == []):
                    listaRegras = [0]
                pos = -5
                while (pos not in listaRegras):
                    if(VER_PRINTS == True):
                        print("Jogador: escolha a posicao! " + str(listaRegras))

                    pos = int(input())
                    
                pos = int(pos) - 1
                if pos != -1:
                    jogada(tabuleiro, jogador, pos)
            
            loop_2pecas(tabuleiro)
            terminalSemPecas(tabuleiro, jogador)
    
    else: # IA vs IA -> usado para fazer testes (tempo, comparar minMax e AlphaBeta, ...)
        while not terminal(tabuleiro):
            jogador = abs((jogador-1))
            startTime = time.time()
            
            if(VER_PRINTS == True):
                mostraTabuleiro(tabuleiro)
            
            if(jogador == 0): # jogador IA 1
                if(COLDSTART <= 0):
                    #jogadaAI = melhorJogadaMinMax(tabuleiro, jogador, profundidade) # minMax
                    jogadaAI = melhorJogadaAlfaBeta(tabuleiro, jogador, profundidadeAB) # alphaBeta
                
                else: # IA joga random
                    listaRegras = regras(tabuleiro, jogador)
                    jogadaAI = random.choice(listaRegras)
                    COLDSTART -= 1

                jogada(tabuleiro, jogador, jogadaAI)
                print("IA 1 (AB): " + str(jogadaAI + 1))
                
            else: # jogador IA 2
                if(COLDSTART <= 0):
                    jogadaAI = melhorJogadaMinMax(tabuleiro, jogador, profundidade) # minMax
                    #jogadaAI = melhorJogadaAlfaBeta(tabuleiro, jogador, profundidadeAB) # alphaBeta
                
                else: # IA joga random
                    listaRegras = regras(tabuleiro, jogador)
                    jogadaAI = random.choice(listaRegras)
                    COLDSTART -= 1

                jogada(tabuleiro, jogador, jogadaAI)
                print("IA 2 (mM): " + str(jogadaAI + 1))

            loop_2pecas(tabuleiro)
            terminalSemPecas(tabuleiro, jogador)
            print("Tempo da escolha:", time.time() - startTime, "segundos\n")

        print(("Alfa-Beta" if tabuleiro[0][0] > tabuleiro[0][1] else "Minimax") + " venceu!")


    mostraTabuleiro(tabuleiro)
