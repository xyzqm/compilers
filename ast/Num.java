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
    private String value;

    /**
     * Constructs a Num object with the given value.
     * @param value the value of the numeric literal, or the variable name
     */
    public Num(String value)
    {
        this.value = value;
    }

    @Override
    public int eval(Environment env) throws RTException
    {
        try
        {
            int number = Integer.parseInt(value);
            return number;
        }
        catch (NumberFormatException e)
        {
            if (env.containsKey(value))
            {
                return env.get(value);
            }
            throw new RTException(value + " is not defined");
        }
    }

    @Override
    public void compile(Emitter e)
    {
        try
        {
            int number = Integer.parseInt(value);
            e.emit("li $v0 " + number);
        }
        catch (NumberFormatException nfe)
        {
            e.getVar(value);
        }
    }
}
