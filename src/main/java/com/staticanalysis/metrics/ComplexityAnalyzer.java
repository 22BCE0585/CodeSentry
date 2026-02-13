package com.staticanalysis.metrics;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ComplexityAnalyzer extends VoidVisitorAdapter<Void> {
    private String fileName;

    public ComplexityAnalyzer(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);

        int complexity = 1; // base complexity

        // Count if statements
        complexity += md.findAll(IfStmt.class).size();

        // Count for loops
        complexity += md.findAll(ForStmt.class).size();

        // Count while loops
        complexity += md.findAll(WhileStmt.class).size();

        // Count do-while loops
        complexity += md.findAll(DoStmt.class).size();

        // Count switch cases
        complexity += md.findAll(SwitchEntry.class).size();

        // Count catch blocks
        complexity += md.findAll(CatchClause.class).size();

        // Count logical operators (&&, ||)
        for (BinaryExpr expr : md.findAll(BinaryExpr.class)) {
            if (expr.getOperator() == BinaryExpr.Operator.AND ||
                expr.getOperator() == BinaryExpr.Operator.OR) {
                complexity++;
            }
        }

        System.out.println("Method Name: " + md.getName());
        System.out.println("Cyclomatic Complexity: " + complexity);
        System.out.println("-----------------------------------");
    }
}
