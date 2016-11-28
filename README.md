# TP3-IA
Project in IA of an independent character in a map with infinite levels

A bien comprendre:
 - Character est un Thread
 - Character a une AdventureMapCharacter qui est sa memoire des cases parcourues
 - AdventureMap est la vraie carte ou se deplace le Character, elle possede une liste de Cases
 - Une Case a enormement d'attributs...
 - CaseView observe un CaseMap qui lui est lie ainsi que le personnage : elle pourra ainsi se mettre a jour en fonction
 - Le Character n'a pas acces a la VRAIE carte, mais a AdventureMapCharacter
 - Les cases du character et de la map ont leur propre systeme de coordonnees
