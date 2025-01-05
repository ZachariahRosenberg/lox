package tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    
    public static void main(String[] args) throws IOException{
        if(args.length!=1){
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }

        String outputDir = args[0];

        defineAst(outputDir, "Expr", Arrays.asList(
            "Binary: Expr left, Token operator, Expr right",
            "Grouping: Expr expression",
            "Literal: Object value",
            "Unary: Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException{
        String path = outputDir + "/" + baseName + ".java";

        PrintWriter writer = new PrintWriter(path, "UTF-8");

        // Imports
        writer.println("package lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();

        // Class Definition
        writer.println("abstract class " + baseName + "{");
        writer.println();

        // Visitor interface
        defineVisitor(writer, baseName, types);
        writer.println();

        // Base accept() class for visitor pattern
        writer.println(addTab(1) + "abstract <R> R accept(Visitor<R> visitor);");
        writer.println();

        // Individual Type Classes
        for(String type : types){
            // Type definition
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
            writer.println();
        }

        writer.println("}");
        writer.close();
    }
    
    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types){
        writer.println(addTab(1) + "interface Visitor<R> {");

        for(String type:types){
            String typeName = type.split(":")[0].trim();
            // R visitLiteralExpr(Literal expr);
            writer.println(addTab(2) + "R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase()+");");
        }

        writer.println(addTab(1) + "}");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList){
        writer.println(addTab(1) + "static class " + className + " extends " + baseName + " {");
        //constructor
        writer.println(addTab(2) + className + "(" + fieldList + "){");
        String[] fields = fieldList.split(", ");
        for(String field:fields){
            String name = field.split(" ")[1];
            writer.println(addTab(3) + "this." + name + " = " + name + ";");
        }
        writer.println(addTab(2) + "}");

        writer.println();

        // Visitor pattern implementation
        writer.println(addTab(2)+"@Override");
        writer.println(addTab(2)+"<R> R accept(Visitor<R> visitor) {");
        writer.println(addTab(3)+"return visitor.visit" + className + baseName + "(this);");
        writer.println(addTab(2)+"}");
        writer.println();

        // Fields
        for(String field: fields){
            writer.println(addTab(2) + "final " + field + ";");
        }

        writer.println(addTab(1) + "}");
    }
  

    private static String addTab(int tabs){
        String tab = "";
        for(int i = 0; i < tabs; i++){
            tab += "    ";
        }
        return tab;
    }

}
