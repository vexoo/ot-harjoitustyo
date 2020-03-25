package calculatorapp.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import javafx.scene.control.TextField;

public class Calculations {

    public Operator currentOperator;

    public String calculate(TextField mainField, TextField secondField) {
        //double firstVal = Double.parseDouble(aux.getText().replaceAll("([\\+\\-\\u00F7\\*\\s](?![0-9]))", "").replaceAll("\\,", "."));
        String maxValue = Double.toString(Double.MAX_VALUE);
        String secondString = secondField.getText().replaceAll("([\\+\\-\\u00F7\\*\\s\\^](?![0-9]))", "");
        String firstString = mainField.getText();

        if (!(isInDoubleRange(secondString) || isInDoubleRange(firstString))) {
            //BigDecimal result = BigDecimal.valueOf(currentOperator.equation(Double, 0.0));
        }
        double firstVal = Double.parseDouble(secondField.getText().replaceAll("([\\+\\-\\u00F7\\*\\s\\^](?![0-9]))", ""));
        //BigDecimal firstVal = new BigDecimal(secondField.getText().replaceAll("([\\+\\-\\u00F7\\*\\s\\^](?![0-9]))", ""));
        double secondVal = parseDouble(mainField);
        //BigDecimal secondVal = new BigDecimal(mainField.getText());

        if (secondVal == 0 && currentOperator == Operator.DIVIDE) {
            return "Cannot divide by zero";
            //return "0";
        }

        double result = currentOperator.applyOperator(firstVal, secondVal);
        return Strings.formatIntoCalcDisplay(result);
    }

    public String squareRoot(TextField mainField) {
        double val = parseDouble(mainField);
        return val < 0 ? "Invalid input" : Strings.formatIntoCalcDisplay(Math.sqrt(val));
    }

    public TextField setToPositiveOrNegative(TextField mainField) {
        double val = parseDouble(mainField);
        if (val > 0) {
            mainField.setText("-" + mainField.getText());
        } else if (val < 0) {
            mainField.setText(mainField.getText().replaceAll("\\-", ""));
        }
        return mainField;
    }

    public String getOperator() {
        return this.currentOperator.toString();
    }

    public void setOperator(Operator operator) {
        this.currentOperator = operator;
    }

    public double parseDouble(TextField textfield) {
        double val = Double.parseDouble(textfield.getText());
        return val;
    }

    public boolean isInDoubleRange(String number) {
        String doubleMaxValue = Double.toString(Double.MAX_VALUE);
        return number.length() < doubleMaxValue.length()
               || (number.length() == doubleMaxValue.length()
               && number.compareTo(doubleMaxValue) <= 0);
    }
}
