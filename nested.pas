begin
readln(n);
i := 1;
while i <= n do
begin
    j := 1;
    while j <= n do
    begin
        if j > i then break;
        write(j);
        j := j + 1;
    end;
    i := i + 1;
    writeln();
end;
writeln(j); // should be 0 since the local j has been destroyed
end;
