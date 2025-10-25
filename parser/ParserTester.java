package parser;

import ast.Program;

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
            // Environment env = new Environment();
            Parser parser = new Parser(args[0]);
            Program p = parser.parseProgram();
            p.execute();
            // parser.parseStatement().execute(env);
            // assert(parser.eof);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
