package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a statement for reading a line from the console.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Readln implements Statement
{
    Var var;

    /**
     * Constructs a new Readln statement with the given variable name.
     * @param var The name of the variable to store the input in.
     */
    public Readln(String var)
    {
        this.var = new Var(var);
    }

    @Override
	public void execute(Environment env) throws RTException
    {
        env.put(var.name(), Integer.parseInt(System.console().readLine()));
    }

    @Override
    public void compile(Emitter e)
    {
        e.emit("li $v0, 5");
        e.emit("syscall");
        e.writeVar(var, "$v0");
    }
}
