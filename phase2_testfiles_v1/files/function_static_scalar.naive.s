.data
_0_sum: .word 0

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
	lw $t0, _0_sum
	li $t0, 5 	
	sw $t0, _0_sum
	lw $t1, _0_sum
	li $v0, 1
	move $a0, $t1
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	li $t2, 10 	
	sw $t2, 0($sp)
	jal addToSum
	lw $t3, 20($sp)
	lw $t4, _0_sum
	li $t5, 5 	
	add $t3, $t4, $t5
	sw $t3, 20($sp)
	lw $t6, _0_sum
	lw $t7, 20($sp)
	add $t6, $t7, $zero
	sw $t6, _0_sum
	lw $t8, _0_sum
	li $v0, 1
	move $a0, $t8
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	li $t9, 7 	
	sw $t9, 0($sp)
	jal subFromSum
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
subFromSum:
	addi $sp, $sp, -60
	sw $fp, 56($sp)
	sw $ra, 52($sp)
	sw $s0, 48($sp)
	sw $s1, 44($sp)
	sw $s2, 40($sp)
	sw $s3, 36($sp)
	sw $s4, 32($sp)
	sw $s5, 28($sp)
	sw $s6, 24($sp)
	sw $s7, 20($sp)
	move $fp, $sp
	lw $t0, 16($sp)
	lw $t1, _0_sum
	lw $t2, 60($sp)
	sub $t0, $t1, $t2
	sw $t0, 16($sp)
	lw $t3, _0_sum
	lw $t4, 16($sp)
	add $t3, $t4, $zero
	sw $t3, _0_sum
	lw $t5, _0_sum
	li $v0, 1
	move $a0, $t5
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	lw $s0, 20($sp)
	lw $s1, 24($sp)
	lw $s2, 28($sp)
	lw $s3, 32($sp)
	lw $s4, 36($sp)
	lw $s5, 40($sp)
	lw $s6, 44($sp)
	lw $s7, 48($sp)
	lw $ra, 52($sp)
	lw $fp, 56($sp)
	addi $sp, $sp, 60
	jr $ra
addToSum:
	addi $sp, $sp, -60
	sw $fp, 56($sp)
	sw $ra, 52($sp)
	sw $s0, 48($sp)
	sw $s1, 44($sp)
	sw $s2, 40($sp)
	sw $s3, 36($sp)
	sw $s4, 32($sp)
	sw $s5, 28($sp)
	sw $s6, 24($sp)
	sw $s7, 20($sp)
	move $fp, $sp
	lw $t6, 16($sp)
	lw $t7, _0_sum
	lw $t8, 60($sp)
	add $t6, $t7, $t8
	sw $t6, 16($sp)
	lw $t9, _0_sum
	lw $t0, 16($sp)
	add $t9, $t0, $zero
	sw $t9, _0_sum
	lw $t1, _0_sum
	li $v0, 1
	move $a0, $t1
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	lw $s0, 20($sp)
	lw $s1, 24($sp)
	lw $s2, 28($sp)
	lw $s3, 32($sp)
	lw $s4, 36($sp)
	lw $s5, 40($sp)
	lw $s6, 44($sp)
	lw $s7, 48($sp)
	lw $ra, 52($sp)
	lw $fp, 56($sp)
	addi $sp, $sp, 60
	jr $ra
