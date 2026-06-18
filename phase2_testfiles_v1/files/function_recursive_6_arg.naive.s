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
	lw $t0, 28($sp)
	li $t1, 11 	
	li $t2, 12 	
	li $t3, 13 	
	li $t4, 14 	
	li $t5, 15 	
	li $t6, 16 	
	sw $t1, 20($sp)
	sw $t2, 16($sp)
	sw $t3, 12($sp)
	sw $t4, 8($sp)
	sw $t5, 4($sp)
	sw $t6, 0($sp)
	jal compute

	move $t0, $v0
	sw $t0, 28($sp)
	lw $t7, 28($sp)
	li $v0, 1
	move $a0, $t7
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
	lw $t9, 148($sp)
	lw $t0, 144($sp)
	add $t8, $t9, $t0
	sw $t8, 48($sp)
	lw $t2, 48($sp)
	lw $t3, 140($sp)
	add $t1, $t2, $t3
	sw $t1, 32($sp)
	lw $t5, 32($sp)
	lw $t6, 136($sp)
	add $t4, $t5, $t6
	sw $t4, 36($sp)
	lw $t8, 36($sp)
	lw $t9, 132($sp)
	add $t7, $t8, $t9
	sw $t7, 24($sp)
	lw $t1, 24($sp)
	lw $t2, 128($sp)
	add $t0, $t1, $t2
	sw $t0, 28($sp)
	lw $t4, 28($sp)
	add $t3, $t4, $zero
	sw $t3, 60($sp)
	li $t5, 1 	
	sw $t5, 80($sp)
	lw $t6, 60($sp)
	li $t7, 0 	
	ble $t6, $t7, _L1
	li $t8, 0 	
	sw $t8, 80($sp)
_L1:
	li $t9, 0 	
	sw $t9, 84($sp)
	lw $t0, 80($sp)
	lw $t1, 84($sp)
	beq $t0, $t1, _L0
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
	lw $t3, 148($sp)
	li $t4, 1 	
	sub $t2, $t3, $t4
	sw $t2, 68($sp)
	lw $t6, 144($sp)
	li $t7, 2 	
	sub $t5, $t6, $t7
	sw $t5, 72($sp)
	lw $t9, 140($sp)
	li $t0, 3 	
	sub $t8, $t9, $t0
	sw $t8, 64($sp)
	lw $t2, 136($sp)
	li $t3, 4 	
	sub $t1, $t2, $t3
	sw $t1, 56($sp)
	lw $t5, 132($sp)
	li $t6, 5 	
	sub $t4, $t5, $t6
	sw $t4, 52($sp)
	lw $t8, 128($sp)
	li $t9, 6 	
	sub $t7, $t8, $t9
	sw $t7, 44($sp)
	lw $t0, 76($sp)
	lw $t1, 68($sp)
	lw $t2, 72($sp)
	lw $t3, 64($sp)
	lw $t4, 56($sp)
	lw $t5, 52($sp)
	lw $t6, 44($sp)
	sw $t1, 20($sp)
	sw $t2, 16($sp)
	sw $t3, 12($sp)
	sw $t4, 8($sp)
	sw $t5, 4($sp)
	sw $t6, 0($sp)
	jal compute

	move $t0, $v0
	sw $t0, 76($sp)
	li $t8, 1 	
	lw $t9, 76($sp)
	add $t7, $t8, $t9
	sw $t7, 40($sp)
	lw $t0, 40($sp)
	move $v0, $t0
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
