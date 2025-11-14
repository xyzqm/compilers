package ast;

import emitter.Emitter;

/**
 * Represents an node in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public interface Node
{
    /**
     * Compiles the expression using the given emitter.
     * @param e The emitter to use for code generation.
     */
    void compile(Emitter e);
}
