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
public class Emitter extends Environment
{
    /**
     * The PrintWriter used to write to the output file.
     */
    /**
     * The PrintWriter used to write to the output file.
     */
    private PrintWriter out;

    /**
     * The current stack height in bytes.
     */
    private int stackHeight = 0;

    /**
     * The label counter for generating unique labels.
     */
    private int labels = 0;

    /**
     * The variable counter for generating unique variable names.
     */
    private int vars = 0;

    /**
     * Stack of continue labels for nested loops.
     */
    public Stack<String> continueLabels = new Stack<>();

    /**
     * Stack of break labels for nested loops.
     */
    public Stack<String> breakLabels = new Stack<>();

    /**
     * The current environment for variable storage.
     */
    private Environment env = this;

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

    /**
     * Returns the next unused variable name.
     * @return The next variable name.
     */
    public String nextVar()
    {
        return "V" + vars++;
    }

    /**
     * Pushes a new environment onto the environment stack.
     */
    public void pushEnv()
    {
        env = new Environment(env);
    }

    /**
     * Pops the current environment from the environment stack.
     */
    public void popEnv()
    {
        for (int i = 0; i < env.size(); i++)
        {
            emitPop("$t0");
        }
        env = env.getParent();
    }

    /**
     * Reverts the environment to its parent.
     */
    public void revertEnv()
    {
        env = env.getParent();
    }

    /**
     * Gets the current environment.
     * @return The current environment.
     */
    public Environment getEnv()
    {
        return env;
    }

    /**
     * Gets the current stack height.
     * @return The stack height.
     */
    public int getStackHeight()
    {
        return stackHeight;
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
    /**
     * Returns the next unused label.
     * @return The next label string.
     */
    public String nextLabel()
    {
        return "L" + labels++;
    }

    /**
     * Emits code to push a register onto the stack.
     * @param reg The register to push.
     */
    /**
     * Emits code to push a register onto the stack.
     * @param reg The register to push.
     */
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
    /**
     * Emits code to pop a register from the stack.
     * @param reg The register to pop into.
     */
    /**
     * Emits code to pop a register from the stack.
     * @param reg The register to pop into.
     */
    public void emitPop(String reg)
    {
        stackHeight -= 4;
        emit("pop(" + reg + ")");
    }

    /**
     * Sets the stack address for a variable.
     * @param var The variable name.
     * @param offset The offset to add to the stack height.
     */
    public void setAddr(String var, int offset)
    {
        env.put(var, stackHeight + offset);
    }

    /**
     * Returns the stack address for a variable, allocating space if needed.
     * @param var The variable.
     * @return The stack address string.
     */
    public String address(Var var)
    {
        if (!env.containsKey(var.name()))
        {
            emitPush("$zero");
            env.put(var.name(), stackHeight);
        }
        return (stackHeight - env.get(var.name())) + "($sp)";
    }

    /**
     * Emits code to load a variable into a register.
     * @param var The variable to load.
     * @param reg The register to load into.
     */
    public void getVar(Var var, String reg)
    {
        emit("# loading " + var.name());
        emit("lw " + reg + " " + address(var));
        emit("# done loading " + var.name());
    }

    /**
     * Emits code to write a register value to a variable.
     * @param id The variable to write to.
     * @param reg The register to write from.
     */
    public void writeVar(Var id, String reg)
    {
        emit("# writing to " + id.name());
        emit("sw " + reg + " " + address(id));
        emit("# done writing to " + id.name());
    }

    /**
     * Emits code for a loop structure.
     * @param body The loop body as a Runnable.
     * @param inc The increment operation as a Runnable.
     */
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
    /**
     * Closes the file. Should be called after all calls to emit.
     */
    public void close()
    {
        out.close();
    }
}
