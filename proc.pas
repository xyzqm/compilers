procedure fact(n);
begin
    if n <= 1 then fact := 1
    else fact := n * fact(n - 1);
end;

begin
WRITELN(fact(10));
end;
