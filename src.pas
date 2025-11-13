BEGIN
count := 0;
WHILE count <= 15 DO
BEGIN
    count := count + 1;
    if count = 5 then continue;
    if count > 12 then break;
    WRITELN(count);
END;
END;
