package scanner;

import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * @author Daniel Zhu
 * @version 082025
 *
 * Usage: See ScannerTester
 *
 */
public class Scanner
{

    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    private static final String CHARS = "()<>:=+-*/%;";

    /**
     * Scanner constructor for construction of a scanner that
     * uses an InputStream object for input.
     * Usage:
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * Method: getNextChar
     */
    private boolean getNextChar()
    {
        try
        {
            int ch = in.read();
            if (ch == -1)
            {
                eof = true;
                currentChar = '\0';
                return false;
            }
            else
            {
                currentChar = (char) ch;
                return true;
            }
        }
        catch (IOException e)
        {
            eof = true;
            currentChar = '\0';
            return false;
        }
    }

    /**
     * Method: hasNext
     * @return whether there is a next token
     */
    public boolean hasNext()
    {
        while (isWhitespace(currentChar))
        {
            if (!getNextChar())
            {
                break;
            }
        }
        return !eof;
    }

    /**
     * Method: isIdentifier
     * @param ch the character to check
     * @return whether the character is an identifier
     */
    private static boolean isIdentifier(char ch)
    {
        return ch == '_' || 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z';
    }

    /**
     * Method: isDigit
     * @param ch the character to check
     * @return whether the character is a digit
     */
    private static boolean isDigit(char ch)
    {
        return '0' <= ch && ch <= '9';
    }

    /**
     * Method: isWhitespace
     * @param ch the character to check
     * @return whether the character is a whitespace
     */
    private static boolean isWhitespace(char ch)
    {
        return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r';
    }

    /**
     * Method: nextToken
     * @return the next token
     * @throws ScanErrorException if there are no more tokens or if an invalid token is encountered
     */
    public String nextToken() throws ScanErrorException
    {
        if (!hasNext())
        {
            throw new ScanErrorException("No more tokens");
        }
        StringBuilder token = new StringBuilder(String.valueOf(currentChar));
        if (isIdentifier(currentChar))
        {
            while (getNextChar() && (isIdentifier(currentChar) || isDigit(currentChar)))
            {
                token.append(currentChar);
            }
        }
        else if (isDigit(currentChar))
        {
            while (getNextChar() && isDigit(currentChar))
            {
                token.append(currentChar);
            }
        }
        else if (CHARS.contains("" + currentChar))
        {
            getNextChar();
            if (!eof &&currentChar == '=' &&"<>=:".contains(token.toString()))
            {
                token.append(currentChar);
                getNextChar();
            }
            else if (!eof && currentChar == '/' && token.toString().equals("/"))
            {
                // skip all characters until a newline is encountered
                while (getNextChar() && currentChar != '\n');
                return nextToken();
            }
        }
        else
        {
            throw new ScanErrorException("Unexpected character: " + currentChar);
        }
        return token.toString();
    }
}
