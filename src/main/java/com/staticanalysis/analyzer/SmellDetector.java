package com.staticanalysis.analyzer;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.staticanalysis.model.Defect;
import com.staticanalysis.model.DefectCollector;

public class SmellDetector extends VoidVisitorAdapter<Void> {

    private String fileName;

    public SmellDetector(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cid, Void arg) {
        super.visit(cid, arg);

        // 🔵 Large Class
        if (cid.getMethods().size() > 10) {
            DefectCollector.addDefect(
                    new Defect(
                            "Code Smell",
                            "Large class detected -> " + cid.getName(),
                            "MAJOR",
                            fileName,
                            cid.getBegin().map(p -> p.line).orElse(-1)
                    )
            );
        }
    }

    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);

        // 🟡 Too Many Parameters
        if (md.getParameters().size() > 3) {
            DefectCollector.addDefect(
                    new Defect(
                            "Code Smell",
                            "Too many parameters in method -> " + md.getName(),
                            "MINOR",
                            fileName,
                            md.getBegin().map(p -> p.line).orElse(-1)
                    )
            );
        }

        // 🟠 Long Method
        if (md.getBody().isPresent()) {
            int lineCount = md.getBody().get().toString().split("\n").length;

            if (lineCount > 30) {
                DefectCollector.addDefect(
                        new Defect(
                                "Code Smell",
                                "Long method detected -> " + md.getName(),
                                "MAJOR",
                                fileName,
                                md.getBegin().map(p -> p.line).orElse(-1)
                        )
                );
            }
        }
    }

    @Override
    public void visit(CatchClause cc, Void arg) {
        super.visit(cc, arg);

        // 🔴 Empty Catch Block
        if (cc.getBody().getStatements().isEmpty()) {
            DefectCollector.addDefect(
                    new Defect(
                            "Code Smell",
                            "Empty catch block detected",
                            "MAJOR",
                            fileName,
                            cc.getBegin().map(p -> p.line).orElse(-1)
                    )
            );
        }
    }
}
