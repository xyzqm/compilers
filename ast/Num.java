package ast;

public class Num implements Expression {
    private String value;

    public Num(String value) {
        this.value = value;
    }

    @Override
    public int eval(Environment env) throws RTException {
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
            throw new RTException("Expected a factor, but found " + value);
        }
    }
}
