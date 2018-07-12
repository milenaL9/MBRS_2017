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
Potrebno je podesiti Java Build Path -> Libraries -> play 1.4.4. 

Pre pokretanja aplikacije potrebno je izvrsiti komandu play dependencies test.
Za pokretanje aplikacije koristiti komandu play run test, gde je **test** naziv projekta (potrebno je preći u folder gde se nalazi projekat):

```
play dependencies test
```
```
play run test
```

zatim u pretraživaču uneti:
```
http://localhost:9000/
```

ako se zeli pristupiti konkretnoj stranici, npr. stranici preduzeca, ukucati sledece:
```
http://localhost:9000/preduzeca/show
```

Za zaustavljanje aplikacije koristiti:
```
Ctrl + c
```


## Ubacivanje plugin-a u MagicDraw
```
1. Otvoriti projekat PluginDevelopment
2. Pronaci fajl build.properties i promeniti putanju do MagicDraw-a
3. Desnim klikom na naziv projekta, odabrati Properties, zatim Java Build Path -> Libraries. Obrisati sve sto se crveni i dodati sve biblioteke iz foldera gde je instaliran MagicDraw (plugins -> libs)
4. Otvoriti build.xml i prevuci ga u Ant konzolu (Windows->Others->Ant)
5. Odraditi build, pa deploy 
6. Automatski se napravi .jar koji je smesten u MagicDraw -> plugins -> myplugin
7. Restartovati MagicDraw i trebalo bi da se pojavi obavestenje da je plugin inicijalizovan
8. Pojavi se stavka Generate -> Code Generation (izgenerisane klase se nalaze na C:/temp)

```
