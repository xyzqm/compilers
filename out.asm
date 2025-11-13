.macro eval_comp(%branch, %res, %lf, %rt)
	%branch %lf, %rt, true
	li %res, 0
	j end
    true:
    	li %res, 1
    end:
.end_macro

.macro push(%reg)
	subu $sp $sp 4
	sw %reg ($sp)
.end_macro

.macro pop(%reg)
	lw %reg ($sp)
	addu $sp $sp 4
.end_macro

move $fp, $sp
	
main:
	li $v0 0
	# writing to j
	push($v0)
	# done writing to j
	# Initialize loop variable i
	li $v0 0
	# writing to i
	push($v0)
	# done writing to i
	# Done initializing loop variable
	li $v0 10
	# writing to V0
	push($v0)
	# done writing to V0
L0:
	lw $v0 -8($fp)
	push($v0)
	lw $v0 -12($fp)
	pop($t0)
	eval_comp(bgt, $v0, $t0, $v0)
	beq $v0, 1, L3
	lw $v0 -8($fp)
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
	lw $v0 -8($fp)
	# writing to j
	sw $v0 -4($fp)
	# done writing to j
	lw $v0 -4($fp)
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
	j L4
L3:
	j L2
L4:
	# continue label
L1:
	lw $v0 -8($fp)
	push($v0)
	li $v0 1
	pop($t0)
	add $v0 $t0 $v0
	# writing to i
	sw $v0 -8($fp)
	# done writing to i
	j L0
L2:
	lw $v0 -4($fp)
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
