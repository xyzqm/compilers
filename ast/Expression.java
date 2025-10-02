package ast;

interface Expression
{
    int eval(Environment env) throws RuntimeException;
}
