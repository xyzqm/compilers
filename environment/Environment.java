package environment;

import java.util.HashMap;
import ast.ProcedureDeclaration;

/**
 * Represents an environment for storing and retrieving variables/functions.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Environment extends HashMap<String, Integer>
{
    /**
     * The parent environment.
     */
    Environment parent;

    /**
     * Stores procedure declarations by name.
     */
    protected HashMap<String, ProcedureDeclaration> procs = new HashMap<>();

    /**
     * Constructs an environment with a parent.
     * @param parent The parent environment.
     */
    public Environment(Environment parent)
    {
        this.parent = parent;
    }

    /**
     * Constructs a root environment with no parent.
     */
    public Environment()
    {
        this.parent = null;
    }

    /**
     * Gets the parent environment.
     * @return The parent environment.
     */
    public Environment getParent()
    {
        return parent;
    }

    /**
     * Gets the value (interpreter) or address (compiler) for the given key.
     * Searches parent environments if necessary.
     * @param key The key to look up.
     * @return The value or address associated with the key,
     *         or null if not found.
     */
    @Override
    public Integer get(Object key)
    {
        Integer val = super.get(key);
        if (val == null && parent != null)
        {
            return parent.get(key);
        }
        return val;
    }

    /**
     * Checks if the environment contains the given key, searching parent environments if necessary.
     * @param key The key to check.
     * @return True if the key exists, false otherwise.
     */
    @Override
    public boolean containsKey(Object key)
    {
        boolean contains = super.containsKey(key);
        if (!contains && parent != null)
        {
            return parent.containsKey(key);
        }
        return contains;
    }

    /**
     * Sets a procedure in the environment.
     * @param name The name of the procedure.
     * @param proc The procedure declaration.
     */
    public void setProcedure(String name, ProcedureDeclaration proc)
    {
        assert(parent == null);
        procs.put(name, proc);
    }

    /**
     * Gets a procedure by name, searching parent environments if necessary.
     * @param name The name of the procedure.
     * @return The procedure declaration, or null if not found.
     */
    public ProcedureDeclaration getProcedure(String name)
    {
        if (parent == null)
        {
            return procs.get(name);
        }
        else
        {
            return parent.getProcedure(name);
        }
    }
}
