package com.staticanalysis;

import java.io.File;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.staticanalysis.analyzer.*;
import com.staticanalysis.metrics.ComplexityAnalyzer;
import com.staticanalysis.model.DefectCollector;
import com.staticanalysis.parser.CodeParser;
import com.staticanalysis.parser.FileScanner;
import com.staticanalysis.report.ReportGenerator;

public class Main {

    public static void main(String[] args) {

        String projectPath;

        if (args.length > 0) {
            projectPath = args[0];
        } else {
            projectPath = "test-input";
        }

        CodeParser parser = new CodeParser();

        List<File> javaFiles = FileScanner.scanJavaFiles(projectPath);

        for (File file : javaFiles) {

            CompilationUnit cu = parser.parseFile(file.getAbsolutePath());
            String fileName = file.getName();

            if (cu != null) {

                // Structural (optional but good to keep)
                new ClassAnalyzer().visit(cu, null);
                new MethodAnalyzer().visit(cu, null);

                // Code Smells
                new SmellDetector(fileName).visit(cu, null);

                // Dead Code
                new DeadCodeDetector(fileName).visit(cu, null);

                // Security Issues
                new SecurityAnalyzer(fileName).visit(cu, null);

                // Complexity-based severity
                new ComplexityAnalyzer(fileName).visit(cu, null);
            }
        }

        // Final reporting
        DefectCollector.printReport();
        DefectCollector.printSummary();
        ReportGenerator.generateJsonReport(DefectCollector.getDefects());
        ReportGenerator.generateHtmlReport(DefectCollector.getDefects());
    }
}
