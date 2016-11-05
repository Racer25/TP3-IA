# TP3-IA
Project in IA of an independent character in a map with infinite levels

A bien comprendre:
 - Character est un Thread
 - Character a une AdventureMapCharacter qui est sa mémoire des cases parcourues
 - AdventureMap est la vrai carte ou se déplace le Character, elle possède une liste de Cases
 - Une Case a énormément d'attributs...
 - CaseView observe un CaseMap qui lui est lié ainsi que le personnage : elle pourra ainsi se mettre à jour en fonction
 - Le Character n'a pas accés à la VRAIE carte, mais à AdventureMapCharacter
 - Character a son propre système de coordonnées à travers l'attribut coordForCharacter dans les CaseCharacter
