import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ast.*;
import parser.Parser;
import scanner.ScanErrorException;

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
     * @throws ScanErrorException If a scanning error occurs.
     * @throws ControlException If a control flow exception occurs.
     * @throws RTException If a runtime exception occurs.
     */
    public static void main(
            String[] args
    ) throws IOException,
             ScanErrorException,
             ControlException,
             RTException
    {
        // ScannerTester.main(args);
        // read src.txt into a String
        String src = new String(Files.readAllBytes(
                Paths.get(args[0])
        ));
        Parser parser = new Parser(src);
        Program p = parser.parseProgram();
        // p.execute();
        p.compile("out.asm");
        // p.execute();
        // p.compile("out.asm");
        // Statement st = new Writeln(new Num("5"));
        // var st = new BinOp(
        //     new Num("5"),
        //     new BinOp(new Num("6"), new Num("2"), Op.ADD),
        //     Op.MUL
        // );
        // Emitter e = new Emitter("out.asm");
        // st.compile(e);
    }
}
