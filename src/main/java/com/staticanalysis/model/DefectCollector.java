package com.staticanalysis.model;

import java.util.ArrayList;
import java.util.List;

public class DefectCollector {
    public static void printSummary() {

    int critical = 0;
    int major = 0;
    int minor = 0;

    for (Defect defect : defects) {
        switch (defect.getSeverity()) {
            case "CRITICAL":
                critical++;
                break;
            case "MAJOR":
                major++;
                break;
            case "MINOR":
                minor++;
                break;
        }
    }

    System.out.println("\n===== SUMMARY =====");
    System.out.println("CRITICAL: " + critical);
    System.out.println("MAJOR: " + major);
    System.out.println("MINOR: " + minor);
    System.out.println("TOTAL: " + defects.size());
}


    private static List<Defect> defects = new ArrayList<>();

    public static void addDefect(Defect defect) {
        defects.add(defect);
    }

    public static List<Defect> getDefects() {
        return defects;
    }

    public static void printReport() {
        System.out.println("\n===== DEFECT REPORT =====");
        for (Defect defect : defects) {
            System.out.println(defect);
        }
    }
}
