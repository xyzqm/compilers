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
	subu $sp, $sp, 8
	li $v0 1
	# writing to i
	sw $v0 -4($fp)
	# done writing to i
	li $v0 1
	# writing to j
	sw $v0 -8($fp)
	# done writing to j
L0:
	# loading i
	lw $v0 -4($fp)
	# done loading i
	push($v0)
	li $v0 5
	pop($t0)
	eval_comp(ble, $v0, $t0, $v0)
	beq $v0, $zero, L2
	li $v0 1
	# writing to j
	sw $v0 -8($fp)
	# done writing to j
L3:
	# loading j
	lw $v0 -8($fp)
	# done loading j
	push($v0)
	li $v0 5
	pop($t0)
	eval_comp(ble, $v0, $t0, $v0)
	beq $v0, $zero, L5
	# loading j
	lw $v0 -8($fp)
	# done loading j
	push($v0)
	# loading i
	lw $v0 -4($fp)
	# done loading i
	pop($t0)
	eval_comp(bgt, $v0, $t0, $v0)
	beq $v0, 1, L6
	j L7
L6:
	j L5
L7:
	# loading j
	lw $v0 -8($fp)
	# done loading j
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 32
	syscall
	# loading j
	lw $v0 -8($fp)
	# done loading j
	push($v0)
	li $v0 1
	pop($t0)
	add $v0 $t0 $v0
	# writing to j
	sw $v0 -8($fp)
	# done writing to j
	# continue label
L4:
	j L3
L5:
	# loading i
	lw $v0 -4($fp)
	# done loading i
	push($v0)
	li $v0 1
	pop($t0)
	add $v0 $t0 $v0
	# writing to i
	sw $v0 -4($fp)
	# done writing to i
	li $v0 11
	li $a0 10
	syscall
	# continue label
L1:
	j L0
L2:
	# loading j
	lw $v0 -8($fp)
	# done loading j
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
