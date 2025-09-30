package scanner;

/**
 * EOFException is thrown when the end of the input stream is reached.
 */
public class EOFException extends ScanErrorException
{
    /**
     * default constructor for EOFException
     */
    public EOFException()
    {
        super("End of file reached");
    }

    /**
     * Constructor for EOFException that includes a reason for the error
     * @param reason the reason for the error
     */
    public EOFException(String reason)
    {
        super("End of file reached: " + reason);
    }
}
