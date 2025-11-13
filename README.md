To run the code, modify `src.pas` and run the following command:

```
java Main.java && java -jar Mars4_5.jar out.asm
```

# Design Notes

Variable scope works in a similar fashion as Python.

For instance:

```
j := 0;
for i from 0 to 10: j := i;
writeln(j);
```

will output 10.

However,

```
for i from 0 to 10: j := i;
writeln(j);
```

will output 0 because since `j` is not declared before the loop, it becomes local to the loop body.
