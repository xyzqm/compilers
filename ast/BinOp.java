package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a binary operation expression.
 * @author Daniel Zhu
 * @version 1.0
 */
public class BinOp implements Expression
{
    private Expression left;
    private Expression right;
    private Op op;

    /**
    * Constructs a binary operation expression.
    * @param left the left operand
    * @param right the right operand
    * @param op the binary operator
 */
    public BinOp(Expression left, Expression right, Op op)
    {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public int eval(Environment env) throws RTException
    {
        int lf = left.eval(env);
        int rt = right.eval(env);
        return op.eval(lf, rt);
    }

    @Override
    public void compile(Emitter e)
    {
        left.compile(e);
        e.emitPush("$v0");
        right.compile(e);
        e.emitPop("$t0");
        op.compile(e, "$v0", "$t0", "$v0");
    }

}
