package ast;

import environment.Environment;

/**
 * Represents an assignment statement in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Assign implements Statement
{
    private String id;
    private Expression expr;

    /**
    * Initializes an assignment statement.
    * @param id The identifier to assign to.
    * @param expr The expression to evaluate and assign.
 */
    public Assign(String id, Expression expr)
    {
        this.id = id;
        this.expr = expr;
    }

    @Override
	public void execute(Environment env) throws RTException
    {
        env.put(id, expr.eval(env));
    }
}
