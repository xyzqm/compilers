package parser;

import java.util.HashMap;
import java.util.Map;

import scanner.ScanErrorException;
import scanner.Scanner;
import scanner.EOFException;
import ast.BinOp;
import ast.Expression;
import ast.Fn;
import ast.Number;
import ast.Statement;
import ast.Writeln;

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
    boolean eof = false;
    java.util.Scanner sc = new java.util.Scanner(System.in);

    /**
     * Constructor for Parser class.
     * @param input The input string to be parsed.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    public Parser(String input) throws ScanErrorException
    {
        scanner = new Scanner(input);
        eat("");
    }

    /**
     * Consumes the current token if it matches the expected token.
     * @param token The expected token.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    void eat(String token) throws ScanErrorException
    {
        try
        {
            if (!currentToken.equals(token))
            {
                throw new RuntimeException("Expected " + token + ", but found " + currentToken);
            }
            currentToken = scanner.nextToken();
        }
        catch (ScanErrorException e)
        {
            if (e instanceof EOFException)
            {
                if (!eof)
                {
                    eof = true;
                    currentToken = "EOF";
                }
                else
                {
                    throw new ScanErrorException("Unexpected EOF");
                }
            }
            else
            {
                throw e;
            }
        }
    }

    /**
     * Parses a number token.
     * @return The parsed number.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    private Number parseNumber() throws ScanErrorException
    {
        Number n = new Number(currentToken);
        eat(currentToken);
        return n;
    }

    /**
     * Parses a factor consisting of a number, a variable, or a parenthesized expression.
     * @return The parsed factor.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    private Expression parseFactor() throws ScanErrorException
    {
        if (currentToken.equals("("))
        {
            eat("(");
            BinOp bo = parseExpression();
            eat(")");
            return bo;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            return new BinOp(new Number("0"), parseFactor(), (a, b) -> a - b);
        }
        else
        {
            return parseNumber();
        }
    }

    /**
     * Parses an expression consisting of negation, *, and /
     * @return the result of the expression
     * @throws ScanErrorException If an error occurs during scanning.
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression cur = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            String op = currentToken;
            eat(currentToken);
            Fn f = (a, b) -> op.equals("*") ? a * b : a / b;
            cur = new BinOp(cur, parseFactor(), f);
        }
        return cur;
    }

    /**
     * Parses field expressions.
     * @return the result of the expression
     * @throws ScanErrorException If an error occurs during scanning.
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression cur = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String op = currentToken;
            eat(currentToken);
            Fn f = (a, b) -> op.equals("+") ? a + b : a - b;
            cur = new BinOp(cur, parseTerm(), f);
        }
        return cur;
    }

    /**
     * Parses statements of the form WRITELN(expr) or BEGIN stmts END;
     * @throws ScanErrorException If an error occurs during scanning.
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Statement s = new Writeln(parseExpression());
            eat(")");
        }
        else if (currentToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            String var = currentToken;
            eat(var);
            eat(")");
            vars.put(var, Integer.parseInt(sc.nextLine()));
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
        // System.out.println("Statement parsed successfully");
    }
}
