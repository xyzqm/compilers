# Subroutines.
# @author Daniel Zhu
# @version 11.23.25

.macro push(%reg)
    subu $sp, $sp, 4
    sw %reg, ($sp)
.end_macro

.macro pop(%reg)
    lw %reg, ($sp)
    addu $sp, $sp, 4
.end_macro

.macro call(%fn)
    push($ra)
    jal %fn
    pop($ra)
.end_macro

.macro read(%reg)
    li $v0, 5
    syscall
    move %reg, $v0
.end_macro

.macro println(%reg)
    move $a0, %reg
    li $v0, 1
    syscall
   	li $v0 11
	li $a0 10
	syscall
.end_macro

main:
read($a0)
read($a1)
read($a2)
call(max3)
println($v0)

read($a0)
call(fact)
println($v0)

read($a0)
call(fib)
println($v0)

li $v0, 10
syscall

max2:
    bgt $a0, $a1, first
    move $v0, $a1
    j max2_return
first:
    move $v0, $a0
max2_return:
    jr $ra

max3:
    call(max2)
    bgt $v0, $a2, max3_return
    move $v0, $a2
max3_return:
    jr $ra

fact:
    beq $a0, 0, fact_base
    sub $a0, $a0, 1
    call(fact)
    add $a0, $a0, 1
    mul $v0, $v0, $a0
    j fact_return
fact_base:
    li $v0, 1
fact_return:
    jr $ra

fib:
    ble $a0, 1, fib_base
    sub $a0, $a0, 1
    call(fib)
    push($v0)
    sub $a0, $a0, 1
    call(fib)
    pop($t0)
    add $v0, $v0, $t0
    add $a0, $a0, 2
    j fib_return
fib_base:
    li $v0, 1
fib_return:
    jr $ra
