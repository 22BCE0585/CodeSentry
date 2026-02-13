package com.staticanalysis.analyzer;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.staticanalysis.model.Defect;
import com.staticanalysis.model.DefectCollector;

public class SecurityAnalyzer extends VoidVisitorAdapter<Void> {

    private String fileName;

    public SecurityAnalyzer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void visit(VariableDeclarator vd, Void arg) {
        super.visit(vd, arg);

        // 🔴 Hardcoded password
        if (vd.getNameAsString().toLowerCase().contains("password")
                && vd.getInitializer().isPresent()
                && vd.getInitializer().get() instanceof StringLiteralExpr) {

            DefectCollector.addDefect(
                    new Defect(
                            "Security",
                            "Hardcoded password -> " + vd.getNameAsString(),
                            "CRITICAL",
                            fileName,
                            vd.getBegin().map(p -> p.line).orElse(-1)
                    )
            );
        }
    }

    @Override
    public void visit(BinaryExpr be, Void arg) {
        super.visit(be, arg);

        // 🔴 Possible SQL Injection
        if (be.getOperator() == BinaryExpr.Operator.PLUS) {

            String expression = be.toString().toLowerCase();

            if (expression.contains("select")
                    || expression.contains("insert")
                    || expression.contains("update")
                    || expression.contains("delete")) {

                DefectCollector.addDefect(
                        new Defect(
                                "Security",
                                "Possible SQL Injection -> " + be.toString(),
                                "CRITICAL",
                                fileName,
                                be.getBegin().map(p -> p.line).orElse(-1)
                        )
                );
            }
        }
    }

    @Override
    public void visit(MethodCallExpr mce, Void arg) {
        super.visit(mce, arg);

        // 🟠 System.exit misuse
        if (mce.getNameAsString().equals("exit")
                && mce.getScope().isPresent()
                && mce.getScope().get().toString().equals("System")) {

            DefectCollector.addDefect(
                    new Defect(
                            "Security",
                            "System.exit() usage detected",
                            "MAJOR",
                            fileName,
                            mce.getBegin().map(p -> p.line).orElse(-1)
                    )
            );
        }
    }
}
