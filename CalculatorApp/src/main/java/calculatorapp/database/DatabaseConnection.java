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
import org.apache.commons.dbutils.DbUtils;

public class DatabaseConnection {

    private ObservableList<ObservableList> data;
    private static String url;
    private static String dbName;
    private static Connection conn = null;

    public DatabaseConnection(String url, String dbName) {
        data = FXCollections.observableArrayList();
        this.url = url;
        this.dbName = dbName;
    }

    /**
     * Luo tietokannan .db tiedoston jos sitä ei ole olemassa. Sijainti on
     * CalculatorApp/-kansio tai sama sijainti kuin ohjelman avaava .jar.
     */
    public static void createNewDatabase() {

        Statement stmt = null;
        String sql = "CREATE TABLE IF NOT EXISTS " + dbName + " (\n"
                + "	operation text NOT NULL,\n"
                + "	result text NOT NULL\n"
                + ");";
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * Lisää viimeisimmän laskutoimituksen tietokantaan ja
     * observableArrayListiin, jonka tableView näyttää.
     *
     * @param operation laskutoimitus muodossa "ensimmäinen arvo - operaattori -
     * toinen arvo - = "
     * @param result laskutoimituksen tulos
     */
    public void insert(String operation, String result) {
        ObservableList<String> row = FXCollections.observableArrayList();
        row.add(operation);
        row.add(result);
        data.add(row);

        String sql = "INSERT INTO " + dbName + "(operation,result) VALUES(?,?)";
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(url);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, operation);
            pstmt.setString(2, result);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * Tyhjentää tietokannan sekä data-ObservableArrayListin.
     */
    public void delete() {
        data.clear();
        String sql = "DELETE from " + dbName;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(url);
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * Täyttää TableView-taulukon vanhalla laskuhistorialla tietokannasta, jos
     * tietokanta on olemassa ja ei ole tyhjä.
     *
     * @param tableView Käyttöliittymän alapuolella oleva taulukko
     */
    public void buildDataFromDatabase(TableView tableView) {
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url);
            String sql = "SELECT * from " + dbName;
            rs = conn.createStatement().executeQuery(sql);
            buildTableViewColumns(rs, tableView);
            addDataToTableView(rs, tableView);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * buildDataFromDatabase apumetodi. Rakentaa TableView:n sarakkeet.
     *
     * @param rs Tietokannan tieto
     * @param tableView Käyttöliittymän alapuolella oleva taulukko
     */
    private void buildTableViewColumns(ResultSet rs, TableView tableView) throws Exception {
        for (int i = 0; i <= 1; i++) {
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            if (i == 0) {
                col.setStyle("-fx-alignment: CENTER-RIGHT;");
            }
            col.setSortable(false);
            tableView.getColumns().addAll(col);
        }
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * buildDataFromDatabase apumetodi. Lisää tietokannan laskuhistorian rivi
     * riviltä row-ObservableListiin, ja lopulta lisää row:n
     * data-ObservableListiin, jonka sisällön käyttöliittymän tableView näyttää.
     *
     * @param rs Tietokannan tieto
     * @param tableView Käyttöliittymän alapuolella oleva taulukko
     */
    private void addDataToTableView(ResultSet rs, TableView tableView) throws Exception {
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
