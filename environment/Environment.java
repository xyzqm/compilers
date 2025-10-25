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

    private HashMap<String, ProcedureDeclaration> procs;

    public void addProcedure(String name, ProcedureDeclaration proc)
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
