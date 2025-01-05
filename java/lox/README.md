# Lox on the JVM

Java Version 21

## Getting Started

Code should be added under the `src/lox/` directory. Make sure all files in the package being with `package lox;`

Compiled code gets saved under the `bin` directory.

## Commands

_Compile source code_
`javac -d bin src/lox/*.java`

_Run program_
`java -cp bin lox.Lox`
`java -cp bin lox.AstPrinter`

_To use the generate AST tool_
`java src/tools/GenerateAst.java src/lox`
