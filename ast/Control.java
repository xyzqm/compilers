package ast;

import evironment.Environment;

/**
 * Represents a control statement in the AST.
 * @param <T> The type of control exception.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Control<T extends ControlException> implements Statement
{
    T e;

    /**
     * Creates a new Control statement with the given exception.
     * @param e The control exception to throw when executed.
     */
    public Control(T e)
    {
        this.e = e;
    }

    @Override
    public void execute(Environment env) throws RTException, ControlException
    {
        throw e;
    }
}
