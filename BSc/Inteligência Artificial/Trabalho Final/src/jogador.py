from defines import *

# RETURN: bool
def terminal(tabuleiro):
    if(tabuleiro[0][0] == 24 and tabuleiro[0][1] == 24):
        if(VER_PRINTS == True):
                print("*** Empate ***")
        return True

    return tabuleiro[0][0] >= 25 or tabuleiro[0][1] >= 25


def terminalSemPecas(tabuleiro, jogador):
    inicioDaFila = P1_INICIO if jogador == 0 else P2_INICIO
     # oponente com 0 pecas nas casas
    casasJogaveis = []
    casasJogaveis = verificaDarPecas(tabuleiro, jogador, casasJogaveis) # obrigatorio dar pecas ao adversario

    if (not oponenteTemPecas(tabuleiro, jogador) and casasJogaveis == []):
            for i in range(NUMERO_DE_CASAS):
                tabuleiro[0][jogador] += tabuleiro[1][inicioDaFila+i]
                tabuleiro[1][inicioDaFila+i] = 0

            return True
    
    return False


# verifica se existe alguma casa com mais que 1 peça
# Verificar que isto ta mt martelado
def umaPecaJogavel(tabuleiro, jogador):
    inicioDaFila = P1_INICIO if jogador == 0 else P2_INICIO

    for i in range(NUMERO_DE_CASAS):
        if tabuleiro[1][inicioDaFila+i] > 1:
            return False

    return True


# verifica se o oponente tem peças
def oponenteTemPecas(tabuleiro, jogador):
    iniciodaFilaOponente = P2_INICIO if jogador == 0 else P1_INICIO

    for i in range(NUMERO_DE_CASAS):
        if tabuleiro[1][iniciodaFilaOponente+i] > 0:
            return True

    return False


def loop_2pecas(tabuleiro):
    # jogo em loop
    # 2 pecas, a 5 ou 6 casas de distancia
    if(tabuleiro[0][0] + tabuleiro[0][1] == 46): # e' quando sobram 2 pecas no tabuleiro
        peca1 = 0
        peca2 = 0
        for i in range(NUMERO_DE_CASAS):
            if (tabuleiro[1][i] == 1):
                peca1 += i
                break
        for i in range(NUMERO_DE_CASAS):
            if (tabuleiro[1][NUMERO_DE_CASAS + i] == 1):
                peca2 += (NUMERO_DE_CASAS + i)
                break

        if(peca2 - peca1 >= 5):
            # remover todas as pecas para o repetivo deposito
            for i in range(NUMERO_DE_CASAS):
                tabuleiro[0][0] += tabuleiro[1][i]
                tabuleiro[1][i] = 0
            for i in range(NUMERO_DE_CASAS):
                tabuleiro[0][1] += tabuleiro[1][NUMERO_DE_CASAS+i]
                tabuleiro[1][NUMERO_DE_CASAS+i] = 0


# verifica se o jogador consegue dar peças ao adversário
# Devolve as posicoes das casas em que o jogador consegue dar peças
def verificaDarPecas(tabuleiro, jogador, casasJogaveis):
    iniciodaFila = P1_INICIO if jogador == 0 else P2_INICIO

    for i in range(NUMERO_DE_CASAS):
        dist = (NUMERO_DE_CASAS-1) - i
        if tabuleiro[1][iniciodaFila+i] > dist:
            casasJogaveis.append(i)

    return casasJogaveis


def regras(tabuleiro, jogador):
    inicioDaFila = P1_INICIO if jogador == 0 else P2_INICIO
    casasJogaveis = []

    # oponente com 0 pecas nas casas
    if (not oponenteTemPecas(tabuleiro, jogador)):
        casasJogaveis = verificaDarPecas(tabuleiro, jogador, casasJogaveis) # obrigatorio dar pecas ao adversario
        
        if (casasJogaveis != []):
            return casasJogaveis

        else:
            # remover todas as pecas para o repetivo deposito
            for i in range(NUMERO_DE_CASAS):
                tabuleiro[0][jogador] += tabuleiro[1][inicioDaFila+i]
                tabuleiro[1][inicioDaFila+i] = 0

            return casasJogaveis


    # Mover casa com 1 peca
    #return true se existe uma peca jogavel
    if umaPecaJogavel(tabuleiro, jogador): 
        for i in range(NUMERO_DE_CASAS):
            if tabuleiro[1][inicioDaFila+i] == 1:
                casasJogaveis.append(i)

    else:
        for i in range(NUMERO_DE_CASAS):
            if tabuleiro[1][inicioDaFila+i] > 1:
                casasJogaveis.append(i)
    

    # jogador com 0 pecas
    if (casasJogaveis == []):
        casasJogaveis.append(0)

    return casasJogaveis


# RETURN: nº de peças a colecionar -> int
def captura(tabuleiro, jogador, casa):
    inicioFilaOposta = P2_INICIO if jogador == 0 else P1_INICIO
    # variavel boleana para saber se o jogador acabou 
    ladoOposto = (casa>NUMERO_DE_CASAS-1) if jogador == 0 else (casa<NUMERO_DE_CASAS)
    nDeSementes = tabuleiro[1][casa]

    # nao se encontra em condicoes de capturar
    if((nDeSementes<2) or (nDeSementes>3) or (not ladoOposto)):
        return 0

    tabuleiro[1][casa] = 0

    if casa > inicioFilaOposta:
        return nDeSementes + captura(tabuleiro, jogador, casa-1)

    return nDeSementes


# Altera o estado do board
# RETURN: o tabuleiro alterado
def jogada(tabuleiro, jogador, casa):
    inicioFila = P1_INICIO if jogador == 0 else P2_INICIO
    distribuidas = 0
    casaAtual = inicioFila+casa+1

    nDeSementes = tabuleiro[1][inicioFila+casa]
    # Retira as sementes da casa
    tabuleiro[1][inicioFila+casa] = 0

    while distribuidas < nDeSementes:
        
        # Se chegar ao fim da 2ª fila
        if casaAtual == len(tabuleiro[1]):
            casaAtual = 0

        if casaAtual == inicioFila+casa:
            distribuidas-=1 # salta a casa inicial
        else:
            tabuleiro[1][casaAtual]+=1

        distribuidas+=1
        casaAtual+=1

    # casa atual -1 pois ta uma csa â frente
    tabuleiro[0][jogador] += captura(tabuleiro, jogador, casaAtual-1)

    return tabuleiro