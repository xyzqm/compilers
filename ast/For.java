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
    private Var id;
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
        this.id = new Var(id);
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
            env.put(id.name(), i);
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
        e.emit("# Initialize loop variable " + id);
        (new Assign(id.name(), l)).compile(e);
        e.emit("# Done initializing loop variable");
        String var = e.nextVar();
        (new Assign(var, r)).compile(e); // store right bound

        e.emitLoop(
            () -> {
                (new If(
                    new BinOp(id, new Var(var), Op.GT),
                    new Control<Break>(new Break()),
                    body
                )).compile(e);
            },
            () -> {
                // increment id
                (new Assign(
                    id.name(),
                    new BinOp(id, new Num(1), Op.ADD))
                ).compile(e);
            }
        );
    }

	@Override
	public void label(Environment e) {
	    id.label(e);
		l.label(e);
		r.label(e);
		body.label(e);
	}
}
