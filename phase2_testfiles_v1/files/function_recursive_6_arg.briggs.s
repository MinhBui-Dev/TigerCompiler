.data

.text
.globl main
main:
	addi $sp, $sp, -72
	sw $fp, 68($sp)
	sw $ra, 64($sp)
	sw $s0, 60($sp)
	sw $s1, 56($sp)
	sw $s2, 52($sp)
	sw $s3, 48($sp)
	sw $s4, 44($sp)
	sw $s5, 40($sp)
	sw $s6, 36($sp)
	sw $s7, 32($sp)
	move $fp, $sp
	li $t0, 11 	
	li $t1, 12 	
	li $t2, 13 	
	li $t0, 14 	
	li $t1, 15 	
	li $t2, 16 	
	sw $t0, 20($sp)
	sw $t1, 16($sp)
	sw $t2, 12($sp)
	sw $t0, 8($sp)
	sw $t1, 4($sp)
	sw $t2, 0($sp)
	jal compute

	move $s0, $v0
	li $v0, 1
	move $a0, $s0
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	li $v0, 0 	
	lw $s0, 32($sp)
	lw $s1, 36($sp)
	lw $s2, 40($sp)
	lw $s3, 44($sp)
	lw $s4, 48($sp)
	lw $s5, 52($sp)
	lw $s6, 56($sp)
	lw $s7, 60($sp)
	lw $ra, 64($sp)
	lw $fp, 68($sp)
	addi $sp, $sp, 72
	jr $ra
compute:
	addi $sp, $sp, -128
	sw $fp, 124($sp)
	sw $ra, 120($sp)
	sw $s0, 116($sp)
	sw $s1, 112($sp)
	sw $s2, 108($sp)
	sw $s3, 104($sp)
	sw $s4, 100($sp)
	sw $s5, 96($sp)
	sw $s6, 92($sp)
	sw $s7, 88($sp)
	move $fp, $sp
	add $s1, $s0, $s2
	add $s1, $s1, $t4
	add $s1, $s1, $s3
	add $s1, $s1, $t5
	add $s1, $s1, $s4
	add $s1, $s1, $zero
	li $t0, 1 	
	li $t1, 0 	
	ble $s1, $t1, _L1
	li $t2, 0 	
_L1:
	li $s1, 0 	
	beq $t6, $s1, _L0
	li $v0, 1 	
	lw $s0, 88($sp)
	lw $s1, 92($sp)
	lw $s2, 96($sp)
	lw $s3, 100($sp)
	lw $s4, 104($sp)
	lw $s5, 108($sp)
	lw $s6, 112($sp)
	lw $s7, 116($sp)
	lw $ra, 120($sp)
	lw $fp, 124($sp)
	addi $sp, $sp, 128
	jr $ra
_L0:
	li $t0, 1 	
	sub $t6, $s0, $t0
	li $t1, 2 	
	sub $s5, $s2, $t1
	li $t2, 3 	
	sub $t4, $t4, $t2
	li $t0, 4 	
	sub $s2, $s3, $t0
	li $t1, 5 	
	sub $s1, $t5, $t1
	li $t2, 6 	
	sub $s0, $s4, $t2
	sw $t6, 20($sp)
	sw $s5, 16($sp)
	sw $t4, 12($sp)
	sw $s2, 8($sp)
	sw $s1, 4($sp)
	sw $s0, 0($sp)
	jal compute

	move $s0, $v0
	li $t0, 1 	
	add $s0, $t0, $s0
	move $v0, $s0
	lw $s0, 88($sp)
	lw $s1, 92($sp)
	lw $s2, 96($sp)
	lw $s3, 100($sp)
	lw $s4, 104($sp)
	lw $s5, 108($sp)
	lw $s6, 112($sp)
	lw $s7, 116($sp)
	lw $ra, 120($sp)
	lw $fp, 124($sp)
	addi $sp, $sp, 128
	jr $ra
