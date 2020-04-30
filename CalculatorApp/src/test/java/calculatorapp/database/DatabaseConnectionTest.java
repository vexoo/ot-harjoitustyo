package calculatorapp.database;

import calculatorapp.controller.Calculations;
import de.saxsys.javafx.test.JfxRunner;
import java.io.File;
import javafx.scene.control.TableView;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
public class DatabaseConnectionTest {

    Calculations calculations;
    DatabaseConnection connection;
    static File file;
    TableView tableView;

    @Before
    public void setUp() {
        connection = new DatabaseConnection("jdbc:sqlite:test.db", "test");
        file = new File("test.db");
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
