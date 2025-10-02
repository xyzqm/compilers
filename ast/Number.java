package ast;

public class Number implements Expression {
    private String value;

    public Number(String value) {
        this.value = value;
    }

    @Override
    public int eval(Environment env) throws RuntimeException {
        try
        {
            int number = Integer.parseInt(value);
            return number;
        }
        catch (NumberFormatException e)
        {
            if (env.containsKey(value))
            {
                return env.get(value);
            }
            throw new RuntimeException("Expected a factor, but found " + value);
        }
    }
}
