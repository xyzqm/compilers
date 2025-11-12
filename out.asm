main:
	li $v0 5
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 0($sp)
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
	li $v0 1
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 4($sp)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 2
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 8($sp)
	lw $t0 ($sp)
	addu $sp $sp 4
	mul $v0 $t0 $v0
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	sw $v0 4($sp)
	lw $v0 4($sp)
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
