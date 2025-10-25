package ast;

import java.util.List;

import environment.Environment;

public class ProcedureCall implements Expression, Statement
{
    private String name;
    private List<Expression> arguments;

    public ProcedureCall(String name, List<Expression> arguments)
    {
        this.name = name;
        this.arguments = arguments;
    }

	@Override
	public int eval(Environment env) throws RTException
	{
		ProcedureDeclaration proc = env.getProcedure(name);
		if (proc == null)
		{
    		throw new RTException("Procedure not found: " + name);
		}
		return proc.eval(env, arguments);
	}

	@Override
	public void execute(Environment env) throws RTException, ControlException {
    	ProcedureDeclaration proc = env.getProcedure(name);
    	if (proc == null)
    	{
      		throw new RTException("Procedure not found: " + name);
    	}
    	proc.eval(env, arguments);
	}
}
