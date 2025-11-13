BEGIN
count := 0;
WHILE count <= 15 DO
BEGIN
    count := count + 1;
    if count = 5 then continue;
    if count > 12 then break;
    WRITELN(count);
END;
for i := 1 to 10 do
begin
    if i = 3 then continue;
    if i = 7 then break;
    WRITELN(i);
end;
END;
