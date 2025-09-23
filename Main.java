import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import parser.ParserTester;
import scanner.ScannerTester;

/**
* @author Daniel Zhu
* @version 09012025
* Main class for the compiler project.
 */
public class Main
{
    /**
     * Main method for the compiler project.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) throws IOException
    {
        // read src.txt into a String
        String src = new String(Files.readAllBytes(Paths.get("src.txt")));
        ParserTester.main(new String[]{src});
    }
}
