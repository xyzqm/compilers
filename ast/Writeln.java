package ast;

public class Writeln implements Statement
{
    private Expression expr;

    public Writeln(Expression expr)
    {
        this.expr = expr;
    }

    @Override
    public void execute(Environment env) throws RuntimeException
    {
        System.out.println(expr.eval(env));
    }
}
