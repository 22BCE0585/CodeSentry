package com.staticanalysis.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {

    public static List<File> scanJavaFiles(String directoryPath) {

        List<File> javaFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            System.out.println("Directory not found.");
            return javaFiles;
        }

        scan(directory, javaFiles);
        return javaFiles;
    }

    private static void scan(File file, List<File> javaFiles) {

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                scan(f, javaFiles);
            }
        } else {
            if (file.getName().endsWith(".java")) {
                javaFiles.add(file);
            }
        }
    }
}
