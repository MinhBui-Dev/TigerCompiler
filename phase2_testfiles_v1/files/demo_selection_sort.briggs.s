.data

.text
.globl main
main:
	addi $sp, $sp, -160
	sw $fp, 156($sp)
	sw $ra, 152($sp)
	sw $s0, 148($sp)
	sw $s1, 144($sp)
	sw $s2, 140($sp)
	sw $s3, 136($sp)
	sw $s4, 132($sp)
	sw $s5, 128($sp)
	sw $s6, 124($sp)
	sw $s7, 120($sp)
	move $fp, $sp
	li $t0, 7 	
	sw $t0, 20($sp)
	li $t1, 2 	
	sw $t1, 24($sp)
	li $t2, 14 	
	sw $t2, 28($sp)
	li $t3, 24 	
	sw $t3, 32($sp)
	li $t4, 5 	
	sw $t4, 36($sp)
	li $t5, 6 	
	sw $t5, 40($sp)
	li $t6, 49 	
	sw $t6, 44($sp)
	li $t7, 33 	
	sw $t7, 48($sp)
	li $s0, 8 	
	li $s3, 0 	
_L0:
	li $t8, 1 	
	sub $s1, $s0, $t8
	bgt $s3, $s1, _L1
	add $s4, $s3, $zero
	li $t9, 1 	
	add $s1, $s3, $t9
	add $s5, $s1, $zero
_L2:
	li $t0, 1 	
	sub $s1, $s0, $t0
	bgt $s5, $s1, _L3
	sll $s5, $s5, 2
	lw $t1, 20($sp)
	li $t1, 0 	
	addi $t1, $sp, 0
	addi $t1, $t1, 20
	add $s5, $s5, $t1
	lw $s1, 0($s5)
	sll $s4, $s4, 2
	lw $t2, 20($sp)
	li $t2, 0 	
	addi $t2, $sp, 0
	addi $t2, $t2, 20
	add $s4, $s4, $t2
	lw $s2, 0($s4)
	li $s6, 1 	
	blt $s1, $s2, _L5
	li $s6, 0 	
_L5:
	li $s1, 0 	
	beq $s6, $s1, _L4
	add $s4, $s5, $zero
_L4:
	li $t3, 1 	
	add $s5, $s5, $t3
	j _L2
_L3:
	sll $s3, $s3, 2
	lw $t4, 20($sp)
	li $t4, 0 	
	addi $t4, $sp, 0
	addi $t4, $t4, 20
	add $s3, $s3, $t4
	lw $s1, 0($s3)
	add $s2, $s1, $zero
	sll $s4, $s4, 2
	lw $t5, 20($sp)
	li $t5, 0 	
	addi $t5, $sp, 0
	addi $t5, $t5, 20
	add $s4, $s4, $t5
	lw $s1, 0($s4)
	add $s1, $s1, $zero
	sll $s3, $s3, 2
	lw $t6, 20($sp)
	li $t6, 0 	
	addi $t6, $sp, 0
	addi $t6, $t6, 20
	add $s3, $s3, $t6
	sw $s1, 0($s3)
	sll $s4, $s4, 2
	lw $t7, 20($sp)
	li $t7, 0 	
	addi $t7, $sp, 0
	addi $t7, $t7, 20
	add $s4, $s4, $t7
	sw $s2, 0($s4)
	li $t8, 1 	
	add $s3, $s3, $t8
	j _L0
_L1:
	li $s3, 0 	
_L6:
	li $t9, 1 	
	sub $s1, $s0, $t9
	bgt $s3, $s1, _L7
	sll $s3, $s3, 2
	lw $t0, 20($sp)
	li $t0, 0 	
	addi $t0, $sp, 0
	addi $t0, $t0, 20
	add $s3, $s3, $t0
	lw $s1, 0($s3)
	li $v0, 1
	move $a0, $s1
	syscall
	li $v0, 11
	li $a0, 10
	syscall
	li $t1, 1 	
	add $s3, $s3, $t1
	j _L6
_L7:
	li $v0, 0 	
	lw $s0, 120($sp)
	lw $s1, 124($sp)
	lw $s2, 128($sp)
	lw $s3, 132($sp)
	lw $s4, 136($sp)
	lw $s5, 140($sp)
	lw $s6, 144($sp)
	lw $s7, 148($sp)
	lw $ra, 152($sp)
	lw $fp, 156($sp)
	addi $sp, $sp, 160
	jr $ra
