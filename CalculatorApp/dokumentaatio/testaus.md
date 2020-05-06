# Testausdokumentti


<img src="https://user-images.githubusercontent.com/51514701/81182467-7c45f200-8fb6-11ea-9a4c-92e30aa0afa9.png" width="1100">

### Sovelluslogiikka

Sovelluslogiikan (eli Calculations-luokka) testaus sisältää suurimmaksi osaksi testejä varmistamaan että laskutoimitukset palauttavata oikeita lukuja. Testattu on myös kaikki mahdolliset virhetilanteet - esim jakolasku nollalla.


### Tiedon tallennus

Testeissä luodaan testeihin oma tietokanta joka poistetaan testien jälkeen. Luokan muutama virheilmoitus on jäänyt testaamatta, sillä niitä ei ohjelman tänhetkisessä käytössä normaalisti tule vastaan.
