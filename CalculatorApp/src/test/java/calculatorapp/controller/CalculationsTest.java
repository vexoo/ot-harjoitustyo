package calculatorapp.controller;

import org.junit.Before;
import javafx.scene.control.TextField;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import de.saxsys.javafx.test.JfxRunner;
import calculatorapp.operator.*;
import calculatorapp.database.DatabaseConnection;
import java.io.File;
import org.junit.AfterClass;

@RunWith(JfxRunner.class)
public class CalculationsTest {

    Calculations calculations;
    Operator operator;
    DatabaseConnection connection;
    TextField mainField, secondField;
    static File file;

    @Before
    public void setUp() {
        connection = new DatabaseConnection("jdbc:sqlite:test.db", "test");
        file = new File("test.db");
        calculations = new Calculations(connection);
        connection.createNewDatabase();
        mainField = new TextField();
        secondField = new TextField();
    }

    @Test
    public void returnOperator() {
        calculations.setOperator(Operator.ADD);
        String expected = "+";
        String value = calculations.getOperator();
        assertEquals(expected, value);
    }

    @Test
    public void testNaturalNumberAddition() {
        setToNaturalNumbers();
        calculations.setOperator(Operator.ADD);
        int result = returnResult();
        assertEquals(60, result);
    }

    @Test
    public void testNaturalNumberSubtraction() {
        setToNaturalNumbers();
        calculations.setOperator(Operator.SUBTRACT);
        int result = returnResult();
        assertEquals(20, result);
    }

    @Test
    public void testNegativeNumberAddition() {
        setToNegativeNumbers();
        calculations.setOperator(Operator.ADD);
        int result = returnResult();
        assertEquals(-60, result);

        mainField.setText("20");
        result = returnResult();
        assertEquals(-20, result);

    }

    @Test
    public void testNaturalNumberMultiplication() {
        setToNaturalNumbers();
        calculations.setOperator(Operator.MULTIPLY);
        int result = returnResult();
        assertEquals(800, result);
    }

    @Test
    public void testNaturalNumberDivision() {
        setToNaturalNumbers();
        calculations.setOperator(Operator.DIVIDE);
        int result = returnResult();
        assertEquals(2, result);
    }

    @Test
    public void testPower() {
        secondField.setText("5");
        mainField.setText("5");
        calculations.setOperator(Operator.POWER);
        int result = returnResult();
        assertEquals(3125, result);
    }

    @Test
    public void divideByZero() {
        secondField.setText("40");
        mainField.setText("0");
        calculations.setOperator(Operator.DIVIDE);
        assertEquals("Cannot divide by zero", calculations.calculate(mainField, secondField));
    }

    @Test
    public void mainFieldZero() {
        secondField.setText("40");
        mainField.setText("0");
        calculations.setOperator(Operator.ADD);
        int result = returnResult();
        assertEquals(40, result);
    }

    @Test
    public void setNegative() {
        mainField.setText("3");
        calculations.setToPositiveOrNegative(mainField);
        int result = Integer.parseInt(mainField.getText());
        assertEquals(-3, result);
    }

    @Test
    public void setPositive() {
        mainField.setText("-3");
        calculations.setToPositiveOrNegative(mainField);
        int result = Integer.parseInt(mainField.getText());
        assertEquals(3, result);
    }

    @Test
    public void squareRootProperInput() {
        mainField.setText("4");
        int result = Integer.parseInt(calculations.squareRoot(mainField));
        assertEquals(2, result);
    }

    @Test
    public void squareRootNegativeInput() {
        mainField.setText("-4");
        assertEquals("Invalid input", calculations.squareRoot(mainField));
    }

    @Test
    public void setNegationForZero() {
        mainField.setText("0");
        calculations.setToPositiveOrNegative(mainField);
        int result = Integer.parseInt(mainField.getText());
        assertEquals(0, result);
    }

    public void setToNaturalNumbers() {
        secondField.setText("40");
        mainField.setText("20");
    }

    public void setToNegativeNumbers() {
        secondField.setText("-40");
        mainField.setText("-20");
    }

    public int returnResult() {
        return Integer.parseInt(calculations.calculate(mainField, secondField));
    }

    @AfterClass
    public static void tearDown() {
        file.delete();
    }
}
