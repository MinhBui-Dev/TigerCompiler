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
	lw $t3, _0_sum
	li $t4, 5 	
	add $s0, $t3, $t4
	lw $t5, _0_sum
	add $t5, $s0, $zero
	sw $t5, _0_sum
	lw $t6, _0_sum
	li $v0, 1
	move $a0, $t6
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	li $t7, 7 	
	sw $t7, 0($sp)
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
	lw $t0, _0_sum
	lw $t1, 60($sp)
	sub $s0, $t0, $t1
	lw $t2, _0_sum
	add $t2, $s0, $zero
	sw $t2, _0_sum
	lw $t3, _0_sum
	li $v0, 1
	move $a0, $t3
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
	lw $t0, _0_sum
	lw $t1, 60($sp)
	add $s0, $t0, $t1
	lw $t2, _0_sum
	add $t2, $s0, $zero
	sw $t2, _0_sum
	lw $t3, _0_sum
	li $v0, 1
	move $a0, $t3
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
