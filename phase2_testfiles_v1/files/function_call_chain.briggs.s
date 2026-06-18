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
	li $t0, 10 	
	sw $t0, 0($sp)
	jal add3

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
	lw $t0, 60($sp)
	move $v0, $t0
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
	lw $s0, 60($sp)
	sw $s0, 0($sp)
	jal add0

	move $s0, $v0
	li $t0, 1 	
	add $s0, $s0, $t0
	move $v0, $s0
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
add2:
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
	lw $s0, 60($sp)
	sw $s0, 0($sp)
	jal add1

	move $s0, $v0
	li $t0, 1 	
	add $s0, $s0, $t0
	move $v0, $s0
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
add3:
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
	lw $s0, 60($sp)
	sw $s0, 0($sp)
	jal add2

	move $s0, $v0
	li $t0, 1 	
	add $s0, $s0, $t0
	move $v0, $s0
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
