package com.staticanalysis.parser;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

public class CodeParser {

    public CompilationUnit parseFile(String filePath) {

        try {
            File file = new File(filePath);

            JavaParser parser = new JavaParser();
            ParseResult<CompilationUnit> result = parser.parse(file);

            if (result.getResult().isPresent()) {
                return result.getResult().get();
            } else {
                System.out.println("Parsing failed.");
                return null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
