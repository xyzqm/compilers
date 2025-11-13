.macro eval_comp(%branch, %res, %lf, %rt)
	%branch %lf, %rt, true
	li %res, 0
	j end
    true:
    	li %res, 1
    end:
.end_macro
	
main:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4
	eval_comp(beq, $v0, $t0, $v0)
	beq $v0, 1, L0
	j L1
L0:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4
	eval_comp(bne, $v0, $t0, $v0)
	beq $v0, 1, L2
	j L3
L2:
	li $v0 3
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
L3:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4
	eval_comp(ble, $v0, $t0, $v0)
	beq $v0, 1, L4
	j L5
L4:
	li $v0 4
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
L5:
L1:
	li $v0 15
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4
	eval_comp(bgt, $v0, $t0, $v0)
	beq $v0, 1, L6
	j L7
L6:
	li $v0 5
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
L7:
