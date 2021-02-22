import random
#------------------------------------------------------------------------Apresentação do jogo---------------------------------------------------------------------

print("O jogo que vamos jogar chama-se Pigs and Bulls.\nNeste jogo o utilizador deve acertar num numero de 4 algarismos, sem repetição.\nCaso o utilizador acerte num algarismo e na sua posição receberá um touro(1T), se só acertar num algarismo fora de sitio recebe um porco(1P).\nVamos começar! ")

#########################################################################Gerador de numero random de 4 digitios sem repetição###############################################
#n = contador de digitos                                                                                                                                                   #            
#y = gera um numero de 0 a 9 inteiro                                                                                                                                       #
#valor de saída = numero random de 4 algarismo                                                                                                                             #
############################################################################################################################################################################
def random_result():
    n = 0                                 
    vazio = [ ]
    result = ""
    while n<4:   
        y = int(10*random.random())
        #Loop para que não haja repetição de valores
        if y not in vazio:
            vazio.append(y)
            n = n+1
    for i in range(len(vazio)):
        #permite construir um numero de 4 digitos, pois o type é str e não int ou float
        w = str(vazio[i])
        result = result + w
    return result
##############################################################################Corpo do Jogo################################################################################
#tenta = input do jogador                                                                                                                                                 #
#acum = acumulador de T                                                                                                                                                   #
#acum_2 = acumulador de P                                                                                                                                                 #
#valor de saida = tentativas do jogador até acertar                                                                                                                       #
###########################################################################################################################################################################
def jogo():
    tenta = ""
    #chamada da função random
    result = random_result()
    final = [ ]
    nmr_tentativas = 1
    #print(result)
    #Loop que permite n tentativas
    while tenta != result:
        touro = 0
        porco = 0
        tenta = str(input("Código? "))
        #Caso acerte à primeira
        if tenta == result:
            print('Acertou!!!!')
            x = "T{a}: {b}, 4T".format(a=nmr_tentativas, b=tenta,)
        #Restantes tentativas até acertar
        else:
            #Permite contar o numero de digitos no sitio certo(Bulls)
            for i in range(len(result)):
                if tenta[i] == result[i]:
                    touro = touro +1
            #Permite contar o numero de digitos existentes mas fora de sitio(Pigs)
            for i in range(len(result)):
                if tenta[i] != result[i] and tenta[i] in result:
                    porco = porco +1
            if touro>0 and porco>0:
                print(touro,"T", " ", porco,"P")
                x = "T{a}: {b}, {c}T {d}P".format(a=nmr_tentativas, b=tenta, c=touro, d = porco)
            elif touro>0:
                print(touro,"T")
                x = "T{a}: {b}, {c}T".format(a=nmr_tentativas, b=tenta, c=touro)
            elif porco>0:
                print(porco,"P")
                x = "T{a}: {b}, {d}P".format(a=nmr_tentativas, b=tenta, d = porco)
            else:
                x = "T{a}: {b}".format(a=nmr_tentativas, b=tenta)
        final.append(x)
        nmr_tentativas = nmr_tentativas + 1
    print("As suas tentativas foram:")
    #Apresentação das jogadas até acertar
    for i in range(nmr_tentativas-1):
        print(final[i])
    #Educação fica sempre bem!
    polite = "Foi um prazer jogar consgo!"
    return polite
print(jogo())



 
                
