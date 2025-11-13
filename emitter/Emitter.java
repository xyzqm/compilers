package emitter;

import java.io.*;
import java.util.Stack;

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
    private int labels = 0;
    private int vars = 0;
    public Stack<String> continueLabels = new Stack<>();
    public Stack<String> breakLabels = new Stack<>();

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
        // read base.asm and write into out
        try (BufferedReader reader = new BufferedReader(new FileReader("base.asm")))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                out.println(line);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        emit("");
    }

    public int getStackHeight()
    {
        return stackHeight;
    }

    public String nextVar()
    {
        return "V" + vars++;
    }

    /**
     * Prints one line of code to file (with non-labels indented).
     * @param code The code to emit.
     */
    public void emit(String code)
    {
        if (!code.endsWith(":") && !code.startsWith("."))
        {
            code = "\t" + code;
        }
        out.println(code);
    }

    /**
     * Returns the next unused label.
     */
    public String nextLabel()
    {
        return "L" + labels++;
    }

    /**
     * Emits code to push a register onto the stack.
     * @param reg The register to push.
     */
    public void emitPush(String reg)
    {
        stackHeight += 4;
        emit("push(" + reg + ")");
    }

    /**
     * Emits code to pop a register from the stack.
     * @param reg The register to pop into.
     */
    public void emitPop(String reg)
    {
        stackHeight -= 4;
        emit("pop(" + reg + ")");
    }

    public void newVar(String name)
    {
        emitPush("$v0");
        put(name, stackHeight);
    }

    public String address(String name)
    {
        return -get(name) + "($fp)";
    }

    public void getVar(String name, String reg)
    {
        emit("lw " + reg + " " + address(name));
    }

    public void writeVar(String name)
    {
        emit("# writing to " + name);
        if (!containsKey(name))
        {
            newVar(name);
        }
        else
        {
            emit("sw $v0 " + address(name));
        }
        emit("# done writing to " + name);
    }

    public void emitLoop(Runnable body, Runnable inc)
    {
        String loop = nextLabel();
        continueLabels.push(nextLabel());
        breakLabels.push(nextLabel());
        emit(loop + ":");

        body.run();

        emit("# continue label");
        emit(continueLabels.pop() + ":");
        inc.run();
        emit("j " + loop);
        emit(breakLabels.pop() + ":");
    }

    /**
     * Closes the file. Should be called after all calls to emit.
     */
    public void close()
    {
        out.close();
    }
}
