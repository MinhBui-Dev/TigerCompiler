.data

.text
.globl main
main:
	addi $sp, $sp, -112
	sw $fp, 108($sp)
	sw $ra, 104($sp)
	sw $s0, 100($sp)
	sw $s1, 96($sp)
	sw $s2, 92($sp)
	sw $s3, 88($sp)
	sw $s4, 84($sp)
	sw $s5, 80($sp)
	sw $s6, 76($sp)
	sw $s7, 72($sp)
	move $fp, $sp
	li $s0, 1 	
	li $s1, 2 	
	li $s2, 3 	
	li $s4, 4 	
	li $s3, 1 	
	bgt $s0, $s1, _L2
	li $s3, 0 	
_L2:
	li $s1, 0 	
	beq $s3, $s1, _L0
	li $s3, 1 	
	bgt $s0, $s2, _L5
	li $s3, 0 	
_L5:
	li $s0, 0 	
	beq $s3, $s0, _L3
	li $s3, 1 	
	j _L4
_L3:
	li $s3, 2 	
_L4:
	j _L1
_L0:
	li $s1, 1 	
	blt $s0, $s2, _L8
	li $s1, 0 	
_L8:
	li $s5, 0 	
	beq $s1, $s5, _L6
	li $s5, 1 	
	bge $s0, $s4, _L11
	li $s5, 0 	
_L11:
	li $s4, 0 	
	beq $s5, $s4, _L9
	li $s3, 4 	
	j _L10
_L9:
	li $s3, 8 	
_L10:
	j _L7
_L6:
	li $s3, 16 	
_L7:
_L1:
	li $v0, 1
	move $a0, $s3
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	li $v0, 0 	
	lw $s0, 72($sp)
	lw $s1, 76($sp)
	lw $s2, 80($sp)
	lw $s3, 84($sp)
	lw $s4, 88($sp)
	lw $s5, 92($sp)
	lw $s6, 96($sp)
	lw $s7, 100($sp)
	lw $ra, 104($sp)
	lw $fp, 108($sp)
	addi $sp, $sp, 112
	jr $ra
