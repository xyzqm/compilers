package ast;

/**
 * Represents a runtime exception while executing the AST.
 * @author Daniel Zhu
 * @version 1.0
 */
public class RTException extends Exception
{
    /**
    * Constructs an exception.
    * @param message the message to be displayed
 */
    public RTException(String message)
    {
        super(message);
    }
}
