package ast;

public interface Statement
{
    public void execute(Environment env) throws RuntimeException;
}
