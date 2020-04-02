# Ohjelmistotekniikka, harjoitustyö

## CalculatorApp

JavaFX:llä toteutetulla käyttöliittymällä varustettu laskin. </br>Windows10 laskimen kaksi eri tekstiriviä toimi inspiraationa laskimen näyttöön

### Dokumentaatio

[Vaatimusmäärittely](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/arkkitehtuuri.md)

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
