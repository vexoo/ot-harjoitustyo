
# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on nimensä mukaisesti laskin JavaFX:llä toteutetulla käyttöliittymällä. 


## Suunnitellut toiminnallisuudet

### Perusversion toiminallisuudet

- plus-, miinus-, kerto- ja jakolasku
- potenssilasku 
- neliöjuuri
- kaikki käyttöön tarvittavat napit: 
  - syötteen poisto
  - nykyisen syötteen lasku
  - desimaali
  - +/- jolla vaihdetaan nykyinen syöte positiivisesta negatiiviseksi tai päinvastoin

</br>Perusversion kaikki toiminnallisuudet ovat toteutettu


### Jatkokehitysideat


- Laskin käyttää tällä hetkellä pelkästään double, liian suuret tai liian pienet luvut eivät toimi. Seuraava askel on korjata tämä
  - Hyvin suuret ja pienet luvut implementoitu. Double vaihdettu BigDecimaliksi.
  
- Laskuhistorian implementointi. Tallenna laskut tietokantaan ja lataa historia omaan ikkunaansa aina kun ohjelma avataan. Lisää myös nappi historian poistoon
  - Laskuhistoria implementoitu
