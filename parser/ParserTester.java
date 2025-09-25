package parser;

/**
 * This class tests the Parser class.
 * @author Daniel Zhu
 * @version 1.0
 */
public class ParserTester
{
    /**
     * Main method to test the Parser class.
     * @param args Command-line arguments.
     */
    public static void main(String[] args)
    {
        try
        {
            // read input from file
            Parser parser = new Parser(args[0]);
            while (true)
            {
                parser.parseStatement();
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
