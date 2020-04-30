package calculatorapp.controller;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import de.saxsys.javafx.test.JfxRunner;
import java.math.BigDecimal;


@RunWith(JfxRunner.class)
public class StringsTest {

    Strings strings;

    @Before
    public void setUp() {
        strings = new Strings();
    }

    @Test
    public void scientificNotation() {
        BigDecimal value = new BigDecimal("10000000000");
        String result = strings.formatIntoCalcDisplay(value);
        assertEquals("1E+10", result);
    }
    
    @Test
    public void noDecimals(){
        BigDecimal value = new BigDecimal("135.0");
        String result = strings.formatIntoCalcDisplay(value);
        assertEquals("135", result);
    }
    
    @Test
    public void removePointlessDecimals(){
        BigDecimal value = new BigDecimal("135.2000");
        String result = strings.formatIntoCalcDisplay(value);
        assertEquals("135.2", result);
    }
    
    @Test
    public void getValueTest(){
        String value = "135.20";
        assertEquals("135.2", strings.getValue(value));
    }

}
