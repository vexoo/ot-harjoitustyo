package calculatorapp.controller;

import ch.obermuhlner.math.big.BigDecimalMath;
import java.math.BigDecimal;
import java.util.Locale;

public class Strings {

    /**
     * Muuttaa laskutoimituksen BigDecimal-arvon String-muotoon.
     * @param value Calculations-luokassa suoritetun laskutoimituksen arvo
     * @return Onko numeromäärä muuttujassa value vähintään 20? Jos on, vaihda kymmenpotenssimuotoon.
     * Jos ei, onko valuessa desimaaleja? Jos ei, palauta BigInteger String-muodossa, jotta tyhjä desimaali (.0) poistetaan.
     * Jos on, poista mahdolliset nolladesimaalit ja palauta.
     */
    public static String formatIntoCalcDisplay(BigDecimal value) {
        Locale.setDefault(Locale.US);
        return value.precision() >= 20
                ? value.stripTrailingZeros().toString() : value.stripTrailingZeros().scale() <= 0
                ? value.toBigInteger().toString() : removeDecimalZeroes(String.format("%.6f", value));
    }

    /**
     * formatIntoCalcDisplay:n apumetodi. Poistaa mahdolliset nolladesimaalit regex avulla.
     * @param s Luku, josta nollat halutaan poistaa, jos niitä on
     * @return Palauttaa saman luvun nollien poiston jälkeen. Esim 1000.2100 -> 1000.21
     */
    private static String removeDecimalZeroes(String s) {
        return s.replaceFirst("[\\.0\\,0]*$|([\\.\\,]\\d*?)0+$", "$1");
    }

    /**
     * Apumetodi CalcUI-luokalle tapaukseen, jossa arvo pitää siirtää yhdestä textFieldistä toiseen.
     * @param val textField.getText() arvo
     * @return palautetaan formatIntoCalcDisplay:n avulla muokattuna
     */
    public static String getValue(String val) {
        BigDecimal value = BigDecimalMath.toBigDecimal(val);
        return formatIntoCalcDisplay(value);
    }
}
