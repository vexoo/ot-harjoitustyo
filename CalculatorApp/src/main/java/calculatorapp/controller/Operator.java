package calculatorapp.controller;

import java.util.function.DoubleBinaryOperator;

public enum Operator {
    POWER("^", (x, y) -> Math.pow(x, y)),
    DIVIDE("\u00F7", (x, y) -> x / y),
    MULTIPLY("*", (x, y) -> x * y),
    SUBTRACT("-", (x, y) -> x - y),
    ADD("+", (x, y) -> x + y);

    private final String operator;
    private final DoubleBinaryOperator equation;

    Operator(String symbol, DoubleBinaryOperator equation) {
        this.operator = symbol;
        this.equation = equation;
    }

    public double applyOperator(double firstValue, double secondValue) {
        return equation.applyAsDouble(firstValue, secondValue);
    }

    @Override
    public String toString() {
        return operator;
    }
}
