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
