package emitter;

import java.io.*;

import environment.Environment;

/**
 * Handles emitting code to an output file.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Emitter extends Environment
{
    /**
     * The PrintWriter used to write to the output file.
     */
    private PrintWriter out;
    private int stackHeight = 0;

    /**
     * Creates an emitter for writing to a new file with the given name.
     * @param outputFileName The name of the output file.
     */
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prints one line of code to file (with non-labels indented).
     * @param code The code to emit.
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
        {
            code = "\t" + code;
        }
        out.println(code);
    }

    /**
     * Emits code to push a register onto the stack.
     * @param reg The register to push.
     */
    public void emitPush(String reg)
    {
        stackHeight += 4;
        emit("subu $sp $sp 4");
        emit("sw " + reg + " ($sp)");
    }

    /**
     * Emits code to pop a register from the stack.
     * @param reg The register to pop into.
     */
    public void emitPop(String reg)
    {
        stackHeight -= 4;
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4");
    }

    public void newVar(String name)
    {
        emitPush("$v0");
        put(name, stackHeight);
    }

    public String address(String name)
    {
        return (stackHeight - get(name)) + "($sp)";
    }

    public void getVar(String name)
    {
        emit("lw $v0 " + address(name));
    }

    public void writeVar(String name)
    {
        if (!containsKey(name))
        {
            newVar(name);
        }
        else
        {
            emit("sw $v0 " + address(name));
        }
    }

    /**
     * Closes the file. Should be called after all calls to emit.
     */
    public void close()
    {
        out.close();
    }
}
