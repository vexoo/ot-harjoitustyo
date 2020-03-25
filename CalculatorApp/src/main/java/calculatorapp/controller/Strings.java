package calculatorapp.controller;

import java.util.Locale;

public class Strings {

    public static String formatIntoCalcDisplay(double value) {
        Locale.setDefault(Locale.US);
        return value % 1 == 0 ? Integer.toString((int) value) : removeDecimalZeros(String.format("%.6f", value));
    }

    public static String removeDecimalZeros(String s) {
        return s.replaceFirst("[\\.0\\,0]*$|([\\.\\,]\\d*?)0+$", "$1");
    }

    public static String getValue(String val) {
        return formatIntoCalcDisplay(Double.parseDouble(val));
    }
}
