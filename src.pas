begin
i := 1;
j := 1;
while i <= 5 do
begin
    j := 1;
    while j <= 5 do
    begin
        if j > i then break;
        write(j);
        j := j + 1;
    end;
    i := i + 1;
    writeln();
end;
writeln(j);
end;
