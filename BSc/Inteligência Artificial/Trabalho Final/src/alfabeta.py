# https://docs.python.org/3.8/library/copy.html
import copy
from operator import itemgetter
from jogador import *

# Funciona como o findAll em Prolog
# RETURN: list    
def jogadasPossiveis(tabuleiro, jogador):
    inicioFila = P1_INICIO if jogador == 0 else P2_INICIO

    listaDeEstados = []
    listaDeCasas = regras(copy.deepcopy(tabuleiro), jogador)

    for casa in listaDeCasas:
        listaDeEstados.append(jogada(copy.deepcopy(tabuleiro), jogador, casa))

    return listaDeEstados


# Cada peça no deposito vale 20
# Cada casa com 1 vale 10 e com 2 vale 15
def evalTab(tabuleiro, jogador, profundidade):
    
    inicioFila = P1_INICIO if jogador == 0 else P2_INICIO
    inicioFilaOponente = abs(inicioFila-NUMERO_DE_CASAS)
    
    if(tabuleiro[0][jogador] >= 25):
        return MAX-profundidade
    elif(tabuleiro[0][abs(jogador-1)] >= 25):
        return MIN+profundidade

    # Enfase Ganhar
    scoreJogador = tabuleiro[0][jogador] * 300
    scoreOponente = tabuleiro[0][abs(jogador-1)] * 200

    for i in range(inicioFila, inicioFila+NUMERO_DE_CASAS):
        if 1 <= tabuleiro[1][i] <= 2:
            scoreJogador -= 10

    for i in range(inicioFilaOponente, inicioFilaOponente+6):
        if 1 <= tabuleiro[1][i] <= 2:
            scoreOponente -= 10

    return scoreJogador - scoreOponente


#jogador tem de trocar
def alfabeta(tabuleiro, jogador, profundidade, maximiza, alfa, beta):
    aJogar = jogador if maximiza else abs(jogador-1)

    if(profundidade == 0 or terminal(tabuleiro) or terminalSemPecas(tabuleiro, aJogar)):
        heur = evalTab(tabuleiro, jogador, profundidade)

        return heur
    
    listaDeEstados = jogadasPossiveis(tabuleiro, aJogar)

    # maximiza
    if maximiza:
        maxEval = MIN
        for estadoSeguinte in listaDeEstados:
            eval = alfabeta(estadoSeguinte, jogador, profundidade-1, False, alfa, beta)
            maxEval = max(maxEval, eval)
            alfa = max(alfa, eval)

            if beta <= alfa:
                break
        
        return maxEval
 
    # minimiza
    minEval = MAX
    for estadoSeguinte in listaDeEstados:
        eval = alfabeta(estadoSeguinte, jogador, profundidade-1, True, alfa, beta)
        minEval = min(eval, minEval)
        beta = min(beta, eval)

        if beta <= alfa:
            break
        
    return minEval


### Uma ideia é avaliar as opções por si só e perceber quando a melhor
### Ordenando-as assim

# retorna a melhor jogada de 0 a 6
def melhorJogadaAlfaBeta(tabuleiro, jogador, profundidade):
    inicioFila = P1_INICIO if jogador == 0 else P2_INICIO

    listaDeEstados = []
    listaDeCasas = regras(copy.deepcopy(tabuleiro), jogador)

    # lista de estados tem como elementos tuplos de (pos, jogada(pos) -> estado, heur(estado) -> int)
    for casa in listaDeCasas:
        novoEstado = jogada(copy.deepcopy(tabuleiro), jogador, casa)
        listaDeEstados.append((casa, copy.deepcopy(novoEstado), evalTab(novoEstado, jogador, profundidade)))

    # não ha jogadas possiveis
    if len(listaDeEstados) == 0:
        return 0

    # ordena a lista pela heur maior para menor
    listaDeEstados.sort(reverse = True, key=itemgetter(2))

    casaMelhorJogada = listaDeEstados[0][0]
    scoreMelhorJogada = MIN

    # profundidade -1 e maximiza = false pois esta é a vez o oponente jogar, nos ja "jogamos"
    for estado in listaDeEstados:
        # passar o scoreMelhroJogad, pois ao passar o sempre o MIN, não permite
        # fazer o corte de forma eficiente
        novoScore = alfabeta(estado[1], jogador, profundidade-1, False, scoreMelhorJogada, MAX)

        if novoScore > scoreMelhorJogada:
            scoreMelhorJogada = novoScore
            casaMelhorJogada = estado[0]

    return casaMelhorJogada
