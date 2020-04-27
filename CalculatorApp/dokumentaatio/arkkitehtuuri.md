# Arkkitehtuurikuvaus

## Rakenne
<img src="https://user-images.githubusercontent.com/51514701/80289958-db9c3a80-874a-11ea-8301-bf8ad6dc8d4c.PNG" width="600">
CalcUI sisältää käyttoliittymän ja Calculations suurimman osan sovelluksen logiikasta. Calculations-luokan metodit kutsuvat tarvittaessa Operator- ja Strings-luokkia. DatabaseConnection huolehtii sovellukseen liittyvästä tietokantatoiminnoista



## Käyttöliittymä

Käyttöliittymä sisältää yhden näkymän johon kuuluu:
- kaksi TextFieldiä
- tarvittavat napit
  - numerot 0-9
  - plus-/miinus-/kerto-/jako-/potenssilasku
  - desimaali
  - yhteenlasku
  - vastaluku
  - neliöjuuri
  - syötteen nollaamiinen
- yksi [TableView](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html)
- nappi TableView ja tietokannan nollaukseen

Halutut syötteet syötetään nappeja painamalla. Sovellus ei tue syötteitä näppäimistön avulla

## Sovelluslogiikka

<img src="https://user-images.githubusercontent.com/51514701/79177496-9a7f5e80-7e0b-11ea-92c5-6feda7eca108.png" width="1000">
Sovellus käyttää laskemiseen pääosin [BigDecimaleja](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/BigDecimal.html), jotta hyvin suurten ja hyvin pienten lukujen laskeminen onnistuisi myös. BigDecimal (ja [BigInteger](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/BigInteger.html)) tallentavat luvun int[]-arrayssa, mikä tarkoittaa että suurin mahdollinen luku Javan omalla BigDecimal implementaatiolla on _2 ^ (Integer.MAX_VALUE)_. Tämän suurempia lukuja ei siis voi laskea.

Sovellus muuttaa Stringinä olevat luvut BigDecimaleiksi, laskee laskutoimituksen ja muuttaa luvun takaisin Stringiksi, joka syötetään sitten TextFieldiin.

Ulkoista kirjastoa [big-math](https://github.com/eobermuhlner/big-math#bigdecimalmath) on käytetty _BigDecimal ^ BigDecimal_ potenssilaskuun, sillä Javan oma BigDecimal potenssilaskuimplementaatio ei hyväksy desimaalisia eksponentteja


## Tietojen pysyväistallennus

Sovelluksella lasketut laskut tallennetaan _history.db_-nimiseen tiedostoon _CalculatorApp/assets/_ kansiossa. Tietokannassa on kaksi saraketta: operation ja result. Operation tallettaa laskutoimituksen muodossa _luku1 - operaattori - luku2 =_ ja result on nimensä mukaisesti laskutoimituksen tulos.

Sovellusta avatessa tietokannasta haetaan mahdollinen historia [ResultSettiin](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/ResultSet.html), joka käydään läpi rivi riviltä. Jokainen rivi tallennetaan [ObservableListiin](https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html) ja rivit sitten tallennetaan vielä ObservableList\<ObservableList\>:iin joka lopuksi lisätään TableViewiin jossa laskuhistoria sitten näkyy.
  
Uudet laskut tallennetaan kumpaankin ObservableListiin sekä tietokantaan. Sovellus siis katsoo tietokannan läpi vain kerran kun sovellus avataan ja uudet laskut lisätään TableViewiin ObservableListin kautta. Tämä on kätevää sillä TableView ottaa huomioon muutokset ObservableListissä automaattisesti.
