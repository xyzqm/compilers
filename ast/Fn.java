package ast;

/**
 * Functional interface representing a function
 * that takes two integers as input and returns an integer.
 * @author Daniel Zhu
 * @version 1.0
 */
@FunctionalInterface
public interface Fn
{
    /**
     * Evaluates the function with the given left and right operands.
     * @param l the left operand
     * @param r the right operand
     * @return the result of the function evaluation
     */
    int eval(int l, int r);
}
