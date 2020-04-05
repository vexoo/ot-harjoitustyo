package calculatorapp.operator;

import ch.obermuhlner.math.big.BigDecimalMath;
import java.math.BigDecimal;
import java.math.MathContext;

public enum Operator {
    POWER("^"),
    DIVIDE("\u00F7"),
    MULTIPLY("*"),
    SUBTRACT("-"),
    ADD("+");

    private final String operator;

    Operator(String symbol) {
        this.operator = symbol;
    }

    /**
     * Metodi yhteenlaskuun.
     *
     * @param firstValue Ensimmäinen luku
     * @param secondValue Toinen luku
     * @return summa
     */
    public BigDecimal addition(String firstValue, String secondValue) {
        return toBDecimal(firstValue).add(toBDecimal(secondValue));
    }

    /**
     * Metodi vähennyslaskuun.
     *
     * @param firstValue Ensimmäinen luku
     * @param secondValue Toinen luku
     * @return erotus
     */
    public BigDecimal subtraction(String firstValue, String secondValue) {
        return toBDecimal(firstValue).subtract(toBDecimal(secondValue));
    }

    /**
     * Metodi kertolaskuun.
     *
     * @param firstValue Ensimmäinen luku
     * @param secondValue Toinen luku
     * @return tulo
     */
    public BigDecimal multiplication(String firstValue, String secondValue) {
        return toBDecimal(firstValue).multiply(toBDecimal(secondValue));
    }

    /**
     * Metodi jakolaskuun.
     *
     * @param firstValue Ensimmäinen luku
     * @param secondValue Toinen luku
     * @return osamäärä
     */
    public BigDecimal division(String firstValue, String secondValue) {
        return toBDecimal(firstValue).divide(toBDecimal(secondValue));
    }

    /**
     * Metodi potenssilaskuun.
     *
     * @param firstValue Ensimmäinen luku
     * @param secondValue Toinen luku
     * @return vastaus
     */
    public BigDecimal power(String firstValue, String secondValue) {
        MathContext mathContext = new MathContext(20);
        return BigDecimalMath.pow(
                toBDecimal(firstValue),
                toBDecimal(secondValue), mathContext);
    }

    /**
     * Apumetodi vähentämään copy-pastea. Muuttaa String-muuttujan
     * BigDecimaliksi.
     *
     * @param string Luku joka on String muodossa
     * @return Luku muutettu BigDecimaliksi
     */
    private BigDecimal toBDecimal(String string) {
        return BigDecimalMath.toBigDecimal(string);
    }

    @Override
    public String toString() {
        return operator;
    }
}
