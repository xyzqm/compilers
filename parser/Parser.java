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
    final String[] ops = {"*/", "+-", "<><= >=", "and", "or"};
    final Map<String, Fn> fns = Map.ofEntries(
            entry("*", (a, b) -> a * b),
            entry("+", (Fn) (a, b) -> a + b),
            entry("-", (Fn) (a, b) -> a - b),
            entry("/", (Fn) (a, b) -> a / b),
            entry("<", (Fn) (a, b) -> a < b ? 1 : 0),
            entry(">", (Fn) (a, b) -> a > b ? 1 : 0),
            entry("<=", (Fn) (a, b) -> a <= b ? 1 : 0),
            entry(">=", (Fn) (a, b) -> a >= b ? 1 : 0),
            entry("=", (Fn) (a, b) -> a == b ? 1 : 0),
            entry("<>", (Fn) (a, b) -> a != b ? 1 : 0),
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
     * Checks if the current token matches the expected token.
     * @param token The expected token.
     * @return True if the current token matches the expected token, false otherwise.
     */
    boolean isToken(String token)
    {
        return currentToken.toUpperCase().equals(token.toUpperCase());
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
            if (!isToken(token))
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
     * Parses if statements.
     * @return the AST of the statement.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    private Statement parseIf() throws ScanErrorException
    {
        eat("IF");
        Expression cond = parseExpression();
        eat("THEN");
        Statement then = parseStatement(false);
        Statement els = null;
        if (isToken("ELSE"))
        {
            eat("ELSE");
            els = parseStatement(false);
        }
        return new If(cond, then, els);
    }

    /**
     * Parses while statements.
     * @return the AST of the statement.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    private Statement parseWhile() throws ScanErrorException
    {
        eat("WHILE");
        Expression cond = parseExpression();
        eat("DO");
        Statement body = parseStatement();
        return new While(cond, body);
    }

    private Statement parseFor() throws ScanErrorException
    {
        eat("FOR");
        String id = currentToken;
        eat(currentToken);
        eat(":=");
        Expression l = parseExpression();
        eat("TO");
        Expression r = parseExpression();
        eat("DO");
        Statement body = parseStatement();
        return new For(id, l, r, body);
    }

    /**
     * Parses statements of the form WRITELN(expr) or BEGIN stmts END;
     * @return the AST of the statement.
     * @throws ScanErrorException If an error occurs during scanning.
     */
    public Statement parseStatement(boolean eatSemi) throws ScanErrorException
    {
        Statement s = null;
        if (isToken("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            s = new Writeln(parseExpression());
            eat(")");
        }
        else if (isToken("READLN"))
        {
            eat("READLN");
            eat("(");
            s = new Readln(currentToken);
            eat(currentToken);
            eat(")");
        }
        else if (isToken("BEGIN"))
        {
            Block b = new Block();
            eat("BEGIN");
            while (!isToken("END"))
            {
                b.addStatement(parseStatement());
            }
            eat("END");
            s = b;
        }
        else if (isToken("IF"))
        {
            s = parseIf();
        }
        else if (isToken("WHILE"))
        {
            return parseWhile();
        }
        else if (isToken("FOR"))
        {
            return parseFor();
        }
        else if (isToken("BREAK"))
        {
            eat("BREAK");
            s = new Control<Break>(new Break());
        }
        else if (isToken("CONTINUE"))
        {
            eat("CONTINUE");
            s = new Control<Continue>(new Continue());
        }
        else // this is an assignment of the form x := expr;
        {
            String var = currentToken;
            eat(var);
            eat(":=");
            s = new Assign(var, parseExpression());
        }
        if (eatSemi)
        {
            eat(";");
        }
        return s;
    }
    public Statement parseStatement() throws ScanErrorException
    {
        return parseStatement(true);
    }
}
