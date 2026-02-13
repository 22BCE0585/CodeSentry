public class SampleTest {

    private int value;
    private String password = "12345";  // hardcoded password

    public void testMethod(int a, int b, int c, int d) {

        int unusedVar = 10;

        String query = "SELECT * FROM users WHERE id=" + a;  // SQL injection pattern

        if (a > 10) {
            return;
        }

        System.exit(0);  // System exit misuse
    }
}
