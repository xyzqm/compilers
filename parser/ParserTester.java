package parser;

public class ParserTester {
    public static void main(String[] args) {
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
