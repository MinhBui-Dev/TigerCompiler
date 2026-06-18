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
	li $t1, 10 	
	sw $t1, 0($sp)
	jal add3

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
add0:
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
	lw $t3, 60($sp)
	move $v0, $t3
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
add1:
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
	lw $t4, 20($sp)
	lw $t5, 20($sp)
	sw $t5, 0($sp)
	jal add0

	move $t4, $v0
	sw $t4, 20($sp)
	lw $t6, 16($sp)
	lw $t7, 20($sp)
	li $t8, 1 	
	add $t6, $t7, $t8
	sw $t6, 16($sp)
	lw $t9, 16($sp)
	move $v0, $t9
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
add2:
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
	lw $t1, 20($sp)
	sw $t1, 0($sp)
	jal add1

	move $t0, $v0
	sw $t0, 20($sp)
	lw $t2, 16($sp)
	lw $t3, 20($sp)
	li $t4, 1 	
	add $t2, $t3, $t4
	sw $t2, 16($sp)
	lw $t5, 16($sp)
	move $v0, $t5
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
add3:
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
	lw $t6, 20($sp)
	lw $t7, 20($sp)
	sw $t7, 0($sp)
	jal add2

	move $t6, $v0
	sw $t6, 20($sp)
	lw $t8, 16($sp)
	lw $t9, 20($sp)
	li $t0, 1 	
	add $t8, $t9, $t0
	sw $t8, 16($sp)
	lw $t1, 16($sp)
	move $v0, $t1
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
