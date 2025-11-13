package ast;

import emitter.Emitter;
import environment.Environment;

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
    public void execute(Environment env) throws RTException, ControlException
    {
    	while (condition.eval(env) != 0)
    	{
            body.execute(env);
    	}
    }

    @Override
    public void compile(Emitter e)
    {
        String loop = e.nextLabel();
        e.continueLabels.push(e.nextLabel());
        e.breakLabels.push(e.nextLabel());
        e.emit(loop + ":");
        condition.compile(e);
        e.emit("beq $v0, $zero, " + e.breakLabels.peek());
        body.compile(e);
        e.emit(e.continueLabels.pop() + ":");
        e.emit("j " + loop);
        e.emit(e.breakLabels.pop() + ":");
    }
}
