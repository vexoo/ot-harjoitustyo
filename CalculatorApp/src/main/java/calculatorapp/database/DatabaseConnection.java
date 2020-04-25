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
    private DbUtils dbUtils;

    public DatabaseConnection() {
        data = FXCollections.observableArrayList();
        dbUtils = new DbUtils();
    }

    /**
     * Luo tietokannan .db tiedoston jos sitä ei ole olemassa. Sijainti on
     * CalculatorApp-kansio.
     */
    public static void createNewDatabase() {

        Connection conn = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:history.db";
        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
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

        String sql = "INSERT INTO history(operation,result) VALUES(?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, operation);
            pstmt.setString(2, result);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tietokannan yhteyden tarkistusmetodi.
     * @return palauttaa yhteyden, tai null jos ei yhteyttä
     */
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

    /**
     * Tyhjentää tietokannan sekä data-ObservableArrayListin.
     */
    public void delete() {
        data.clear();
        String sql = "DELETE from history";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Täyttää TableView-taulukon vanhalla laskuhistorialla tietokannasta, jos
     * tietokanta on olemassa ja ei ole tyhjä.
     *
     * @param tableView Käyttöliittymän alapuolella oleva taulukko
     */
    public void buildDataFromDatabase(TableView tableView) {
        Connection c;
        ResultSet rs = null;
        try {
            c = connect();
            String sql = "SELECT * from history";
            rs = c.createStatement().executeQuery(sql);
            buildTableViewColumns(rs, tableView);
            addDataToTableView(rs, tableView);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        } finally {
            DbUtils.closeQuietly(rs);
        }
    }

    /**
     * buildDataFromDatabase apumetodi. Rakentaa TableView:n kolumnit.
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
            tableView.getColumns().addAll(col);
        }
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
