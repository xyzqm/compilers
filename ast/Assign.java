package ast;

public class Assign implements Statement
{
    private String id;
    private Expression expr;

    public Assign(String id, Expression expr) {
        this.id = id;
        this.expr = expr;
    }

	@Override
	public void execute(Environment env) throws RTException
	{
	    env.put(id, expr.eval(env));
	}
}
