# retroFace - Java Spring Boot, Thymeleaf, H2 / PostgreSQL


Helsingin Yliopiston Java-Palvelinohjelmointi - MOOCin lopputyö 
"vanhan ajan facebook"  


localhost:8080
tai

https://vast-springs-53157.herokuapp.com/ 

-Herokun versioon voi luoda uusia käyttäjiä, tai kirjautua suoraan Erkkinä (test/test)

- tämä Githubin versio toimii paikallisesti.


retroFace - manuaali:



LOG IN 

- jo kirjautunut käyttäjä sisään

SIGN UP

- luo uusi tili. Kaikki kentät vaaditaan, ja uniikki profiili-merkkijono



LOG OUT

- kirjaudu ulos



MY WALL - näkymä

	

	- omalla 'seinällä' profiilikuva,ystäväluettelo, ystäväpyyntöluettelo, viestikenttä,

	viesteihin liittyvät kommentit

	  

	MY PHOTOS

		- kirjautuneen käyttäjän omat kuvat

		- lisää kuvia 1MB kokoon asti

		- valitse profiilikuva

		- kommentoi kuvia

		- tykkää kuvista

	FIND

		- etsi käyttäjää nimellä

	ALL USERS

		- listaa kaikki käyttäjät ja heidän suhteensa kirjautuneeseen käyttäjään

	  

	- muiden käyttäjien nimissä linkki heidän 'seinillensä',

	joissa näytetään kuten edellä, paitsi ei ystäväpyyntöluetteloa, eikä

	kuvien lisäystoimintoa. Tässä navigointi: 'user's WALL' ja 'user's PHOTOS'.

	MY WALL palauttaa kirjautuneen käyttäjän omalle seinälle.

	- käyttäjiä voi hakea myös osoiteriviltä:  '/users/profile'

	- samoin valokuvia 'users/profile/photos'

	

- ensimmäisen käynnistyksen yhteydessä on luotu jo pari käyttäjää 


 
