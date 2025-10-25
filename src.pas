PROCEDURE max(x, y);
BEGIN
max := x;
IF y > x THEN max := y;
WRITELN(max);
END;
max(3, 5);
