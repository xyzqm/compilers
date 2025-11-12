package ast;

import emitter.Emitter;
import environment.Environment;

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

    @Override
    public void compile(Emitter e)
    {
        expr.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        // print newline
        e.emit("li $v0 11");
        e.emit("li $a0 10");
        e.emit("syscall");
    }
}
