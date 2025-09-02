package scanner;

import java.io.FileInputStream;

/**
 * @author Daniel Zhu
 * @version 09012025
 * Tester class for the Scanner class.
 */
public class ScannerTester
{
    /**
     * Main method for testing the Scanner class.
     * @param args command-line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            // read input from file
            Scanner scanner = new Scanner(new FileInputStream("src.txt"));
            while (scanner.hasNext())
            {
                System.out.println(scanner.nextToken() + " ");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
