To run the code, modify `src.pas` and run the following command:

```
java Main.java && java -jar Mars4_5.jar out.asm
```

# Design Notes

Variable scope works in the same fashion as Python.

For instance:

```
for i from 0 to 10: j := i;
writeln(j);
```

Will output 10.
