package ast;

import java.util.ArrayList;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a block of statements.
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
        for (Statement statement : this)
        {
            statement.compile(e);
        }
    }
}
