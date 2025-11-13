package emitter;

import java.io.*;
import java.util.Stack;

import ast.Var;
import environment.Environment;

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
    // private int stackHeight = 0;
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

    // public int getStackHeight()
    // {
    //     return stackHeight;
    // }

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
        // stackHeight += 4;
        emit("push(" + reg + ")");
    }

    /**
     * Emits code to pop a register from the stack.
     * @param reg The register to pop into.
     */
    public void emitPop(String reg)
    {
        // stackHeight -= 4;
        emit("pop(" + reg + ")");
    }

    public String address(Var var)
    {
        return -var.getOffset() + "($fp)";
    }

    public void getVar(Var var, String reg)
    {
        emit("# loading " + var.name());
        emit("lw " + reg + " " + address(var));
        emit("# done loading " + var.name());
    }

    public void writeVar(Var id)
    {
        emit("# writing to " + id.name());
        // if (!containsKey(name))
        // {
        //     newVar(name);
        // }
        emit("sw $v0 " + address(id));
        emit("# done writing to " + id.name());
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
