package ast;

import environment.Environment;

public class Program extends Environment {
    private Statement body;

    public void setBody(Statement body) {
        this.body = body;
    }

	public void execute() throws RTException, ControlException
	{
	    body.execute(this);
	}
}
