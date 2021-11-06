.data
erro: .asciiz "Erro. Não pode dividir por 0!"
stack: .asciiz "Stack\n"
traço: .asciiz "\n->"
input: .space 256
.text

main:
	#D� a frase inicial
	li $v0, 4
	la $a0, traço
	syscall

	#Pede o input
	li $v0, 8
	la $a0, input
	li $a1, 256
	syscall
	
	addi $t3, $zero, 32 #ASCII do espa�o
	addi $t4, $zero, 47 #ASCII da ultima opera��o
	addi $t6, $zero, 99 #ASCII da primeira minuscula
	addi $t7, $zero, 48 #ASCII do primeiro nmr


#Percorre a String e divide os operadores dos operandos
search:
	lb $t0, 0($a0)
	addi $a0, $a0, 1  #Incrementa o pointer
	lb $t1, 0($a0)
	beq $t0, $t3, search #se for um espaço continua a percorrer
	nop

	slt $a2, $t4, $t0   
	beq $a2, $zero, escolha #Se o ASCII for menor que o da ultima operação salta para a escolha da label
	nop

	bge $t0, $t6, escolha #Se o ASCII for maior que o da primeira minuscula salta para a escolha da label
	nop
	
	slt $a3, $t1, $t7
	beq $a3, $zero, acumula
	nop	

	addi $sp, $sp, -4
	addi $t0, $t0, -48 #Subtraindo 48 ao ASCII do numero obtemos o seu valor decimal
	sw $t0, 0($sp)
	j search
	nop

#escolhe entre as operações possiveis
escolha:
	addi $t5, $zero, 43
	beq $t0, $t5, soma
	nop 

	addi $t5, $zero, 45
	beq $t0, $t5, subtrai
	nop

	addi $t5, $zero, 42
	beq $t0, $t5, multiplica
	nop

	addi $t5, $zero, 47
	beq $t0, $t5, divide
	nop
	
	addi $t5, $zero, 99
	beq $t0, $t5, clear
	nop
	
	addi $t5, $zero, 100 #Como existem 2 instruções começadas por "d", vamos procurar a segunda letra
	beq $t0, $t5, D
	nop
	
	addi $t5, $zero, 110
	beq $t0, $t5, negate
	nop
	
	addi $t5, $zero, 115
	beq $t0, $t5, swap
	nop

	addi $t5, $zero, 111
	beq $t0, $t5, off
	nop
	
	beq $zero, $zero, resultado
	nop

D:
	lb $t0, 0($a0)
	
	addi $t5, $zero, 101
	beq $t0, $t5, delete
	nop
	
	addi $t5, $zero, 117
	beq $t0, $t5, duplica
	nop

soma:
	lw $a1, 0($sp)
	lw $a2, 4($sp)
	add $v0, $a1, $a2
	addi $sp, $sp, 4 #Faz avançar a stack
	sw $v0, 0($sp)
	j search
	nop

subtrai:
	lw $a1, 4($sp)
	lw $a2, 0($sp)
	sub $v0, $a1, $a2
	addi $sp, $sp, 4 #Faz avançar a stack	
	sw $v0, 0($sp)
	j search
	nop

multiplica:
	lw $a1, 0($sp)
	lw $a2, 4($sp)
	mult $a1, $a2
	mflo $v0
	addi $sp, $sp, 4 #Faz avançar a stack
	sw $v0, 0($sp)
	j search
	nop

clear:
	addi $sp, $sp, 4
	lw $a1, 0($sp)
	beq $a1, $zero, search
	nop
	j clear
	nop

divide:
	lw $a1, 4($sp)
	lw $a2, 0($sp)
	beq $a2, $zero, ERRO
	div $a1, $a2
	mflo $v0 #Quociente
	mfhi $a3 #Resto 
	addi $sp, $sp, 4 #Faz avançar a stack
	sw $v0, 0($sp)
	j search
	nop 

delete:
	addi $sp, $sp 4 #remove ultimo da stack
	j search
	nop
	
duplica:
	lw $a1, 0($sp) 
	move $v0, $a1 
	addi $sp, $sp, -4 
	sw $v0, 0($sp)
	j search
	nop

#So converte de positivo para negativo
negate:
	lw $a1, 0($sp)
	sub $v0, $zero, $a1
	sw $v0, 0($sp)
	j search
	nop

swap:
	lw $a1, 0($sp)
	move $at, $a1
	lw $a2, 4($sp)
	sw $a2, 0($sp)
	sw $at, 4($sp)
	j search
	nop
	
resultado:
	lw $a0, 0($sp)
	li $v0, 1
	la $a0, ($a0)
	syscall
	j main
	nop

off:
	li $v0, 10
	syscall
ERRO:
	li $v0, 4
	la $a0, erro
	syscall
	j main
	nop

acumula:
	addi $t0, $t0, -48
	addi $t1 $t1, -48  #Subtrai 48 ao ascii dos nmrs
	mulo $t9, $t0, 10  #multiplica o primeiro por 10
	add $v0, $t9, $t1 
	add $sp, $sp, -4
	sw $v0, 0($sp)
	addi $a0, $a0, 1
	j search
	nop
