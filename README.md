# Ohjelmistotekniikka, harjoitustyö

## CalculatorApp

### Dokumentaatio

[Vaatimusmäärittely](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/vaatimusmaarittely.md)

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

Testikattavuusraportti (target/site/jacoco/index.html):

```
mvn jacoco:report
```

Checkstyle (target/site/checkstyle.html):

```
mvn jxr:jxr checkstyle:checkstyle
```
