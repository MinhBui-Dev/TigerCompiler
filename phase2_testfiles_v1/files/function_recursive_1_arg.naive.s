.data

.text
.globl main
main:
	addi $sp, $sp, -64
	sw $fp, 60($sp)
	sw $ra, 56($sp)
	sw $s0, 52($sp)
	sw $s1, 48($sp)
	sw $s2, 44($sp)
	sw $s3, 40($sp)
	sw $s4, 36($sp)
	sw $s5, 32($sp)
	sw $s6, 28($sp)
	sw $s7, 24($sp)
	move $fp, $sp
	lw $t0, 20($sp)
	li $t1, 7 	
	sw $t1, 0($sp)
	jal factorial

	move $t0, $v0
	sw $t0, 20($sp)
	lw $t2, 20($sp)
	li $v0, 1
	move $a0, $t2
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	li $v0, 0 	
	lw $s0, 24($sp)
	lw $s1, 28($sp)
	lw $s2, 32($sp)
	lw $s3, 36($sp)
	lw $s4, 40($sp)
	lw $s5, 44($sp)
	lw $s6, 48($sp)
	lw $s7, 52($sp)
	lw $ra, 56($sp)
	lw $fp, 60($sp)
	addi $sp, $sp, 64
	jr $ra
factorial:
	addi $sp, $sp, -76
	sw $fp, 72($sp)
	sw $ra, 68($sp)
	sw $s0, 64($sp)
	sw $s1, 60($sp)
	sw $s2, 56($sp)
	sw $s3, 52($sp)
	sw $s4, 48($sp)
	sw $s5, 44($sp)
	sw $s6, 40($sp)
	sw $s7, 36($sp)
	move $fp, $sp
	lw $t3, 28($sp)
	li $t3, 1 	
	sw $t3, 28($sp)
	lw $t4, 76($sp)
	li $t5, 0 	
	beq $t4, $t5, _L1
	lw $t6, 28($sp)
	li $t6, 0 	
	sw $t6, 28($sp)
_L1:
	lw $t7, 20($sp)
	li $t7, 0 	
	sw $t7, 20($sp)
	lw $t8, 28($sp)
	lw $t9, 20($sp)
	beq $t8, $t9, _L0
	li $v0, 1 	
	lw $s0, 36($sp)
	lw $s1, 40($sp)
	lw $s2, 44($sp)
	lw $s3, 48($sp)
	lw $s4, 52($sp)
	lw $s5, 56($sp)
	lw $s6, 60($sp)
	lw $s7, 64($sp)
	lw $ra, 68($sp)
	lw $fp, 72($sp)
	addi $sp, $sp, 76
	jr $ra
_L0:
	lw $t0, 24($sp)
	lw $t1, 76($sp)
	li $t2, 1 	
	sub $t0, $t1, $t2
	sw $t0, 24($sp)
	lw $t3, 32($sp)
	lw $t4, 24($sp)
	sw $t4, 0($sp)
	jal factorial

	move $t3, $v0
	sw $t3, 32($sp)
	lw $t5, 16($sp)
	lw $t6, 76($sp)
	lw $t7, 32($sp)
	mul $t5, $t6, $t7
	sw $t5, 16($sp)
	lw $t8, 16($sp)
	move $v0, $t8
	lw $s0, 36($sp)
	lw $s1, 40($sp)
	lw $s2, 44($sp)
	lw $s3, 48($sp)
	lw $s4, 52($sp)
	lw $s5, 56($sp)
	lw $s6, 60($sp)
	lw $s7, 64($sp)
	lw $ra, 68($sp)
	lw $fp, 72($sp)
	addi $sp, $sp, 76
	jr $ra
