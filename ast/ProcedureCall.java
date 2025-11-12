package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a procedure call in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class ProcedureCall implements Expression, Statement
{
    /**
     * The name of the procedure to call.
     */
    private String name;

    /**
     * The arguments to pass to the procedure.
     */
    private List<Expression> arguments;

    /**
     * Constructs a ProcedureCall.
     * @param name The name of the procedure.
     * @param arguments The arguments for the procedure.
     */
    public ProcedureCall(String name, List<Expression> arguments)
    {
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * Evaluates the procedure call and returns its result.
     * @param env The environment in which to evaluate.
     * @return The result of the procedure.
     * @throws RTException If the procedure is not found or an error occurs.
     */
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

    /**
     * Executes the procedure call.
     * @param env The environment in which to execute.
     * @throws RTException If an error occurs during execution.
     * @throws ControlException If a control flow exception occurs.
     */
    @Override
    public void execute(Environment env) throws RTException, ControlException
    {
        eval(env);
    }

    @Override
    public void compile(Emitter e)
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compile'");
    }
}
