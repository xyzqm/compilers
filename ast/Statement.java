package ast;

interface Statement
{
    public void execute(Environment env) throws RuntimeException;
}
