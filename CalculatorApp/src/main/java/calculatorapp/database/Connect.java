package calculatorapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

    public static void createNewDatabase() {

        String url = "jdbc:sqlite:history.db";

        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	operation text NOT NULL,\n"
                + "	result text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection connect() {
        String url = "jdbc:sqlite:history.db";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
