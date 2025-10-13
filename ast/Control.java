package ast;

public class Control<T extends ControlException> implements Statement {
    T e;

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
