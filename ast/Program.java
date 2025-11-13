package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents the main program in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Program extends Environment
{
    /**
     * The body statement of the program.
     */
    private Statement body;

    /**
     * Sets the body statement of the program.
     * @param body The body statement.
     */
    public void setBody(Statement body)
    {
        this.body = body;
    }

    /**
     * Executes the program.
     * @throws RTException If an error occurs during execution.
     * @throws ControlException If a control flow exception occurs.
     */
    public void execute() throws RTException, ControlException
    {
        Environment mainEnv = new Environment(this);
        body.execute(mainEnv);
    }

    /**
     * Compiles the program to the specified file.
     * @param filename The name of the output file.
     */
    public void compile(String filename)
    {
        Emitter e = new Emitter(filename);
        e.emit("main:");
        body.label(this);
        e.emit("subu $sp, $sp, " + getOffset());
        body.compile(e);
        e.close();
    }
}
