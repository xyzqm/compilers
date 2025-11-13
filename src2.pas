begin
for i := 1 to 5 do
begin
    for j := 1 to 5 do
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
