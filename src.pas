PROCEDURE max(x, y);
BEGIN
    max := x;
    IF y > x THEN max := y;
    z := x + y;
    WRITELN(max);
    WRITELN(z);
END;
PROCEDURE min(x, y);
BEGIN
    min := x;
    IF y < x THEN min := y;
    z := x + y;
    WRITELN(min);
    WRITELN(z);
END;

BEGIN
    max(3, 5);
    WRITELN(min(3, 5));
END;
