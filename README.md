# Fakturisanje
###### Fakultet tehničkih nauka, Novi Sad
###### Metodologije brzog razvoja softvera 2017

## Za implementaciju je korišćeno:
* [Play 1.4.4](https://www.playframework.com/download) - Web framework
* [Microsoft SQL Server](https://www.microsoft.com/en-gb/sql-server/sql-server-downloads) - Baza podataka
* [Freemarker](https://freemarker.apache.org/index.html) - Template engine
* [MagicDraw](https://www.nomagic.com/products/magicdraw) - Profil i class dijagram

## Instalacija
Klonirati projekat na lokalnu mašinu:
```
git clone https://github.com/milenaL9/MBRS_2017.git
```

Pre pokretanja aplikacije je potrebno:
1. instalirati play, i
2. kreirati bazu.

### Kreiranje baze podataka
Nakon kreiranja baze, u fajlu application.conf (conf/application.conf) potrebno je izmeniti sledeće linije:
```
 db.default.url=jdbc:jtds:sqlserver://localhost/**nazivBaze**
 db.default.user=**username**
 db.default.pass=**password**
 ```

## Pokretanje aplikacije
Za pokretanje aplikacije koristiti komandu play run test, gde je **test** naziv projekta (potrebno je preći u folder gde se nalazi projekat):
```
play run test
```

zatim u pretraživaču uneti adresu:
```
http://localhost:9000/
```

Za zaustavljanje aplikacije u komandnoj liniji koristiti:
```
Ctrl + c
```
