begin
prod := 1;
for i := 1 to 100 do begin
    readln(x);
    if x = 0 then break
    else if x = 2 then continue
    else begin
        prod := prod * x;
        writeln(prod);
    end;
end;
writeln(prod);
end;
.
