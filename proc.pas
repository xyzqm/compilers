procedure fact(n);
begin
    if n <= 1 then fact := 1
    else fact := n * fact(n - 1);
end;

procedure fib(n);
begin
    if n <= 1 then fib := 1
    else fib := fib(n - 1) + fib(n - 2);
end;

begin
WRITELN(fact(10));
WRITELN(fib(10));
end;
