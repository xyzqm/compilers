package ast;

import java.util.ArrayList;
import java.util.Stack;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a block of statements.
 * All local variables should be cleared after execution.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Block extends ArrayList<Statement> implements Statement
{
    @Override
	public void execute(Environment env) throws RTException, ControlException
    {
        for (Statement statement : this)
        {
            statement.execute(env);
        }
    }

    @Override
    public void compile(Emitter e)
    {
        Stack<String> vars = new Stack<>();
        for (Statement statement : this)
        {
            if (statement instanceof Assign)
            {
                vars.push(((Assign) statement).getName());
            }
            statement.compile(e);
        }
        while (!vars.isEmpty())
        {
            System.out.println(e);
            System.out.println(vars);
            assert(e.containsKey(vars.peek()));
            assert(e.getStackHeight() == e.get(vars.peek()));
            e.remove(vars.pop());
            e.emitPop("$v0"); // register doesn't matter here
        }
    }
}
