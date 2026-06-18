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
	li $s6, 0 	
	li $s7, 0 	
	li $s5, 1 	
	lw $s4, 84($sp)
	li $t0, 1 	
	ble $s4, $t0, _L2
	li $s5, 0 	
_L2:
	li $s2, 0 	
	beq $s5, $s2, _L0
	move $v0, $s4
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
	li $t1, 1 	
	sub $s3, $s4, $t1
	sw $s3, 0($sp)
	jal fib

	move $s6, $v0
	li $t2, 2 	
	sub $s0, $s4, $t2
	sw $s0, 0($sp)
	jal fib

	move $s7, $v0
	add $s1, $s6, $s7
	move $v0, $s1
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
