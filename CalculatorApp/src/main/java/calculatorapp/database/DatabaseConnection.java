package calculatorapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DatabaseConnection {

    private ObservableList<ObservableList> data;

    public DatabaseConnection() {
        data = FXCollections.observableArrayList();
    }

    public static void createNewDatabase() {

        String url = "jdbc:sqlite:history.db";

        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
                + "	operation text NOT NULL,\n"
                + "	result text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String operation, String result) {
        ObservableList<String> row = FXCollections.observableArrayList();
        row.add(operation);
        row.add(result);
        data.add(row);

        String sql = "INSERT INTO history(operation,result) VALUES(?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, operation);
            pstmt.setString(2, result);
            pstmt.executeUpdate();

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

    public void delete() {
        data.clear();
        String sql = "DELETE from history";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buildDataFromDatabase(TableView tableView) {
        Connection c;
        try {
            c = connect();

            String SQL = "SELECT * from history";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            buildTableViewColumns(rs, tableView);
            addDataToTableView(rs, tableView);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void buildTableViewColumns(ResultSet rs, TableView tableView) throws Exception {
        for (int i = 0; i <= 1; i++) {
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            tableView.getColumns().addAll(col);
        }
    }

    public void addDataToTableView(ResultSet rs, TableView tableView) throws Exception {
        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
        tableView.setItems(data);
    }
}
