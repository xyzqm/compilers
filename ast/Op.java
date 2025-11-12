package ast;

import emitter.Emitter;

public enum Op {
    MUL((a, b) -> a * b, "mul"),
    ADD((a, b) -> a + b, "add"),
    SUB((a, b) -> a - b, "sub"),
    DIV((a, b) -> a / b, "div"),
    LT((a, b) -> a < b ? 1 : 0, "FIX"),
    GT((a, b) -> a > b ? 1 : 0, "FIX"),
    LE((a, b) -> a <= b ? 1 : 0, "FIX"),
    GE((a, b) -> a >= b ? 1 : 0, "FIX"),
    EQ((a, b) -> a == b ? 1 : 0, "FIX"),
    NE((a, b) -> a != b ? 1 : 0, "FIX"),
    AND((a, b) -> a != 0 ? b : a, "FIX"),
    OR((a, b) -> a != 0 ? a : b, "FIX");

    private final Fn fn;
    private final String cmd;

    Op(Fn fn, String cmd)
    {
        this.fn = fn;
        this.cmd = cmd;
    }

    public int eval(int a, int b)
    {
        return fn.eval(a, b);
    }

    public void compile(Emitter e, String a, String b, String c)
    {
        e.emit(String.format("%s %s %s %s", cmd, a, b, c));
    }

    @FunctionalInterface
    public interface Fn {
        int eval(int a, int b);
    }
}
