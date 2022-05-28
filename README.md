# Vizsgafeladat

## Feladat: Logisztikai Alkalmazás létrehozása.
A feladat egy logisztikai alkalmazás adatelérési, üzleti logikai és REST rétegének részleges megvalósítása Spring Boot
alkalmazásában. A logisztikai cég szállítási terveket (TransportPlan) készít,amelyek azt foglalják össze, hogy egy adott
szállítmányt milyen szakaszokon (Section) keresztül, milyen mérföldköveket (Milestone) érintve terveznek kiszállítani.

### REST végpontok:
A végpontok
  - POST/api/addresses: Új címet hoz létre az adatbázisban. A kérés törzsében az Address entitás mezőinek
    megfelelő propertykkel rendelkező JSON objektumot vár.

  - GET /api/addresses: az összes cím lekérdezése.

  - GET /api/addresses/{id}: adott id-jűcím lekérdezése.

  - DELETE /api/addresses/{id}: adott id-jűcím törlése.

  - PUT /api/addresses/{id}: az adott id-heztartozó cím módosítása az adatbázisban. A kérés törzsében
    az Addressentitás mezőinek megfelelő propertykkel rendelkező JSON objektumot vár, az URL-ben lévő
    id definiálja,hogy melyik entitást módosítjuk.

  - POST /api/addresses/search : címek kereséséttámogatóvégpont, ország, város, irányítószám, utca alapján.
    A törzsben a keresési feltételeket reprezentáló address objektum érkezik. Amely propertyk alapján
    szűrni szeretnénk, azok vannak kitöltve (csak a fent említett 4 mező szerinti szűrést kell támogatni!).
    Az egyes propertykre vonatkozó feltételek ÉS kapcsolatban értendők. Város és utca esetébenk is-és
    nagybetű nem számít, valamint nem szükséges pontos egyezés, elegendő, ha a megadott stringgel kezdődik
    az adott mező. Országkód és az irányítószám esetén viszont pontos egyezés szükséges.

  - POST /api/transportPlans/{id}/delay: azid-veladott terv adott mérföldkövéhez regisztrál egy
    percekben megadott várható késést az alábbi szabályok szerint:
        - A mérföldkőazonosítójaésa késés hossza percekben a törzsben jönnek JSON formátumban
        - Növeld meg az adott mérföldkőnél tervezett időpontot a késés hosszával!
        - Ha a megadott mérföldkő kezdő mérföldkő a szakaszon belül, akkor annak a szakasznak a
          végmérföldkövénél tervezett időt is növeld meg a késés hosszával!
        - Ha a megadott mérföldkő egy szakaszon belüli végmérföldkő, akkor a következő szakasz kezdő
          mérföldkövének tervezett idejét növeld a késés hosszával!
        - A szállítási tervhez tárolt várható bevételt a késés miatt néhány százalékkal csökkenteni
          kell. Hogy hány perces késéshez hány százalékos csökkentés szükséges, az alkalmazás propertyk
          között legyen megadható. A határok, ahol nő a levonandó százelékos arány: 30perc, 60perc, 120perc

### Tesztek és Spring Security
Az utolsó végponthoz integrációs tesztek létrehozása.

Spring Security segítségével valósíts meg autentikációt és autorizációt az alábbimódon:
    •Egy/api/login POST végpont helyes felhasználónév-jelszó páros esetén1 0 percigérvényesJWT tokent ad vissza, amely
    tartalmazza a usernevet és a user jogosultságait.
    •Csak AddressManager jogosultság birtokában érhetők el a cím létrehozó, módosító és törlő végpontok.
    •Csak TransportManager jogosultság birtokában érhető el a késés regisztráló végpont.
    •Memóriában tárolt felhasználókk.