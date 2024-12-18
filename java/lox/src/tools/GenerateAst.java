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

        writer.println("package lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + "{");
        writer.println();

        for(String type : types){
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
            writer.println();
        }

        writer.println("}");
        writer.close();
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
