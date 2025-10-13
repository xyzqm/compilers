package ast;

public class For implements Statement
{
    private String id;
    private Expression l, r;
    private Statement body;

    public For(String id, Expression l, Expression r, Statement body)
    {
        this.id = id;
        this.l = l;
        this.r = r;
        this.body = body;
    }

	@Override
	public void execute(Environment env) throws RTException, ControlException {
	    int lf = l.eval(env);
	    int rt = r.eval(env);
	    for (int i = lf; i <= rt; i++) {
			env.put(id, i);
			try
			{
    			body.execute(env);
			}
			catch (Break b)
			{
				break;
			}
			catch (Continue c)
			{
				continue;
			}
	    }
	}
}
