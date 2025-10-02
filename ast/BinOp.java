package ast;

public class BinOp implements Expression
{
    private Expression left;
    private Expression right;
    private Fn op;

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
