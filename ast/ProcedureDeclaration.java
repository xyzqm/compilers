package ast;

import java.util.List;
import environment.Environment;

/**
 * Represents a procedure declaration in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class ProcedureDeclaration
{
    /**
     * The name of the procedure.
     */
    public String name;
    private List<String> parameters;
    private Statement body;

    /**
     * Constructs a ProcedureDeclaration.
     * @param name The name of the procedure.
     * @param parameters The parameters of the procedure.
     * @param body The body statement of the procedure.
     */
    public ProcedureDeclaration(String name, List<String> parameters, Statement body)
    {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    /**
     * Evaluates the procedure with the given arguments.
     * @param env The environment in which to evaluate.
     * @param arguments The arguments to pass to the procedure.
     * @return The result of the procedure.
     * @throws RTException If an error occurs during evaluation.
     */
    public int eval(Environment env, List<Expression> arguments) throws RTException
    {
        // System.out.println(env);
        if (parameters.size() != arguments.size())
        {
            throw new RTException("Wrong number of arguments for procedure " + name);
        }
        Environment procEnv = new Environment(env.getParent());
        procEnv.put(name, 0); // dummy return value
        for (int i = 0; i < parameters.size(); i++)
        {
            procEnv.put(parameters.get(i), arguments.get(i).eval(env));
        }
        try
        {
            body.execute(procEnv);
        }
        catch (ControlException e)
        {
            throw new RTException("Error executing procedure " + name + ": " + e.getMessage());
        }
        return procEnv.get(name);
    }
}
