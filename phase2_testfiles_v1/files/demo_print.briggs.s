.data

.text
.globl main
main:
	addi $sp, $sp, -80
	sw $fp, 76($sp)
	sw $ra, 72($sp)
	sw $s0, 68($sp)
	sw $s1, 64($sp)
	sw $s2, 60($sp)
	sw $s3, 56($sp)
	sw $s4, 52($sp)
	sw $s5, 48($sp)
	sw $s6, 44($sp)
	sw $s7, 40($sp)
	move $fp, $sp
	li $s0, 10 	
	li $s2, 20 	
	li $t0, 2 	
	mul $s3, $s0, $t0
	sub $s1, $s2, $s0
	add $s0, $s0, $s2
	sw $s3, 8($sp)
	sw $s1, 4($sp)
	sw $s0, 0($sp)
	jal printout
	li $v0, 0 	
	lw $s0, 40($sp)
	lw $s1, 44($sp)
	lw $s2, 48($sp)
	lw $s3, 52($sp)
	lw $s4, 56($sp)
	lw $s5, 60($sp)
	lw $s6, 64($sp)
	lw $s7, 68($sp)
	lw $ra, 72($sp)
	lw $fp, 76($sp)
	addi $sp, $sp, 80
	jr $ra
printout:
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
	lw $t0, 68($sp)
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	lw $s0, 64($sp)
	li $v0, 1
	move $a0, $s0
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	lw $s1, 60($sp)
	li $v0, 1
	move $a0, $s1
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
