package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a for loop statement in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class For implements Statement
{
    private String id;
    private Expression l, r;
    private Statement body;

    /**
     * Creates a new For loop statement.
     * @param id The loop variable identifier.
     * @param l The lower bound expression.
     * @param r The upper bound expression.
     * @param body The loop body statement.
     */
    public For(String id, Expression l, Expression r, Statement body)
    {
        this.id = id;
        this.l = l;
        this.r = r;
        this.body = body;
    }

    @Override
    public void execute(Environment env) throws RTException, ControlException
    {
        int lf = l.eval(env);
        int rt = r.eval(env);
        for (int i = lf; i <= rt; i++)
        {
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

	@Override
	public void compile(Emitter e)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'compile'");
	}
}
