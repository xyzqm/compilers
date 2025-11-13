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
        // e.emit("# Initialize loop variable " + id);
        // (new Assign(id, new BinOp(l, new Num("1"), Op.SUB))).compile(e);
        // e.emit("# Done initializing loop variable");
        // String var = e.nextVar();
        // (new Assign(var, r)).compile(e); // store right bound

        // e.emitLoop(() -> {
        //     // write id + 1 to $v0
        //     (new BinOp(new Num(id), new Num("1"), Op.ADD)).compile(e);
        //     // condition
        //     e.getVar(var, "$t0");
        //     e.emit("bgt $v0, $t0, " + e.breakLabels.peek());
        //     // set id to $v0
        //     e.writeVar(id);
        //     body.compile(e);
        // });
    }
}
