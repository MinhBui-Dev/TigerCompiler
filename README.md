# Tiger compiler for MIPS32

This Java compiler translates Tiger programs into MIPS32 assembly. It can compile the same program with two register allocators, so you can trace a variable from the intermediate representation through liveness analysis to the loads and stores in the final assembly.

## Compile the Fibonacci example

From the repository root, use GNU Make and JDK 17. The repository includes the ANTLR 4.11.1 JAR. Pass its absolute path because the Makefile changes directories when packaging the compiler.

```sh
make ANTLR="$PWD/lib/antlr-4.11.1-complete.jar"
mkdir -p build/examples
cp phase2_testfiles_v1/files/demo_fib.tiger build/examples/
java -jar cs8803_bin/tigerc.jar -f build/examples/demo_fib.tiger -i -v -n -g
```

Look beside the input for `demo_fib.ir`, `demo_fib.liveness`, `demo_fib.naive.s`, and `demo_fib.briggs.s`. Compare the two assembly files around the recursive call. The repository also includes [the Fibonacci source and sample outputs](phase2_testfiles_v1/files/).

## Follow a program through the compiler

| Stage | Where to look |
| --- | --- |
| Parse and check | [Tiger grammar](Tiger.g4) and [semantic analyzer](src/tigercompiler/MyTigerSemanticAnalyzer.java). The analyzer builds the AST and tracks declarations in nested scopes. |
| Generate intermediate code | [IR generator](src/tigercompiler/MyIRGenerator.java) and [instruction classes](src/tiger/irinstruction/). |
| Analyze and allocate registers | [Control-flow and liveness analysis](src/tigercompiler/codeanalysis/) and [naive and Briggs allocators](src/tigercompiler/registorallocator/). |
| Emit assembly | [MIPS generator](src/tigercompiler/MyMIPSGenerator.java) and [MIPS32 instructions](src/tiger/mips32/). |

The [command-line driver](src/tigercompiler/Tigerc.java) also accepts `-c` to write the control-flow graph and `-a` to write the interference graph in Graphviz format. The [semantic error examples](phase2_testfiles_v1/semantic/) exercise cases such as undefined variables and invalid array expressions.
