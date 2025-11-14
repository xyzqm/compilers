package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a numeric literal in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Var implements Expression
{
    private String id;

    /**
     * Constructs a Num object with the given value.
     * @param id the value of the numeric literal, or the variable name
     */
    public Var(String id)
    {
        this.id = id;
    }

    @Override
    public int eval(Environment env) throws RTException
    {
        if (env.containsKey(id))
        {
            return env.get(id);
        }
        throw new RTException(id + " is not defined");
    }

    /**
     * Returns the variable name.
     * @return the variable name as a string
     */
    public String name()
    {
        return id;
    }

    @Override
    public void compile(Emitter e)
    {
        e.getVar(this, "$v0");
    }
}
