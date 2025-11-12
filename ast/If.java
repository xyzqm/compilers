package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents an if-then-else statement in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class If implements Statement
{
    private Expression cond;
    private Statement then;
    private Statement els;

    /**
    * Constructs an if statement with the given condition, then statement, and else statement.
    * @param cond The condition expression.
    * @param then The statement to execute if the condition is true.
    * @param els The statement to execute if the condition is false.
 */
    public If(Expression cond, Statement then, Statement els)
    {
        this.cond = cond;
        this.then = then;
        this.els = els;
    }

    @Override
	public void execute(Environment env) throws RTException, ControlException
    {
        if (cond.eval(env) != 0)
        {
            then.execute(env);
    	}
        else if (els != null)
    	{
            els.execute(env);
    	}
    }

    @Override
    public void compile(Emitter e)
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compile'");
    }
}
