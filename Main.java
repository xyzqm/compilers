import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import parser.ParserTester;

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
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException
    {
        // ScannerTester.main(args);
        // read src.txt into a String
        String src = new String(Files.readAllBytes(Paths.get("src4.pas")));
        ParserTester.main(new String[]{src});
    }
}
