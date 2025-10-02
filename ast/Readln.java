package ast;

public class Readln implements Statement {
    String var;

    public Readln(String var) {
        this.var = var;
    }

	@Override
	public void execute(Environment env) throws RTException {
		env.put(var, Integer.parseInt(System.console().readLine()));
	}
}
