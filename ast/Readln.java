package ast;

import environment.Environment;

/**
 * Represents a statement for reading a line from the console.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Readln implements Statement
{
    String var;

    /**
     * Constructs a new Readln statement with the given variable name.
     * @param var The name of the variable to store the input in.
     */
    public Readln(String var)
    {
        this.var = var;
    }

    @Override
	public void execute(Environment env) throws RTException
    {
        env.put(var, Integer.parseInt(System.console().readLine()));
    }
}
