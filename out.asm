main:
	li $v0 0
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 3
	lw $t0 ($sp)
	addu $sp $sp 4
	sub $v0 $t0 $v0
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 11
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
	li $v0 16
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 2
	lw $t0 ($sp)
	addu $sp $sp 4
	div $v0 $t0 $v0
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
	li $v0 2
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 3
	lw $t0 ($sp)
	addu $sp $sp 4
	mul $v0 $t0 $v0
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 7
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 3
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 4
	lw $t0 ($sp)
	addu $sp $sp 4
	mul $v0 $t0 $v0
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
