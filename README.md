# Ohjelmistotekniikka, harjoitustyö

## CalculatorApp

JavaFX:llä toteutetulla käyttöliittymällä varustettu laskin. </br>Windows10 laskimen kaksi eri tekstiriviä toimi inspiraationa laskimen näyttöön

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/vexoo/ot-harjoitustyo/blob/master/CalculatorApp/dokumentaatio/arkkitehtuuri.md)

## Releaset

[Viikko 5](https://github.com/vexoo/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

Osoita komentorivi CalculatorApp kansioon.<br/>
#### Avaa ohjelma:

```
mvn compile exec:java -Dexec.mainClass=calculatorapp.ui.CalcUI
```

#### Generoi suoritettava jar:

```
mvn package
```
Sijainti - _target/CalculatorApp-1.0-SNAPSHOT.jar_

#### Suorita testit:

```
mvn test
```
Sijainti - _target/site/jacoco/index.html_


#### Testikattavuusraportti:
```
mvn jacoco:report
```
Sijainti - _target/site/jacoco/index.html_


#### Checkstyle:
```
mvn jxr:jxr checkstyle:checkstyle
```
Sijainti - _target/site/checkstyle.html_


#### JavaDoc:

```
mvn javadoc:javadoc
```

Sijainti -  _target/site/apidocs/index.html_
