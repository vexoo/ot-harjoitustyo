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
    public void testPositiveNumbers() {
        mainField.setText("50");
        secondField.setText("60");
        
        calculations.setOperator(Operator.ADD);
        int result = Integer.parseInt(calculations.calculate(mainField, secondField));
        assertEquals(110, result);
        
        calculations.setOperator(Operator.SUBTRACT);
        result = Integer.parseInt(calculations.calculate(mainField, secondField));
        assertEquals(10, result);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
