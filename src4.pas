procedure bar();
writeln(x);

procedure wrong();
begin
    x := 5;
    bar();
end;

begin
    wrong();
end;
