package environment;

import java.util.HashMap;
import ast.ProcedureDeclaration;

/**
 * Represents an environment for storing and retrieving variables.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Environment extends HashMap<String, Integer>
{
    Environment parent;

    private HashMap<String, ProcedureDeclaration> procs = new HashMap<>();

    public Environment(Environment parent)
    {
        this.parent = parent;
    }

    public Environment()
    {
        this.parent = null;
    }

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

    public void setProcedure(String name, ProcedureDeclaration proc)
    {
        assert(parent == null);
        procs.put(name, proc);
    }

    public ProcedureDeclaration getProcedure(String name)
    {
        if (parent == null)
        {
            return procs.get(name);
        }
        else {
            return parent.getProcedure(name);
        }
    }
}
