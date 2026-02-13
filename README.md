# CodeSentry
### AST-Based Static Code Analysis & Automated Defect Detection Framework

CodeSentry is a modular static code analysis engine built in Java using AST-based parsing (JavaParser).  
It detects code smells, dead code, security vulnerabilities, and computes software metrics such as cyclomatic complexity.

The tool classifies defects by severity (CRITICAL / MAJOR / MINOR) and generates structured JSON and HTML reports with file-level traceability.

---

## Features

### Static Analysis Capabilities

#### Code Smell Detection
- Too many parameters
- Long methods
- Large classes
- Empty catch blocks

#### ☠ Dead Code Detection
- Unused variables
- Unreachable code

#### Security Vulnerability Detection
- Hardcoded passwords
- SQL injection patterns
- System.exit misuse

#### Software Metrics
- Cyclomatic complexity computation
- Severity escalation based on complexity thresholds

---

## Defect Classification Model

Each detected issue is classified into one of three severity levels:

| Severity  | Meaning |
|------------|------------|
| CRITICAL | High-impact security or logic flaw |
| MAJOR | Significant structural or maintainability issue |
| MINOR | Low-risk code quality issue |

Each defect includes:
- File name
- Line number
- Severity
- Defect type
- Detailed message

---

## Architecture Overview

Target Java Source Code
↓
JavaParser (AST Construction)
↓
Analyzers
├── SecurityAnalyzer
├── SmellDetector
├── DeadCodeDetector
├── ComplexityAnalyzer
↓
Defect Model
↓
DefectCollector
↓
ReportGenerator
├── Console Report
├── JSON Report
└── HTML Dashboard


---

## Project Structure

CodeSentry/
│
├── src/main/java/com/staticanalysis/
│ ├── analyzer/
│ │ ├── SmellDetector.java
│ │ ├── DeadCodeDetector.java
│ │ ├── SecurityAnalyzer.java
│ │
│ ├── metrics/
│ │ └── ComplexityAnalyzer.java
│ │
│ ├── model/
│ │ ├── Defect.java
│ │ └── DefectCollector.java
│ │
│ ├── parser/
│ │ ├── CodeParser.java
│ │ └── FileScanner.java
│ │
│ ├── report/
│ │ └── ReportGenerator.java
│ │
│ └── Main.java
│
├── test-input/
│ └── EnterpriseUserService.java
│
├── report.json
├── report.html
└── pom.xml


---

## Requirements

- Java 17+
- Maven 3.8+

Verify installation:
java -version
mvn -version


---

## Installation

Clone the repository:



Build the project:
mvn clean install


---

## Usage

Analyze a folder containing Java files:
mvn exec:java "-Dexec.mainClass=com.staticanalysis.Main" -Dexec.args="test-input"


Or analyze any external Java project:
mvn exec:java "-Dexec.mainClass=com.staticanalysis.Main" -Dexec.args="path/to/java/project"


---

## Output

### Console Output
- Structured defect report
- Severity summary dashboard

Example:
[ CRITICAL ] Security | EnterpriseUserService.java | Line 10 -> Hardcoded password -> password
[ MAJOR ] Code Smell | EnterpriseUserService.java | Line 12 -> Long method detected
[ MINOR ] Dead Code | EnterpriseUserService.java | Line 15 -> Unused variable

===== SUMMARY =====
CRITICAL: 4
MAJOR: 4
MINOR: 6
TOTAL: 14

---

### Generated Files

#### report.json
Machine-readable structured defect report.

#### report.html
Styled dashboard report with:
- Colored severity rows
- File & line traceability
- Structured defect table

---

## Concepts Demonstrated

- Static Code Analysis
- Abstract Syntax Tree (AST) Traversal
- Automated Defect Classification
- Cyclomatic Complexity Metrics
- Secure Coding Analysis
- Severity-Based Reporting
- Modular Software Architecture
- Automated Report Generation

---

## Sample Enterprise Test Case

CodeSentry has been validated against:
- Service-layer business logic
- Database interaction code
- Authentication modules
- Nested control-flow structures
- Realistic vulnerability patterns

---

## Future Enhancements

- Configurable rule thresholds (rules.properties)
- Multi-language support (C++)
- CI/CD integration
- SARIF output format
- ML-based defect prediction
- Interactive web dashboard

---

## License

This project is intended for educational and research purposes.

