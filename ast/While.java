package ast;

/**
 * Represents a while statement in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class While implements Statement
{
    private Expression condition;
    private Statement body;

    /**
     * Constructs a new While statement.
     * @param condition The condition expression.
     * @param body The body statement.
     */
    public While(Expression condition, Statement body)
    {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Environment env) throws RTException
    {
    	while (condition.eval(env) != 0)
    	{
            body.execute(env);
    	}
    }
}
