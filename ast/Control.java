package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a control statement in the AST.
 * @param <T> The type of control exception.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Control<T extends ControlException> implements Statement
{
    /**
     * The control exception to throw when executed.
     */
    private T exception;

    /**
     * Creates a new Control statement with the given exception.
     * @param exception The control exception to throw when executed.
     */
    public Control(T exception)
    {
        this.exception = exception;
    }

    @Override
    public void execute(Environment env) throws RTException, ControlException
    {
        throw exception;
    }

    @Override
    public void compile(Emitter e)
    {
        if (exception instanceof Break)
        {
            e.emit("j " + e.breakLabels.peek());
        }
        else if (exception instanceof Continue)
        {
            e.emit("j " + e.continueLabels.peek());
        }
        else throw new UnsupportedOperationException("Unimplemented method 'compile'");
    }

	@Override
	public void label(Environment e)
	{
	}
}
