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
	lw $t0, 28($sp)
	li $t0, 1 	
	sw $t0, 28($sp)
	lw $t1, 36($sp)
	li $t1, 2 	
	sw $t1, 36($sp)
	lw $t2, 44($sp)
	li $t2, 3 	
	sw $t2, 44($sp)
	lw $t3, 56($sp)
	li $t3, 4 	
	sw $t3, 56($sp)
	lw $t4, 48($sp)
	li $t4, 1 	
	sw $t4, 48($sp)
	lw $t5, 28($sp)
	lw $t6, 36($sp)
	bgt $t5, $t6, _L2
	lw $t7, 48($sp)
	li $t7, 0 	
	sw $t7, 48($sp)
_L2:
	lw $t8, 32($sp)
	li $t8, 0 	
	sw $t8, 32($sp)
	lw $t9, 48($sp)
	lw $t0, 32($sp)
	beq $t9, $t0, _L0
	lw $t1, 40($sp)
	li $t1, 1 	
	sw $t1, 40($sp)
	lw $t2, 28($sp)
	lw $t3, 44($sp)
	bgt $t2, $t3, _L5
	lw $t4, 40($sp)
	li $t4, 0 	
	sw $t4, 40($sp)
_L5:
	lw $t5, 20($sp)
	li $t5, 0 	
	sw $t5, 20($sp)
	lw $t6, 40($sp)
	lw $t7, 20($sp)
	beq $t6, $t7, _L3
	lw $t8, 52($sp)
	li $t8, 1 	
	sw $t8, 52($sp)
	j _L4
_L3:
	lw $t9, 52($sp)
	li $t9, 2 	
	sw $t9, 52($sp)
_L4:
	j _L1
_L0:
	lw $t0, 24($sp)
	li $t0, 1 	
	sw $t0, 24($sp)
	lw $t1, 28($sp)
	lw $t2, 44($sp)
	blt $t1, $t2, _L8
	lw $t3, 24($sp)
	li $t3, 0 	
	sw $t3, 24($sp)
_L8:
	lw $t4, 64($sp)
	li $t4, 0 	
	sw $t4, 64($sp)
	lw $t5, 24($sp)
	lw $t6, 64($sp)
	beq $t5, $t6, _L6
	lw $t7, 68($sp)
	li $t7, 1 	
	sw $t7, 68($sp)
	lw $t8, 28($sp)
	lw $t9, 56($sp)
	bge $t8, $t9, _L11
	lw $t0, 68($sp)
	li $t0, 0 	
	sw $t0, 68($sp)
_L11:
	lw $t1, 60($sp)
	li $t1, 0 	
	sw $t1, 60($sp)
	lw $t2, 68($sp)
	lw $t3, 60($sp)
	beq $t2, $t3, _L9
	lw $t4, 52($sp)
	li $t4, 4 	
	sw $t4, 52($sp)
	j _L10
_L9:
	lw $t5, 52($sp)
	li $t5, 8 	
	sw $t5, 52($sp)
_L10:
	j _L7
_L6:
	lw $t6, 52($sp)
	li $t6, 16 	
	sw $t6, 52($sp)
_L7:
_L1:
	lw $t7, 52($sp)
	li $v0, 1
	move $a0, $t7
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
