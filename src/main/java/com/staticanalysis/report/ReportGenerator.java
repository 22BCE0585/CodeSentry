package com.staticanalysis.report;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.staticanalysis.model.Defect;

public class ReportGenerator {
    public static void generateHtmlReport(List<Defect> defects) {

    try {
        File file = new File("report.html");
        java.io.PrintWriter writer = new java.io.PrintWriter(file);

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Static Analysis Report</title>");
        writer.println("<style>");
        writer.println("body { font-family: Arial; }");
        writer.println("table { border-collapse: collapse; width: 100%; }");
        writer.println("th, td { border: 1px solid black; padding: 8px; text-align: left; }");
        writer.println("th { background-color: #f2f2f2; }");
        writer.println(".CRITICAL { background-color: #ffcccc; }");
        writer.println(".MAJOR { background-color: #ffe0b3; }");
        writer.println(".MINOR { background-color: #ffffcc; }");
        writer.println("</style>");
        writer.println("</head>");
        writer.println("<body>");

        writer.println("<h1>Static Code Analysis Report</h1>");

        writer.println("<table>");
        writer.println("<tr><th>Severity</th><th>Type</th><th>Message</th></tr>");

        for (Defect defect : defects) {
            writer.println("<tr class='" + defect.getSeverity() + "'>");
            writer.println("<td>" + defect.getSeverity() + "</td>");
            writer.println("<td>" + defect.getType() + "</td>");
            writer.println("<td>" + defect.getMessage() + "</td>");
            writer.println("</tr>");
        }

        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");

        writer.close();

        System.out.println("HTML report generated: report.html");

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static void generateJsonReport(List<Defect> defects) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File("report.json"), defects);
            System.out.println("JSON report generated: report.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
