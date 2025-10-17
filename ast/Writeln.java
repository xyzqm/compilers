package ast;

import evironment.Environment;

/**
 * Represents a writeln statement in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Writeln implements Statement
{
    private Expression expr;

    /**
     * Constructs a Writeln statement with the given expression.
     *
     * @param expr the expression to be written to the console
     */
    public Writeln(Expression expr)
    {
        this.expr = expr;
    }

    @Override
    public void execute(Environment env) throws RTException
    {
        System.out.println(expr.eval(env));
    }
}
