package emitter;

import java.io.*;

/**
 * Handles emitting code to an output file.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Emitter
{
    /**
     * The PrintWriter used to write to the output file.
     */
    private PrintWriter out;

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
        emit("subu $sp $sp 4");
        emit("sw " + reg + " ($sp)");
    }

    /**
     * Emits code to pop a register from the stack.
     * @param reg The register to pop into.
     */
    public void emitPop(String reg)
    {
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4");
    }

    /**
     * Closes the file. Should be called after all calls to emit.
     */
    public void close()
    {
        out.close();
    }
}
