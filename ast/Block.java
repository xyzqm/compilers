package ast;

import java.util.ArrayList;
import java.util.List;

public class Block implements Statement
{
    private List<Statement> statements;

    public Block()
    {
        statements = new ArrayList<>();
    }

    public void addStatement(Statement statement)
    {
        statements.add(statement);
    }

	@Override
	public void execute(Environment env) throws RTException
	{
		for (Statement statement : statements)
		{
			statement.execute(env);
		}
	}
}
