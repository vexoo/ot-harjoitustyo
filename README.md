# Ohjelmistotekniikka, harjoitustyö

## CalculatorApp

### Dokumentaatio

[Alustava vaatimusmäärittely](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/tyoaikakirjanpito.md)

### Komentorivitoiminnot

Osoita komentorivi CalculatorApp kansioon.<br/>
Avaa ohjelma:

```
mvn compile exec:java -Dexec.mainClass=calculatorapp.ui.CalcUI
```

Suorita testit:

```
mvn test
```

Testikattavuusraportti:

```
mvn jacoco:report
```

Checkstyle:

```
mvn jxr:jxr checkstyle:checkstyle
```
Checkstyle raportti: target/site/checkstyle.html
