package calculatorapp.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import calculatorapp.controller.Calculations;
import calculatorapp.controller.Strings;
import calculatorapp.operator.Operator;
import calculatorapp.database.*;
import java.util.Locale;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CalcUI extends Application {

    public Calculations calculations;
    public Connect connect;
    static boolean operatorSelected;
    static boolean resultDisplayed;
    private TextField mainField;
    private TextField secondField;
    private GridPane buttons;
    public ObservableList<ObservableList> data = FXCollections.observableArrayList();
    private TableView tableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Locale.setDefault(Locale.US);
        calculations = new Calculations();
        connect = new Connect();
        buttons = createGrid();

        mainField = createTextField(new Font("SansSerif", 27));
        secondField = createTextField(new Font("SansSerif", 22));

        VBox displayFields = new VBox();
        displayFields.getChildren().addAll(secondField, mainField);

        createNumberButtons();
        createOperatorButtons();
        createOtherButtons();
        connect.createNewDatabase();

        tableView = new TableView();
//        data = FXCollections.observableArrayList();
        buildData();
        System.out.println(data);
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setTop(displayFields);
        root.setCenter(buttons);
        root.setBottom(tableView);
        System.out.println(data);

        Scene scene = new Scene(root, 465, 430);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(465);
        primaryStage.setResizable(true);
        primaryStage.show();
        System.out.println(data);
    }

    //CONNECTION DATABASE
    public void buildData() {
        Connection c;
//        data = FXCollections.observableArrayList();
        try {
            c = connect.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from history";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            for (int i = 1; i < 3; i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
            }

            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            //System.out.println(data);
            tableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void addData(String operation, String result) {
        ObservableList<String> row = FXCollections.observableArrayList();
//        System.out.println(operation);
//        System.out.println(result);
        row.add("55");
        row.add(operation);
        row.add(result);
//        System.out.println(row);
        data.add(row);
    }

    private TextField createTextField(Font font) {
        TextField textField = new TextField();
        textField.setMaxWidth(Double.POSITIVE_INFINITY);
        textField.setEditable(false);
        textField.setStyle("-fx-text-box-border: transparent; -fx-background-color: -fx-control-inner-background;");
        textField.setFont(font);
        return textField;
    }
    
    public String getData(){
        return data.toString();
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        return grid;
    }

    private void createOperatorButtons() {
        int buttonRow = 0;
        for (Operator op : Operator.values()) {
            String operator = op.toString();

            Button button = createButton(operator);
            button.setOnAction(e -> {
                if (secondField.getText().isEmpty()) {
                    secondField.setText(mainField.getText().isEmpty() ? "0" : Strings.getValue(mainField.getText()));
                    secondField.appendText(" " + operator);
                    calculations.setOperator(op);
                    resultDisplayed = true;
                    operatorSelected = true;
                } else if (operatorSelected) {
                    calculations.setOperator(op);
                    int end = secondField.getText().length();
                    secondField.replaceText(end - 1, end, operator);
                } else {
                    secondField.setText(calculations.calculate(mainField, secondField) + " " + operator);
                    mainField.clear();
                    calculations.setOperator(op);
                    resultDisplayed = true;
                    operatorSelected = true;
                }
            });
            buttons.add(button, 3, buttonRow++);
        }
        System.out.println(getData());
    }

    private void createNumberButtons() {
        int col, row;
        for (int n = 1; n < 10; n++) {
            row = (n - 1) / 3;
            col = (n - 1) % 3;
            buttons.add(createNumberButton(n), col, (2 - row) + 1);
        }
        buttons.add(createNumberButton(0), 1, 4);
    }

    private Button createNumberButton(int number) {
        String numberAsString = Integer.toString(number);
        Button button = createButton(numberAsString);
        button.setOnAction(e -> {
            if (resultDisplayed) {
                mainField.setText(numberAsString);
                resultDisplayed = false;
            } else if (mainField.getText().equals("0")) {
                mainField.setText(numberAsString);
            } else {
                mainField.appendText(numberAsString);
            }
            operatorSelected = false;
        });
        return button;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        button.setFont(new Font("Arial", 30));
        GridPane.setFillHeight(button, true);
        GridPane.setFillWidth(button, true);
        GridPane.setHgrow(button, Priority.ALWAYS);
        GridPane.setVgrow(button, Priority.ALWAYS);
        return button;
    }

    private void createOtherButtons() {
        Button decimal = createButton(".");
        buttons.add(decimal, 0, 4);
        decimal.setOnAction(e -> {
            if (mainField.getText().indexOf('.') == -1) {
                mainField.appendText(".");
            }
        });

        Button eq = createButton("=");
        buttons.add(eq, 2, 4);
        eq.setOnAction(e -> {
            if (!secondField.getText().isEmpty()) {
                mainField.setText(calculations.calculate(mainField, secondField));
                resultDisplayed = true;
                operatorSelected = false;
                secondField.clear();
            }
        });

        Button plusMinus = createButton("+/-");
        buttons.add(plusMinus, 0, 0);
        plusMinus.setOnAction(e -> {
            calculations.setToPositiveOrNegative(mainField);
        });

        Button clear = createButton("c");
        clear.setOnAction(e -> {
            mainField.clear();
            secondField.clear();
            operatorSelected = false;
        });
        buttons.add(clear, 2, 0);

        Button sqrt = createButton("\u221A");
        sqrt.setOnAction(e -> {
            mainField.setText(calculations.squareRoot(mainField));
            resultDisplayed = true;
            operatorSelected = false;
            secondField.clear();
        });
        buttons.add(sqrt, 1, 0);
    }
}
