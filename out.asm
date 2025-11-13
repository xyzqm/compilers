.macro eval_comp(%branch, %res, %lf, %rt)
	%branch %lf, %rt, true
	li %res, 0
	j end
    true:
    	li %res, 1
    end:
.end_macro
	
main:
	li $v0 0
	subu $sp $sp 4
	sw $v0 ($sp)
L0:
	lw $v0 0($sp)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 15
	lw $t0 ($sp)
	addu $sp $sp 4
	eval_comp(ble, $v0, $t0, $v0)
	beq $v0, $zero, L2
	lw $v0 0($sp)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4
	add $v0 $t0 $v0
	sw $v0 0($sp)
	lw $v0 0($sp)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 5
	lw $t0 ($sp)
	addu $sp $sp 4
	eval_comp(beq, $v0, $t0, $v0)
	beq $v0, 1, L3
	j L4
L3:
	j L1
L4:
	lw $v0 0($sp)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 12
	lw $t0 ($sp)
	addu $sp $sp 4
	eval_comp(bgt, $v0, $t0, $v0)
	beq $v0, 1, L5
	j L6
L5:
	j L2
L6:
	lw $v0 0($sp)
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
L1:
	j L0
L2:
