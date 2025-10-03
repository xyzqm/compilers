package parser;

import scanner.ScanErrorException;
import scanner.Scanner;
import scanner.EOFException;

import java.util.Map;
import static java.util.Map.entry;

import ast.*;

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
    final String[] ops = {"*/", "+-", "<><=>=", "and", "or"};
    final Map<String, Fn> fns = Map.ofEntries(
            entry("*", (a, b) -> a * b),
            entry("+", (Fn) (a, b) -> a + b),
            entry("-", (Fn) (a, b) -> a - b),
            entry("/", (Fn) (a, b) -> a / b),
            entry("<", (Fn) (a, b) -> a < b ? 1 : 0),
            entry(">", (Fn) (a, b) -> a > b ? 1 : 0),
            entry("<=", (Fn) (a, b) -> a <= b ? 1 : 0),
            entry(">=", (Fn) (a, b) -> a >= b ? 1 : 0),
            entry("==", (Fn) (a, b) -> a == b ? 1 : 0),
            entry("!=", (Fn) (a, b) -> a != b ? 1 : 0),
            entry("and", (Fn) (a, b) -> a != 0 ? b : a),
            entry("or", (Fn) (a, b) -> a != 0 ? a : b)
        );
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
                throw new ScanErrorException("Expected " + token + ", but found " + currentToken);
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
    private Num parseNumber() throws ScanErrorException
    {
        Num n = new Num(currentToken);
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
            Expression bo = parseExpression();
            eat(")");
            return bo;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            return new BinOp(new Num("0"), parseFactor(), (a, b) -> a - b);
        }
        else
        {
            return parseNumber();
        }
    }

    /**
     * Parses expressions of a given level.
     * @return the AST of the expression.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    private Expression parseExpression(int level) throws ScanErrorException
    {
        if (level == -1)
        {
            return parseFactor();
        }
        Expression cur = parseExpression(level - 1);
        while (ops[level].contains(currentToken))
        {
            String op = currentToken;
            eat(currentToken);
            cur = new BinOp(cur, parseExpression(level - 1), fns.get(op));
        }
        return cur;
    }

    /**
     * Parses expressions of the highest level.
     * @return the AST of the expression.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    private Expression parseExpression() throws ScanErrorException
    {
        return parseExpression(ops.length - 1);
    }

    /**
     * Parses statements of the form WRITELN(expr) or BEGIN stmts END;
     * @return the AST of the statement.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    public Statement parseStatement() throws ScanErrorException
    {
        Statement s = null;
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            s = new Writeln(parseExpression());
            eat(")");
        }
        else if (currentToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            s = new Readln(currentToken);
            eat(currentToken);
            eat(")");
        }
        else if (currentToken.equals("BEGIN"))
        {
            Block b = new Block();
            eat("BEGIN");
            while (!currentToken.equals("END"))
            {
                b.addStatement(parseStatement());
            }
            eat("END");
            s = b;
        }
        else // this is an assignment of the form x := expr;
        {
            String var = currentToken;
            eat(var);
            eat(":=");
            s = new Assign(var, parseExpression());
        }
        eat(";");
        return s;
    }
}
