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
import java.util.Locale;
import javafx.scene.text.Font;

public class CalcUI extends Application {

    public Calculations calculations;
    static boolean operatorSelected;
    static boolean resultDisplayed;
    private TextField mainField;
    private TextField secondField;
    private GridPane buttons;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Locale.setDefault(Locale.US);
        calculations = new Calculations();
        buttons = createGrid();

        mainField = createTextField(new Font("SansSerif", 27));
        secondField = createTextField(new Font("SansSerif", 22));

        VBox displayFields = new VBox();
        displayFields.getChildren().addAll(secondField, mainField);

        createNumberButtons();
        createOperatorButtons();
        createOtherButtons();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setTop(displayFields);
        root.setCenter(buttons);

        Scene scene = new Scene(root, 465, 430);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private TextField createTextField(Font font) {
        TextField textField = new TextField();
        textField.setMaxWidth(465);
        textField.setEditable(false);
        textField.setStyle("-fx-text-box-border: transparent; -fx-background-color: -fx-control-inner-background;");
        textField.setFont(font);
        return textField;
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
