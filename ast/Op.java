package ast;

import emitter.Emitter;

/**
 * Enum representing supported binary operations.
 * @author Daniel Zhu
 * @version 1.0
 */
public enum Op
{
    /**
     * Multiplication operation.
     */
    MUL((a, b) -> a * b, "mul"),
    /**
     * Addition operation.
     */
    ADD((a, b) -> a + b, "add"),
    /**
     * Subtraction operation.
     */
    SUB((a, b) -> a - b, "sub"),
    /**
     * Division operation.
     */
    DIV((a, b) -> a / b, "div"),
    /**
     * Less than operation.
     */
    LT((a, b) -> a < b ? 1 : 0, "FIX"),
    /**
     * Greater than operation.
     */
    GT((a, b) -> a > b ? 1 : 0, "FIX"),
    /**
     * Less than or equal operation.
     */
    LE((a, b) -> a <= b ? 1 : 0, "FIX"),
    /**
     * Greater than or equal operation.
     */
    GE((a, b) -> a >= b ? 1 : 0, "FIX"),
    /**
     * Equality operation.
     */
    EQ((a, b) -> a == b ? 1 : 0, "FIX"),
    /**
     * Not equal operation.
     */
    NE((a, b) -> a != b ? 1 : 0, "FIX"),
    /**
     * Logical AND operation.
     */
    AND((a, b) -> a != 0 ? b : a, "FIX"),
    /**
     * Logical OR operation.
     */
    OR((a, b) -> a != 0 ? a : b, "FIX");

    /**
     * The function implementing the operation.
     */
    private final Fn fn;
    /**
     * The assembly command for the operation.
     */
    private final String cmd;

    /**
     * Constructs an Op enum value.
     * @param fn The function implementing the operation.
     * @param cmd The assembly command for the operation.
     */
    Op(Fn fn, String cmd)
    {
        this.fn = fn;
        this.cmd = cmd;
    }

    /**
     * Evaluates the operation on two operands.
     * @param a The left operand.
     * @param b The right operand.
     * @return The result of the operation.
     */
    public int eval(int a, int b)
    {
        return fn.eval(a, b);
    }

    /**
     * Emits assembly code for this operation.
     * @param e The emitter.
     * @param a The destination register.
     * @param b The first operand register.
     * @param c The second operand register.
     */
    public void compile(Emitter e, String a, String b, String c)
    {
        e.emit(String.format("%s %s %s %s", cmd, a, b, c));
    }

    /**
     * Functional interface for binary operations.
     */
    @FunctionalInterface
    public interface Fn
    {
        /**
         * Evaluates the operation.
         * @param a The left operand.
         * @param b The right operand.
         * @return The result of the operation.
         */
        int eval(int a, int b);
    }
}
