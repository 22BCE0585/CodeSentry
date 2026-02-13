package com.staticanalysis.analyzer;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodAnalyzer extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);

        System.out.println("Method Name: " + md.getName());
        System.out.println("Number of Parameters: " + md.getParameters().size());
        System.out.println("-----------------------------------");
    }
}
