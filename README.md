# Lego Mindstorms 08

*Lego Mindstorms Basispraktikum* am KIT im WS 17/18. 

> Ziel dieses Praktikums in 4er-Gruppen ist der Entwurf und die Programmierung eines Lego-Mindstorms-Roboters zur Durchquerung eines vielseitigen Hindernisparcours.

## Parcours

* Labyrinth (Linie verfolgen mit Abzweigungen)
* Linie verfolgen (mit Lücken)
* Brücke

<img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/parcours-1.jpg" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/parcours-2.jpg" height="150">

## Termin 1 (16.10.2017)

* Vorstellung der Ziele des Praktikums
* Gruppeneinteilung
* Einrichten der Laptops
* Einigung auf Kettenfahrzeug
* Überlegung, einen rotierenden Ultraschallsensor zu benutzen
* Bau des Fahrgestells, aber keine Zeit mehr zum Testen der Fahreigenschaft

<img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/2017-10-16.jpg" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/model-day1.png" height="150">

## Übungsblatt

* Planen der Anbringung weiterer Sensoren an das Fahrgestell

<img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/model-draft-1.png" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/model-draft-2.png" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/model-draft-3.png" height="150">

## Termin 2 (23.10.2017)

* Besprechung des LDD Prototyp
  * Ultraschall-Sensor wird stattdessen vorne angebracht, da sonst der Roboter nur sich selbst sieht
  * Gyroskop wird entfernt und durch einen Touch-Sensor ersetzt, da das Gyroskop nur schwer die aktuelle Neigung berechnen kann (statt der Beschleunigung)
  * Brick wird besser fixiert
* Fertigstellen des Aufbaus und erstes Testen der Fahreigenschaft auf dem Parcours
* Einrichten des Git-Repos und der IDE
* Entwicklung von Klassen für Motorsteuerung und Ultraschall-Sensor (paralell zueinander)
  * Sensor bewegt sich in einem Thread und steuert Messpositionen an
    * Statt Messung momentan noch Piepston
  * Roboter kann sich in bestimmten Winkeln drehen
    * Dies ist aber abhängig von Motorgeschwindigkeit und Drehrichtung: Ungenauigkeit der Motoren
* Absprache über die Softwarearchitektur

## Termin 3 (06.11.2017)
* Linie verfolgen nahezu fertig
* Motor-Regelung
* Ultraschall-Messung fertig
* Beginn der Verfolgung der Brücke

<img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/2017-11-06-1.jpg" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/2017-11-06-2.jpg" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/2017-11-06-3.jpg" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/2017-11-06-4.jpg" height="150">

## Termin 4 (13.11.2017)
* Weitere Verbesserungen der Linienfolgung
* Motorensteuerung überarbeitet
* Realisierung, dass Ultraschall gerade auftreffen muss
  * Sensor zeigt jetzt gerade nach unten
* Verschiedene Versuche zu Brückenverfolgung
