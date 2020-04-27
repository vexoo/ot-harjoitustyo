
package calculatorapp.controller;

import calculatorapp.database.DatabaseConnection;
import java.io.File;
import java.sql.SQLException;
import javafx.scene.control.TableView;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class DatabaseConnectionTest {

    Calculations calculations;
    DatabaseConnection connection;
    static File file;
    TableView tableView;

    @Before
    public void setUp() {
        connection = new DatabaseConnection("jdbc:sqlite:assets/test.db", "test");
        file = new File("assets/test.db");
        connection.createNewDatabase();
        tableView = new TableView();
    }

    @Test(expected = Test.None.class)
    public void testInsert() {
        connection.insert("1 + 1 =", "2");
    }

    @Test(expected = Test.None.class)
    public void testBuildFromDatabase() {
        connection.insert("1 + 1 =", "2");
        connection.buildDataFromDatabase(tableView);
    }

    @Test(expected = Test.None.class)
    public void testDatabaseDeletion() {
        connection.delete();
    }

    @AfterClass
    public static void tearDown() {
        file.delete();
    }
}
