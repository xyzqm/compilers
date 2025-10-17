package ast;

import evironment.Environment;

/**
 * Represents a binary operation expression.
 * @author Daniel Zhu
 * @version 1.0
 */
public class BinOp implements Expression
{
    private Expression left;
    private Expression right;
    private Fn op;

    /**
    * Constructs a binary operation expression.
    * @param left the left operand
    * @param right the right operand
    * @param op the binary operator
 */
    public BinOp(Expression left, Expression right, Fn op)
    {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public int eval(Environment env) throws RTException
    {
        int leftValue = left.eval(env);
        int rightValue = right.eval(env);
        return op.eval(leftValue, rightValue);
    }
}
