package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents an assignment statement in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Assign implements Statement
{
    private Var id;
    private Expression expr;

    /**
    * Initializes an assignment statement.
    * @param id The identifier to assign to.
    * @param expr The expression to evaluate and assign.
 */
    public Assign(String id, Expression expr)
    {
        this.id = new Var(id);
        this.expr = expr;
    }

    @Override
	public void execute(Environment env) throws RTException
    {
        env.put(id.name(), expr.eval(env));
    }

    @Override
    public void compile(Emitter e)
    {
        expr.compile(e);
        e.writeVar(id);
    }

	@Override
	public void label(Environment e)
	{
	    id.label(e);
		expr.label(e);
	}
}
