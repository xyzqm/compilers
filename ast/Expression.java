package ast;

public interface Expression
{
    int eval(Environment env) throws RTException;
}
