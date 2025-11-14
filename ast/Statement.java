package ast;

import environment.Environment;

/**
 * Represents a statement in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public interface Statement extends Node
{
    /**
     * Executes the statement in the given environment.
     * @param env The environment in which the statement is executed.
     * @throws RTException If an error occurs during execution.
     * @throws ControlException If a control flow exception occurs.
     */
    void execute(Environment env) throws RTException, ControlException;
}
