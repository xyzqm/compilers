package ast;

import java.util.List;
import environment.Environment;

/**
 * Represents a procedure declaration in the AST.
 */
public class ProcedureDeclaration {
    public String name;
    private List<String> parameters;
    private Statement body;

    public ProcedureDeclaration(String name, List<String> parameters, Statement body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public int eval(Environment env, List<Expression> arguments) throws RTException
    {
        if (parameters.size() != arguments.size())
        {
            throw new RTException("Wrong number of arguments for procedure " + name);
        }
        Environment procEnv = new Environment(env);
        procEnv.put(name, 0); // dummy return value
        for (int i = 0; i < parameters.size(); i++)
        {
            procEnv.put(parameters.get(i), arguments.get(i).eval(env));
        }
        try {
            body.execute(procEnv);
        }
        catch (ControlException e)
        {
            throw new RTException("Error executing procedure " + name + ": " + e.getMessage());
        }
        return procEnv.get(name);
    }
}
