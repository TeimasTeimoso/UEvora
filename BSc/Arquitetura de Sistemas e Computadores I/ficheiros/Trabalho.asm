
.data

	originalImage:		.asciiz "lena.gray"
	imageMean:       	.asciiz "lenaMean.gray"
	imageMedian:		.asciiz "lenaMedian.gray"
	
	# 512 * 512 = 262144
	originalBuffer:		.space 262144
	meanBuffer:		.space 262144
	medianBuffer:		.space 262144
	medianKernel:		.space 9


.text

main:
	
	la $a0, originalImage
	jal read_gray_image
	nop
	
	la $a0, originalBuffer
	la $a1, meanBuffer
	la $a2, 512
	la $a3, 512
	jal mean_filter
	nop

	
	la $a0, imageMean
	la $a1, meanBuffer
	li $a2, 262144
	jal write_gray_image
	nop
	
	la $a0, originalBuffer
	la $a1, medianBuffer
	la $a2, 512
	la $a3, 512
	jal median_filter
	nop
	
	la $a0, imageMedian
	la $a1, medianBuffer
	li $a2, 262144
	jal write_gray_image
	nop
	
	j END
	nop
	
###################################################################################################	
# Abre a imagem e lè a mesma para o buffer pretendido
#
# Recebe -> $a0 = nome da imagem a abrir
#
# Retorna -> $v0 = Endereço onde está a imagem
###################################################################################################
read_gray_image:
	
	#Abre o ficheiro
	li $a1, 0
	li $a2, 0 	  #read only
	li $v0, 13	  #open
	syscall 	  #v0 = file descriptor
	
	#Lê o ficheiro
	move $a0, $v0		#Coloca o file descriptor($v0) como argumento ($a0)
	la $a1, originalBuffer  #a1 = endereço do buffer para onde é lida a imagem
	li $a2, 262144		#a2 = tamanho, numero de bytes a ler
	li $v0, 14
	syscall
	
	move $v0, $a1		#retorna o endereço onde esta a imagem
	
	#Fecha o ficheiro
	li $v0, 16
	syscall
	
	jr $ra
	nop
	
###################################################################################################
# Escreve a imagem em disco num novo ficheiro
#
# Recebe -> $a0 = nome da imagem onde guardar; $a1 = endereço do buffer; $a2 = tamanho do buffer
#
# Não tem valor de retorno
###################################################################################################
write_gray_image:
	
	move $t1, $a1
	move $t2, $a2
	
	#Abre o ficheiro
	li $a1, 1
	li $a2, 0
	li $v0, 13
	syscall
	
	#Move os registos conforme a syscall de Write
	move $a0, $v0
	move $a1, $t1
	move $a2, $t2
	
	li $v0, 15	#Escreve o ficheiro
	syscall
	
	li $v0, 16	#Fecha o ficheiro
	syscall
	
	jr $ra
	nop
	
###################################################################################################
# Aplica o filtro de média usando convolução2D na matriz original
# 
# Recebe -> $a0 = buffer da matriz A; $a1 = buffer onde é guardada a matriz B; $a2 = Nº Colunas
#	 -> $a3 = Nº Linhas
#
# Não tem valor de retorno
#
# Registos usados:	$s0 = posicao do pixel
#		  	$s5 = posical final
#			$s1 = margem direita
#			$s3 = (Nº Colunas-1)
#			$s4 = novo valor do bit
#		
#			x1 x2 x3
#			x4 x  x5
#			x6 x7 x8
#
###################################################################################################
mean_filter:

	# Guardar os valores dos registos $sX, pois devem ser preservados
	addi $sp, $sp, -20
	sw $s0, 0($sp)
	sw $s1, 4($sp)
	sw $s3, 8($sp)
	sw $s4, 12($sp)
	sw $s5, 16($sp)
	
	# $s0 é a posição do primeiro pixel, Nº Colunas + 1 (2ª linha e 2ª coluna)
	addi $s0, $a2, 1       
	
	addi $s3, $a2, -1	# Nº Colunas-1
	
	# Contador da margem direta inicializado no primeiro valor da msm
	add $s1, $a2, $s3	# 2ª Linha, penultima coluna
	add $s1, $s1, $a0
	
	# Pixel da penultima coluna e penultina linha é (N ºLinhas-2) * Nº Colunas + (Nº Colunas-2)
	addi $t8, $a2, -2	# (Nº Linas-2)
	mul $s5, $t8, $a3	# (Nº Linhas-2)*Nº Colunas
	addi $t8, $a3, -2	# (N º Colunas - 2)
	add $s5, $s5, $t8 	# (Nº Linhas-2) * Nº Colunas + (Nº Colunas-2) Ultimo pixel
	add $s5, $a0, $s5
	
	# começa na primeira pos segunda linha e segunda coluna
	add $a0, $a0, $s0
	
	# o novo buffer tmb vai comecar na segunda linha e segunda coluna
	add $a1, $a1, $s0
	
	cycle:
	
	sub $a0, $a0, $a2	# Primeira linha, Segunda coluna
	#x1
	lbu $t0, -1($a0)
	add $s4, $zero, $t0

	#x2
	lbu $t0, 0($a0)
	add $s4, $s4, $t0
	
	#x3
	lbu $t0, 1($a0)
	add $s4, $s4, $t0
	
	
	add $a0, $a0, $a2	# Segunda linha, Segunda Coluna
	#x4
	lbu $t0, -1($a0)
	add $s4, $s4, $t0

	#x
	lbu $t0, 0($a0)
	add $s4, $s4, $t0 	
	
	#x5
	lbu $t0, 1($a0)
	add $s4, $s4, $t0
	

	add $a0, $a0, $a2	# Terceira linha, Terceira coluna
	#x6
	lbu $t0, -1($a0)
	add $s4, $s4, $t0
	
	#x7
	lbu $t0, 0($a0)
	add $s4, $s4, $t0
	
	#x8
	lbu $t0, 1($a0)
	add $s4, $s4, $t0
	
	# media
	div $s4, $s4, 9 	# 9 é o numero de valores da mascara
	
	# guarda o valor no novo buffer
	sb $s4, 0($a1)
	
	sub $a0, $a0, $s3	# Incrementa a posicao do buffer original
	add $a1, $a1, 1		# Incrementa a posicao do novo buffer
	
	# Verifica se a posicao para que vai entrar é uma margem
	beq $a0, $s1, border
	nop
	
	# Condicao de paragem
	blt $a0, $s5, cycle
	nop
	
	addi $sp, $sp, 20
	
	jr $ra
	nop
	
	border:
	# Caso seja margem vai avançar 2 posicoes
	# Uma pela margem esquerda e outra para a desejada
	# Isto acontece tmb no segundo buffer
	# Para a posicao ir para o sitio correto 
	addi $a0, $a0, 2	
	addi $a1, $a1, 2
	
	j cycle
	add $s1, $s1, $a2	# Incrementa para o valor da proxima margem

###################################################################################################
# Aplica o filtro de mediana a uma imagem
# Recebe -> $a0 = $a0 = buffer da matriz A; $a1 = buffer onde é guardada a matriz B; $a2 = Nº Colunas
#	    $a3 = Nº Linhas
#	    
# Registos usados -> $s0 a $s5 com os valores anteriores, pois assumimos que o filtro de média e mediana
#		     seriam aplicados á msm imagem
#		     $s6 = mascara de mediana
#
# Não tem valor de retorno
###################################################################################################	
median_filter:
	#como os registos $s estao preservado podemos continuar a usa-los
	addi $sp, $sp, -8
	sw $s6, 0($sp)
	sw $ra, 4($sp)

	
	# o array da mascara fica em $s6
	la $s6, medianKernel
	
	# começa na primeira pos segunda linha e segunda coluna
	add $a0, $a0, $s0
	
	# o novo buffer tmb vai comecar na segunda linha e segunda coluna
	add $a1, $a1, $s0	
	
	median_cycle:
	
	sub $a0, $a0, $a2	# Primeira linha, Segunda coluna
	#x1
	lbu $t0, -1($a0)
	sb $t0, 0($s6)

	#x2
	lbu $t0, 0($a0)
	sb $t0, 1($s6)
	
	#x3
	lbu $t0, 1($a0)
	sb $t0, 2($s6)
	
	
	add $a0, $a0, $a2	# Segunda linha, Segunda Coluna
	#x4
	lbu $t0, -1($a0)
	sb $t0, 3($s6)

	#x
	lbu $t0, 0($a0)
	sb $t0, 4($s6) 	
	
	#x5
	lbu $t0, 1($a0)
	sb $t0, 5($s6)
	

	add $a0, $a0, $a2	# Terceira linha, Terceira coluna
	#x6
	lbu $t0, -1($a0)
	sb $t0, 6($s6)
	
	#x7
	lbu $t0, 0($a0)
	sb $t0, 7($s6)
	
	#x8
	lbu $t0, 1($a0)
	sb $t0, 8($s6)

	jal sort
	nop
	
	# guarda o valor no novo buffer
	sb $v0, 0($a1)
	
	sub $a0, $a0, $s3	# Incrementa a posicao do buffer original
	add $a1, $a1, 1		# Incrementa a posicao do novo buffer
	
	#Verifica se a posicao para que vai entrar é uma margem
	beq $a0, $s1, median_border
	nop
	
	bne $a0, $s5, median_cycle
	nop
	
	lw $ra, 4($sp)
	addi $sp, $sp, 8
	jr $ra
	nop
	
	median_border:
	# Caso seja margem vai avançar 2 posicoes
	# Uma pela margem esquerda e outra para a desejada
	# Isto acontece tmb no segundo buffer
	# Para a posicao ir para o sitio correto 
	addi $a0, $a0, 2	
	addi $a1, $a1, 2
	
	j median_cycle
	add $s1, $s1, $a2	# Incrementa para o valor da proxima margem
	
	

###################################################################################################
# Funcao auxiliar sort que organiza a mascara obtida no corpo da median_filter
# Recebe -> $s6, com o a mascara de 9 posicoes
# Retorna -> A 5ª pos da mascara, isto é, a mediana
# $t0 e usado como contador da pos
# $t3 conta o numero de iteracoes
###################################################################################################
sort:	
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	add $t0, $s6, 8 	# O array tera 8 iteracoes ate ao fim
	li $t3, 0 		# Contador do numero de itercaoes
	
	sorting_cycle:
	beq $s6, $t0, out
	nop
	lbu $t1, 0($s6)
	lbu $t2, 1($s6)
	blt $t2, $t1, swap
	addi $s6, $s6, 1 
	addi $t3, $t3, 1 	# Incrementa o numero de iteracoes
	j sorting_cycle
	nop

	swap:
	sb $t1, 1($s6)
	sb $t2, 0($s6)
	sub $s6, $s6, $t3	# Volta ao inicio do array
	li $t3, 0 		# N de iteracoes volta a 0
	j sorting_cycle
	nop
	
	out:
	sub $s6, $s6, $t3	# Volta ao inicio do array
	lbu $v0, 4($s6)		# Devolve a mediana
	lw $ra, 0($sp)
	addi, $sp, $sp, 4
	jr $ra
	nop	

END:

