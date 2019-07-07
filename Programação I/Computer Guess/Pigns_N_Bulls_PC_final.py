import random

# Lista onde estão todas as possibilidades de combinações
list_rand = []

# False enquanto o programa não adivinhar, True Quando o programa acertar
win = False

############################################################################################################################################################################################

############################################################################################################################################################################################
# Função:                    rand_num ->  Gera uma lista com " l " digitos todos diferentes 
#
# Argumentos:             l (inteiro) -> comprimento da lista
#
# Valor de retorno:      rand (lista de inteiros) -> lista de dígitos
############################################################################################################################################################################################

def rand_num(l):
     n = 0
     inicio= []
     rand = ""
     while n != l :
          x = int(random.random()*10)
          if x not in inicio:
               inicio.append(x)
               n = n+1
               #print(x)
     for i in range(len(inicio)):     
        w = str(inicio[i])
        rand = rand + w
     return rand

############################################################################################################################################################################################

############################################################################################################################################################################################
# Função:                        adivinhar -> função que escolhe aleatoriamente uma possibilidade na lista que contém todas
#
# Argumentos:                 lista(lista de listas) -> lista que contém todas as possibilidades
#
# Valor de retorno:          n(lista de inteiros) -> uma lista que representa uma possibilidade
############################################################################################################################################################################################

def adivinhar(lista):
     r = random.randint(0,len(lista)-1)
     n = lista[r]
     return n

############################################################################################################################################################################################

############################################################################################################################################################################################
# Função:                    retirar_rand -> retira da lista todas as possibilidades que contém pelo menos um dos algarismos da tentativa d
#
# Argumentos:             d (lista de inteiros)    -> tentativa do progama
#                                  lista (lista de listas)   -> lista que contém todas as possibilidades
#
# Valor de retorno:      não tem valor de retorno
############################################################################################################################################################################################

def retirar_rand(d, lista):
     remove = []
     for i in range(len(d)):
          for y in range(len(lista)):
               if d[i] in lista[y]:
                    if lista[y] not in remove:
                         remove. append(lista[y])
                    #lista.remove(lista[y])
     for x in range(len(remove)):
          lista.remove(remove[x])

############################################################################################################################################################################################

############################################################################################################################################################################################
# Função:                     retirar_rand_1 -> escolhe as possibilidades que contém pelo menos um dos algarismos da tentaviva e adiciona numa lista nova
#
# Argumentos:             d (lista de inteiros)    -> tentativa do progama
#                                  lista (lista de listas)   -> lista que contém todas as possibilidades
#
# Valor de retorno:      lista_nova (lista de listas -> lista que contém as novas possibilidades
############################################################################################################################################################################################

def retirar_rand_1(d, lista):
     lista_nova = []
     for y in range(len(lista)):
          if d[0] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[1] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[2] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[3] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
     return lista_nova

############################################################################################################################################################################################

############################################################################################################################################################################################
# Função:                     retirar_rand_2 -> escolhe as possibilidades que contém pelo menos dois dos algarismos da tentaviva e adiciona numa lista nova
#
# Argumentos:             d (lista de inteiros)    -> tentativa do progama
#                                  lista (lista de listas)   -> lista que contém todas as possibilidades
#
# Valor de retorno:      lista_nova (lista de listas -> lista que contém as novas possibilidades
############################################################################################################################################################################################

def retirar_rand_2(d, lista):
     lista_nova = []
     for y in range(len(lista)):
          if d[0] in lista[y] and d[1] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[0] in lista[y] and d[2] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[0] in lista[y] and d[3] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[1] in lista[y] and d[2] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[1] in lista[y] and d[3] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[2] in lista[y] and d[3] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
     return lista_nova

############################################################################################################################################################################################

############################################################################################################################################################################################
# Função:                    retirar_rand_3 -> escolhe as possibilidades que contém pelo menos três dos algarismos da tentaviva e adiciona numa lista nova
#
# Argumentos:             d (lista de inteiros)    -> tentativa do progama
#                                  lista (lista de listas)   -> lista que contém todas as possibilidades
#
# Valor de retorno:      lista_nova (lista de listas -> lista que contém as novas possibilidades
############################################################################################################################################################################################

def retirar_rand_3(d, lista):
     lista_nova = []
     for y in range(len(lista)):
          if d[0] in lista[y] and d[1] in lista[y] and d[2] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[0] in lista[y] and d[1] in lista[y] and d[3] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[0] in lista[y] and d[2] in lista[y] and d[3] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
          elif d[1] in lista[y] and d[2] in lista[y] and d[3] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
     return lista_nova

############################################################################################################################################################################################

############################################################################################################################################################################################
# Função:                     retirar_rand_4 -> escolhe as possibilidades que contém os quatro algarismos da tentaviva e adiciona numa lista nova
#
# Argumentos:             d (lista de inteiros)    -> tentativa do progama
#                                  lista (lista de listas)   -> lista que contém todas as possibilidades
#
# Valor de retorno:      lista_nova (lista de listas -> lista que contém as novas possibilidades
############################################################################################################################################################################################

def retirar_rand_4(d, lista):
     lista_nova = []
     for y in range(len(lista)):
          if d[0] in lista[y] and d[1] in lista[y] and d[2] in lista[y] and d[3] in lista[y]:
               if lista[y] not in lista_nova:
                    lista_nova. append(lista[y])
     return lista_nova

############################################################################################################################################################################################

############################################################################################################################################################################################
# Função:                     retirar_4 -> escolhe as possibilidades que contém os quatro algarismos da tentaviva e remove da lista das possibilidades
#
# Argumentos:             d (lista de inteiros)    -> tentativa do progama
#                                  lista (lista de listas)   -> lista que contém todas as possibilidades
#
# Valor de retorno:      lista_nova (lista de listas -> lista que contém as novas possibilidades
############################################################################################################################################################################################

def retirar_4(d,lista):
     remove = []
     for y in range(len(lista)):
          if d[0] in lista[y] and d[1] in lista[y] and d[2] in lista[y] and d[3] in lista[y]:
               if lista[y] not in remove:
                    remove.append(lista[y])
     for x in range(len(remove)):
          lista.remove(remove[x])
     return lista

############################################################################################################################################################################################

#Ciclo para gerar a lista completa com todas as possibilidades (neste caso são 5040)
l = 0
while l != 5040:
     r = rand_num(4)
     if r not in list_rand:
          list_rand.append(r)
          #lista_rand(r)
          l = l+1
#print(list_rand)

############################################################################################################################################################################################

#ciclo principal do jogo

#Número de tentativas          
nmr_tentativas = 1

#Lista com todas as tentativas
final = [ ]

while win == False:
     #print(len(list_rand))
     rand = adivinhar(list_rand)
     print(rand)
     pigs = int(input("quantos pigs?"))
     bulls = int(input("quantos bulls?"))


     if pigs + bulls == 0:
          retirar_rand(rand, list_rand)
          
     elif pigs + bulls == 1:
          list_rand = retirar_rand_1(rand, list_rand)
          list_rand.remove(rand)
          list_rand = retirar_4(rand, list_rand)
          
     elif pigs + bulls == 2:
          list_rand = retirar_rand_2(rand, list_rand)
          list_rand.remove(rand)
          list_rand = retirar_4(rand, list_rand)

     elif pigs + bulls == 3:
          list_rand = retirar_rand_3(rand, list_rand)
          list_rand.remove(rand)
          list_rand = retirar_4(rand, list_rand)

     elif pigs + bulls == 4:
          list_rand = retirar_rand_4(rand, list_rand)
          list_rand.remove(rand)

     if bulls == 4:
          win = True

     if pigs>0 and bulls>0:
          x = "T{a}: {b}, {c}T {d}P".format(a = nmr_tentativas, b = rand, c = bulls, d = pigs)
     elif bulls>0:
          x = "T{a}: {b}, {c}T".format(a=nmr_tentativas, b=rand, c=bulls)
     elif pigs>0:
          x = "T{a}: {b}, {d}P".format(a=nmr_tentativas, b=rand, d=pigs)
     else:
          x = "T{a}: {b}".format(a=nmr_tentativas, b=rand)
     final.append(x)
     nmr_tentativas = nmr_tentativas + 1
     
print("WIN! \nAs tentativas foram:")

for i in range(len(final)):
     print(final[i])

