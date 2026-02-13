package com.staticanalysis.analyzer;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassAnalyzer extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(ClassOrInterfaceDeclaration cid, Void arg) {
        super.visit(cid, arg);

        System.out.println("Class Name: " + cid.getName());
        System.out.println("Number of Methods: " + cid.getMethods().size());
        System.out.println("Number of Fields: " + cid.getFields().size());
        System.out.println("-----------------------------------");
    }
}
