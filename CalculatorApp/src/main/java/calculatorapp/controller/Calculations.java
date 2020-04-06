package calculatorapp.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import ch.obermuhlner.math.big.BigDecimalMath;
import javafx.scene.control.TextField;
import calculatorapp.operator.*;
import java.math.MathContext;

public class Calculations {

    private Operator currentOperator;

    /**
     * Ottaa syötetyt luvut kummastakin TextFieldistä ja palauttaa
     * laskutoimituksen vastauksen mainFieldiin.
     *
     * @param mainField alemman TextFieldin eli toinen luku
     * @param secondField ylemmän TextFieldin eli ensimmäinen luku. Sisältää
     * myös nykyisen operaattorin, jonka metodi poistaa regex avulla ennen laskutoimitusta
     * @return palauttaa vastauksen String-muodossa joka tulee näkyville
     * mainFieldissä. Muotoilun hoitaa Strings-luokka
     */
    public String calculate(TextField mainField, TextField secondField) {
        String fVal = secondField.getText().replaceAll("([\\+\\-\\u00F7\\*\\s\\^](?![0-9]))", "");
        String sVal = mainField.getText();

        if (sVal.equals("0") && currentOperator == Operator.DIVIDE) {
            return "Cannot divide by zero";
        }

        BigDecimal firstVal = BigDecimalMath.toBigDecimal(fVal);
        BigDecimal secondVal = BigDecimalMath.toBigDecimal(sVal);
        BigDecimal result = currentOperator.applyOperator(firstVal, secondVal, currentOperator);
        return Strings.formatIntoCalcDisplay(result);
    }


    /**
     * Laskee mainFieldin luvun neliöjuuren. Jos ylemmässä textFieldissä on
     * luku, palauttaa metodi laskutoimituksen jossa toinen luku on mainFieldin
     * neliöjuuri.
     *
     * @param mainField Alempi textField
     * @return Palauttaa luvun neliöjuuren jos mahdollista, eli luku on suurempi
     * kuin 0
     */
    public String squareRoot(TextField mainField) {
        MathContext mathContext = new MathContext(20);
        BigDecimal val = BigDecimalMath.toBigDecimal(mainField.getText());
        return val.signum() < 0 ? "Invalid input" : Strings.formatIntoCalcDisplay(BigDecimalMath.sqrt(val, mathContext));
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
