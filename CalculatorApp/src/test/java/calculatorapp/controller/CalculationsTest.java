/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorapp.controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import javafx.scene.control.TextField;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import calculatorapp.ui.CalcUI;
import calculatorapp.Main;
import de.saxsys.javafx.test.JfxRunner;

/**
 *
 * @author Jani
 */
@RunWith(JfxRunner.class)
public class CalculationsTest {

    Calculations calculations;
    Operator operator;
    TextField mainField, secondField;

    @Before
    public void setUp() {
        calculations = new Calculations();
        mainField = new TextField();
        secondField = new TextField();
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

    @After
    public void tearDown() {
    }
}
