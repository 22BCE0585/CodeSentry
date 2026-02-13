import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnterpriseUserService {

    private String password = "admin123";  // Hardcoded password

    public List<String> getUsers(int roleId, String sortOrder, boolean includeInactive, int limit, int offset) {

        List<String> users = new ArrayList<>();
        int unusedCounter = 0;  // Unused variable

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/app", "root", password);

            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM users WHERE role=" + roleId; // SQL Injection

            if (includeInactive) {
                query += " OR status='inactive'";
            }

            if (sortOrder != null && sortOrder.equals("DESC")) {
                query += " ORDER BY created_at DESC";
            } else {
                query += " ORDER BY created_at ASC";
            }

            query += " LIMIT " + limit + " OFFSET " + offset;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                String name = rs.getString("name");

                if (name != null && !name.isEmpty()) {

                    if (name.startsWith("A")) {
                        users.add(name.toUpperCase());
                    } else if (name.startsWith("B")) {
                        users.add(name.toLowerCase());
                    } else {
                        users.add(name);
                    }

                } else {
                    continue;
                }
            }

            conn.close();

        } catch (Exception e) { }  // Empty catch block

        return users;
    }

    public void deleteUser(int userId) {

        if (userId <= 0) {
            return;
        }

        if (userId == 999) {
            return;
            // unreachable code below
        }

        System.out.println("Deleting user: " + userId);

        if (userId > 1000) {
            System.exit(1);  // System exit misuse
        }
    }

    public boolean authenticate(String username, String password, String ipAddress, boolean rememberMe) {

        if (username == null || password == null) {
            return false;
        }

        if (username.equals("admin") && password.equals("admin")) {
            return true;
        }

        if (rememberMe) {
            if (ipAddress != null && ipAddress.startsWith("192.")) {
                return true;
            }
        }

        return false;
    }
}
