package ast;

public class Factor implements Expression {
    private String value;

    public Factor(String value) {
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
