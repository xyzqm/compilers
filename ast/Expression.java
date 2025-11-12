package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents an expression in the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public interface Expression
{
    /**
    * Evaluates the expression in a given environment.
    * @param env The environment in which to evaluate the expression.
    * @return The result of evaluating the expression.
    * @throws RTException If an error occurs during evaluation.
 */
    int eval(Environment env) throws RTException;
    void compile(Emitter e);
}
