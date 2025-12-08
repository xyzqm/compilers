PROCEDURE foo(d, g);
ignore := bar(d + g);

PROCEDURE bar(g);
WRITELN(g);

BEGIN
f := 2;
WRITELN(f);
ignore := foo(3, f);
WRITELN(f);
END;
