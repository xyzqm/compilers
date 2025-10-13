package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block of statements.
 * @author Daniel Zhu
 * @version 1.0
 */
public class Block implements Statement
{
    private List<Statement> statements;

    /**
     * Constructs an empty block.
     */
    public Block()
    {
        statements = new ArrayList<>();
    }

    /**
     * Adds a statement to the block.
     * @param statement the statement to add
     */
    public void addStatement(Statement statement)
    {
        statements.add(statement);
    }

    @Override
	public void execute(Environment env) throws RTException, ControlException
    {
        for (Statement statement : statements)
        {
            statement.execute(env);
        }
    }
}
