.data
_0_result: .word 0

.text
.globl main
main:
	addi $sp, $sp, -56
	sw $fp, 52($sp)
	sw $ra, 48($sp)
	sw $s0, 44($sp)
	sw $s1, 40($sp)
	sw $s2, 36($sp)
	sw $s3, 32($sp)
	sw $s4, 28($sp)
	sw $s5, 24($sp)
	sw $s6, 20($sp)
	sw $s7, 16($sp)
	move $fp, $sp
	lw $t0, _0_result
	li $t1, 12 	
	sw $t1, 0($sp)
	jal fib

	move $t0, $v0
	sw $t0, _0_result
	lw $t2, _0_result
	li $v0, 1
	move $a0, $t2
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	li $v0, 0 	
	lw $s0, 16($sp)
	lw $s1, 20($sp)
	lw $s2, 24($sp)
	lw $s3, 28($sp)
	lw $s4, 32($sp)
	lw $s5, 36($sp)
	lw $s6, 40($sp)
	lw $s7, 44($sp)
	lw $ra, 48($sp)
	lw $fp, 52($sp)
	addi $sp, $sp, 56
	jr $ra
fib:
	addi $sp, $sp, -84
	sw $fp, 80($sp)
	sw $ra, 76($sp)
	sw $s0, 72($sp)
	sw $s1, 68($sp)
	sw $s2, 64($sp)
	sw $s3, 60($sp)
	sw $s4, 56($sp)
	sw $s5, 52($sp)
	sw $s6, 48($sp)
	sw $s7, 44($sp)
	move $fp, $sp
	lw $t3, 36($sp)
	li $t3, 0 	
	sw $t3, 36($sp)
	lw $t4, 40($sp)
	li $t4, 0 	
	sw $t4, 40($sp)
	lw $t5, 32($sp)
	li $t5, 1 	
	sw $t5, 32($sp)
	lw $t6, 84($sp)
	li $t7, 1 	
	ble $t6, $t7, _L2
	lw $t8, 32($sp)
	li $t8, 0 	
	sw $t8, 32($sp)
_L2:
	lw $t9, 24($sp)
	li $t9, 0 	
	sw $t9, 24($sp)
	lw $t0, 32($sp)
	lw $t1, 24($sp)
	beq $t0, $t1, _L0
	lw $t2, 84($sp)
	move $v0, $t2
	lw $s0, 44($sp)
	lw $s1, 48($sp)
	lw $s2, 52($sp)
	lw $s3, 56($sp)
	lw $s4, 60($sp)
	lw $s5, 64($sp)
	lw $s6, 68($sp)
	lw $s7, 72($sp)
	lw $ra, 76($sp)
	lw $fp, 80($sp)
	addi $sp, $sp, 84
	jr $ra
	j _L1
_L0:
	lw $t3, 28($sp)
	lw $t4, 84($sp)
	li $t5, 1 	
	sub $t3, $t4, $t5
	sw $t3, 28($sp)
	lw $t6, 36($sp)
	lw $t7, 28($sp)
	sw $t7, 0($sp)
	jal fib

	move $t6, $v0
	sw $t6, 36($sp)
	lw $t8, 16($sp)
	lw $t9, 84($sp)
	li $t0, 2 	
	sub $t8, $t9, $t0
	sw $t8, 16($sp)
	lw $t1, 40($sp)
	lw $t2, 16($sp)
	sw $t2, 0($sp)
	jal fib

	move $t1, $v0
	sw $t1, 40($sp)
	lw $t3, 20($sp)
	lw $t4, 36($sp)
	lw $t5, 40($sp)
	add $t3, $t4, $t5
	sw $t3, 20($sp)
	lw $t6, 20($sp)
	move $v0, $t6
	lw $s0, 44($sp)
	lw $s1, 48($sp)
	lw $s2, 52($sp)
	lw $s3, 56($sp)
	lw $s4, 60($sp)
	lw $s5, 64($sp)
	lw $s6, 68($sp)
	lw $s7, 72($sp)
	lw $ra, 76($sp)
	lw $fp, 80($sp)
	addi $sp, $sp, 84
	jr $ra
_L1:
