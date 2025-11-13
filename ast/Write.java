package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a writeln statement in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Write implements Statement
{
    private Expression expr;
    private boolean newline;

    /**
     * Constructs a Writeln statement with the given expression.
     *
     * @param expr the expression to be written to the console
     */
    public Write(Expression expr, boolean newline)
    {
        this.expr = expr;
        this.newline = newline;
    }

    @Override
    public void execute(Environment env) throws RTException
    {
        if (expr != null)
        {
            System.out.print(expr.eval(env));
        }
        if (newline)
        {
            System.out.println();
        }
    }

    @Override
    public void compile(Emitter e)
    {
        if (expr != null)
        {
            expr.compile(e);
        }
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");

        if (newline)
        {
            e.emit("li $v0 11");
            e.emit("li $a0 10");
            e.emit("syscall");
        }
    }
}
