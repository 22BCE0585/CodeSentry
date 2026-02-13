package com.staticanalysis.model;

public class Defect {

    private String type;
    private String message;
    private String severity;
    private String fileName;
    private int lineNumber;

    public Defect(String type, String message, String severity,String fileName, int lineNumber) {
        this.type = type;
        this.message = message;
        this.severity = severity;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public String getType() { return type; }
    public String getMessage() { return message; }
    public String getSeverity() { return severity; }
    public String getFileName() { return fileName; }
    public int getLineNumber() { return lineNumber; }

    @Override
    public String toString() {
        return "[ " + severity + " ] "
                + type + " | "
                + fileName + " | Line "
                + lineNumber + " -> "
                + message;
    }
}
