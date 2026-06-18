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
	li $t0, 7 	
	sw $t0, 0($sp)
	jal factorial

	move $s0, $v0
	li $v0, 1
	move $a0, $s0
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
	li $s2, 1 	
	lw $s1, 76($sp)
	li $t0, 0 	
	beq $s1, $t0, _L1
	li $s2, 0 	
_L1:
	li $s0, 0 	
	beq $s2, $s0, _L0
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
	li $t1, 1 	
	sub $s0, $s1, $t1
	sw $s0, 0($sp)
	jal factorial

	move $s2, $v0
	mul $s0, $s1, $s2
	move $v0, $s0
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
