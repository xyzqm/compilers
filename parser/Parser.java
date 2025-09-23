package parser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * @author Daniel Zhu
 * @version 082025
 *
 * Usage: See ScannerTester
 *
 */
public class Parser
{
    Scanner scanner;
    String currentToken = "";

    // map from variable to integer values
    Map<String, Integer> vars = new HashMap<>();

    public Parser(String input) throws ScanErrorException {
        scanner = new Scanner(input);
        eat("");
    }

    void eat(String token) throws ScanErrorException
    {
        if (!currentToken.equals(token)) {
            throw new RuntimeException("Expected " + token + ", but found " + currentToken);
        }
        currentToken = scanner.nextToken();
    }

    private int parseNumber() throws ScanErrorException {
        try {
            int number = Integer.parseInt(currentToken);
            eat(currentToken);
            return number;
        } catch (NumberFormatException e) {
            if (vars.containsKey(currentToken)) {
                int value = vars.get(currentToken);
                eat(currentToken);
                return value;
            }
            throw new ScanErrorException("Expected a factor, but found " + currentToken);
        }
    }

    private int parseFactor() throws ScanErrorException {
        if (currentToken.equals("("))
        {
            eat("(");
            int n = parseExpression();
            eat(")");
            return n;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            return -parseFactor();
        }
        else
        {
            return parseNumber();
        }
    }

    /**
    * Parses an expression consisting of negation, *, and /
    * @return the result of the expression
    * @throws ScanErrorException
 */
    private int parseTerm() throws ScanErrorException
    {
        int n = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            String op = currentToken;
            eat(currentToken);
            int m = parseFactor();
            if (op.equals("*"))
            {
                n *= m;
            }
            else
            {
                n /= m;
            }
        }
        return n;
    }

    /**
    * Parses field expressions.
    * @return the result of the expression
    * @throws ScanErrorException
 */
    private int parseExpression() throws ScanErrorException
    {
        int n = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String op = currentToken;
            eat(currentToken);
            int m = parseTerm();
            if (op.equals("+"))
            {
                n += m;
            }
            else
            {
                n -= m;
            }
        }
        return n;
    }

    /**
     * Parses statements of the form WRITELN(expr) or BEGIN stmts END;
     */
    public void parseStatement() throws ScanErrorException {
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            System.out.println(parseExpression());
            eat(")");
        }
        else if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            while (!currentToken.equals("END"))
            {
                parseStatement();
            }
            eat("END");
        }
        else // this is an assignment of the form x := expr;
        {
            String var = currentToken;
            eat(var);
            eat(":=");
            int value = parseExpression();
            vars.put(var, value);
        }
        eat(";");
    }
}
