package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a numeric literal in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Num implements Expression
{
    private int value;

    /**
     * Constructs a Num object with the given value.
     * @param integer value
     */
    public Num(int value)
    {
        this.value = value;
    }

    @Override
    public int eval(Environment env) throws RTException
    {
        return value;
    }

    @Override
    public void label(Environment e)
    {
    }

    @Override
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }

}
