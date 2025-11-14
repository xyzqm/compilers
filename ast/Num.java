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
     * @param value the integer value for this numeric literal
     */
    public Num(int value)
    {
        this.value = value;
    }

    /**
     * Evaluates the numeric literal.
     * @param env The environment (unused).
     * @return The integer value of this literal.
     */
    @Override
    public int eval(Environment env) throws RTException
    {
        return value;
    }

    /**
     * Compiles the numeric literal to assembly code.
     * @param e The emitter to use for code generation.
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }

}
