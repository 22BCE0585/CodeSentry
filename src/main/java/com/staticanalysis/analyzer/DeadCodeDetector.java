package com.staticanalysis.analyzer;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.staticanalysis.model.Defect;
import com.staticanalysis.model.DefectCollector;

public class DeadCodeDetector extends VoidVisitorAdapter<Void> {

    private String fileName;

    public DeadCodeDetector(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);

        if (md.getBody().isPresent()) {

            BlockStmt body = md.getBody().get();

            //Detect Unreachable Code
            md.findAll(ReturnStmt.class).forEach(returnStmt -> {
                returnStmt.getParentNode().ifPresent(parent -> {

                    if (parent instanceof BlockStmt) {

                        BlockStmt block = (BlockStmt) parent;
                        boolean foundReturn = false;

                        for (Statement stmt : block.getStatements()) {

                            if (foundReturn) {

                                DefectCollector.addDefect(
                                        new Defect(
                                                "Dead Code",
                                                "Unreachable statement detected in method -> " + md.getName(),
                                                "MAJOR",
                                                fileName,
                                                stmt.getBegin().map(p -> p.line).orElse(-1)
                                        )
                                );
                                break;
                            }

                            if (stmt == returnStmt) {
                                foundReturn = true;
                            }
                        }
                    }
                });
            });

            //Detect Unused Variables
            Set<String> declaredVars = new HashSet<>();
            Set<String> usedVars = new HashSet<>();

            body.findAll(VariableDeclarator.class)
                    .forEach(v -> declaredVars.add(v.getNameAsString()));

            body.findAll(NameExpr.class)
                    .forEach(n -> usedVars.add(n.getNameAsString()));

            for (String var : declaredVars) {

                if (!usedVars.contains(var)) {

                    DefectCollector.addDefect(
                            new Defect(
                                    "Dead Code",
                                    "Unused variable -> " + var + " in method -> " + md.getName(),
                                    "MINOR",
                                    fileName,
                                    md.getBegin().map(p -> p.line).orElse(-1)
                            )
                    );
                }
            }
        }
    }
}
