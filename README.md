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

## Termin 5 (20.11.2017)
* Benutzen von eigenen Akkus, die ohne Laden den ganzen Vormittag halten
* Brückenverfolgung mit drehbarem Ultraschall funktioniert nicht
  * Entweder zu langsam / Zu viele Messungen oder es misst sowieso nur eine Richtung
  * Entscheidung, den Sensor direkt vorne anzubringen
  * Dazu **Umbau des Roboters**, der noch nicht fertiggestellt ist
* Motorsteuerung überarbeitet
  * Motoren haben manchmal undefinierte Dinge getan
  * Ansteuerung hat mehr auf Probieren beruht
  * Verwendung der mitgelieferten Pilot Klasse, die sich um die Regelung kümmert
* Linienverfolgung verbessert
  * Drehrichtung wird gespeichert, um schnelleres Verfolgen zu ermöglichen
* Labyrinth begonnen
  * Erkennung von Abzweigungen
  * Algorithmus momentan zufällig
  * Probleme mit der zuverlässigen Rotation, da die neuen Motorklassen noch nicht integriert sind
* Veränderter Parcours

<img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/parcours-3.jpg" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/parcours-4.jpg" height="150">

## Termin 6 (27.11.2017)
* Eigene Akkus halten nicht den ganzen Vormittag, weil sie nicht geladen wurden.
* Roboter wird nochmal etwas umgebaut
  * Weniger breite Ketten, damit die Wellen nicht so durchhängen
  * Fixierung der Sensoren
  * Kabelmanagement
* Linie folgen fertig
  * Inklusive Umfahren eines Hindernisses
* Labyrinth fertig
  * Idee: Folge immer der rechten Kante
  * Probleme traten auf, weil auf der Platte manchmal blau erkannt wird
    * Stoppe nur, wenn 50 Samples nacheinander blau sind
* Motorsteuerung verbessert
  * Motoren fahren zuverlässig und benutzen Gyroskop für die Winkelbestimmung

<img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/2017-11-27-1.jpg" height="150"> <img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/2017-11-27-1.jpg" height="150">

## Übungsblatt
* Simulation der Labyrinth-Verfolgung am PC 

<img src="https://github.com/nicky992/LegoMindstorms08/raw/master/docs/simulation.png" height="150">

## Termin 7 (04.12.2017)
* Experimente mit dem Suchen von Objekten
  * Momentan zufälliges Abfahren des Bereichs
* Implementierung der Brückenüberquerung
  * Dazu erneuter Umbau des Roboters, Ultraschallsensor ist jetzt seitlich angebracht
* Fertigstellung der Objektumfahrung bei der Linienverfolgung

## Termin 8 (11.12.2017)
* Verbesserung der Brückenüberquerung
* Verbesserung der Motorsteuerung (Angabe von Distanzen)
* Roboter spielt Jeopardy, solange die Sensoren initialisiert werden
* Verbesserung der Linienverfolgung
* Akkus laufen aus

## Termin 9 (18.12.2017)
* Verbesserung des Suchens von Objekten
* Modus zum automatischen Ausführen von mehreren Zuständen (ohne Hauptmenü)
* Roboter wendet automatisch, um verschiedene Zustände zu verbinden
