package calculatorapp.controller;

import calculatorapp.database.DatabaseConnection;
import java.math.BigDecimal;
import ch.obermuhlner.math.big.BigDecimalMath;
import javafx.scene.control.TextField;
import calculatorapp.operator.Operator;
import java.math.MathContext;

public class Calculations {

    private Operator currentOperator;
    private final DatabaseConnection connect;

    public Calculations(DatabaseConnection connection) {
        this.connect = connection;
    }

    /**
     * Ottaa syötetyt luvut kummastakin TextFieldistä ja palauttaa
     * laskutoimituksen vastauksen. CalcUI-luokka syöttää vastauksen
     * mainFieldiin.
     *
     * @param mainField alemman TextFieldin eli toinen luku
     * @param secondField ylemmän TextFieldin eli ensimmäinen luku. Sisältää
     * myös nykyisen operaattorin, jonka metodi poistaa regex avulla ennen
     * laskutoimitusta
     * @return palauttaa vastauksen String-muodossa joka tulee näkyville
     * mainFieldissä. Muotoilun hoitaa Strings-luokka
     */
    public String calculate(TextField mainField, TextField secondField) {
        String fVal = secondField.getText().replaceAll("([\\+\\-\\u00F7\\*\\s\\^](?![0-9]))", "");
        String sVal = Strings.getValue(mainField.getText());

        if (sVal.equals("0") && currentOperator == Operator.DIVIDE) {
            return "Cannot divide by zero";
        }

        BigDecimal firstVal, secondVal, result;
        try {
            firstVal = BigDecimalMath.toBigDecimal(fVal);
            secondVal = BigDecimalMath.toBigDecimal(sVal);
            result = currentOperator.applyOperator(firstVal, secondVal, currentOperator);
        } catch (ArithmeticException ex) {
            return "Number overflow";
        }
        String resultAsString = Strings.formatIntoCalcDisplay(result);
        insertIntoDB(secondField.getText(), Strings.formatIntoCalcDisplay(secondVal), resultAsString);
        return resultAsString;
    }

    /**
     * Muotoilee laskutoimituksen ja kutsuu DatabaseConnection-luokan insert
     * metodia lisäämään laskutoimituksen ja tuloksen tietokantaan.
     *
     */
    private void insertIntoDB(String firstVal, String secondVal, String result) {
        String operation = firstVal + " " + secondVal + " = ";
        connect.insert(operation, result);
    }

    /**
     * Laskee mainFieldin luvun neliöjuuren. Lisää myös neliöjuuren laskutoimituksen tietokantaan.
     *
     * @param mainField Alempi textField
     * @return Palauttaa luvun neliöjuuren jos mahdollista, eli luku on suurempi
     * kuin 0
     */
    public String squareRoot(TextField mainField) {
        MathContext mathContext = new MathContext(10);
        BigDecimal val = BigDecimalMath.toBigDecimal(Strings.getValue(mainField.getText()));
        if (val.equals(BigDecimal.ZERO)) {
            return "0";
        } else {
            String operation, result;
            if (val.signum() < 0) {
                return "Invalid input";
            } else {
                operation = "\u221A" + val.toString() + " = ";
                result = Strings.formatIntoCalcDisplay(BigDecimalMath.sqrt(val, mathContext));
                connect.insert(operation, result);
                return result;
            }
        }
    }

    /**
     * Vaihtaa textFieldissä olevan luvun sen vastalukuun.
     *
     * @param mainField Alempi textField
     * @return palauttaa mainFieldin vastaluvuksi vaihtamisen jälkeen
     */
    public TextField setToPositiveOrNegative(TextField mainField) {
        double val = Double.parseDouble(mainField.getText());
        if (val > 0) {
            mainField.setText("-" + mainField.getText());
        } else if (val < 0) {
            mainField.setText(mainField.getText().replaceAll("\\-", ""));
        }
        return mainField;
    }

    /**
     * Metodi palauttaa nykyisen operaattorin.
     *
     * @return nykyinen operaattori
     */
    public String getOperator() {
        return this.currentOperator.toString();
    }

    /**
     * Metodi asettaa nykyisen operaattorin annettuun Operator-arvoon.
     *
     * @param operator yksi Operator-enumin arvoista
     */
    public void setOperator(Operator operator) {
        this.currentOperator = operator;
    }
}
