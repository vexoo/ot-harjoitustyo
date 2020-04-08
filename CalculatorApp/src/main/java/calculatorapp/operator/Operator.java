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
     * Laskee annetun laskutoimituksen.
     * @param firstValue ensimmäinen luku
     * @param secondValue toinen luku
     * @param operator nykyinen operaattori
     * @return summa/erotus/jne riippuen operaattorista
     */
    public BigDecimal applyOperator(BigDecimal firstValue, BigDecimal secondValue, Operator operator) {
        MathContext mathContext = new MathContext(20);
        switch (operator) {
            case ADD:
                firstValue = firstValue.add(secondValue, mathContext);
                break;
            case SUBTRACT:
                firstValue = firstValue.subtract(secondValue, mathContext);
                break;
            case DIVIDE:
                firstValue = firstValue.divide(secondValue, mathContext);
                break;
            case MULTIPLY:
                firstValue = firstValue.multiply(secondValue, mathContext);
                break;
            case POWER:
                firstValue = BigDecimalMath.pow(firstValue, secondValue, mathContext);
        }
        return firstValue;
    }

    @Override
    public String toString() {
        return operator;
    }
}
