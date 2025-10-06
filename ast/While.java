package ast;

public class While implements Statement {
    private Expression condition;
    private Statement body;

    public While(Expression condition, Statement body)
    {
        this.condition = condition;
        this.body = body;
    }

	@Override
	public void execute(Environment env) throws RTException {
		while (condition.eval(env) != 0)
		{
			body.execute(env);
		}
	}
}
