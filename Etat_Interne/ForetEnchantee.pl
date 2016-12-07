:-dynamic(putrid/2).
:-dynamic(windy/2).
:-dynamic(caseCovered/2).
:-dynamic(border/6).
:-dynamic(fall/2).
:-dynamic(monstruous/2).
:-dynamic(riskMonstruous/2).
:-dynamic(riskFall/2).
:-dynamic(currentCase/2).
:-dynamic(voisin/2).

% -----------------------------------------------------------------------
% Methodes externes
% -----------------------------------------------------------------------

update_internal_state(CooXCurrentCase, CooYCurrentCase, Putrid, Windy, BordureDroite, BordureGauche, BordureHaut, BordureBas):-
        %Ajout de la case si la case est putride, puis si la case actuelle a des voisins et qu'ils n'ont pas encore ete decouvert
	%alors elle lance la methode interne update_risk_putrid_case.
	%Si la case n'est pas putride alors elle regarde si ses voisins avaient un risque qu'il y ait un monstre. Si oui alors on supprime ce risque et on lance
	%la methode interne update_risk_not_putrid_case.

	%Ajout de la case actuelle dans les cases parcourues
	asserta(caseCovered(CooXCurrentCase, CooYCurrentCase)),
	(currentCase(_,_)
	->  retract(currentCase(_,_))
	;   !),
	asserta(currentCase(CooXCurrentCase, CooYCurrentCase)),

	(Putrid
	 ->  asserta(putrid(CooXCurrentCase, CooYCurrentCase)),
	    VoisinHaut is CooXCurrentCase-1,
	    VoisinDroite is CooYCurrentCase+1,
	    VoisinBas is CooXCurrentCase+1,
	    VoisinGauche is CooYCurrentCase-1,
	    (\+BordureHaut
	     ->	(\+caseCovered(VoisinHaut, CooYCurrentCase)
		->  update_risk_putrid_case(VoisinHaut, CooYCurrentCase)
		;    !)
	    ;    !),
	    (\+BordureDroite
	     ->	(\+caseCovered(CooXCurrentCase, VoisinDroite)
		->  update_risk_putrid_case(CooXCurrentCase, VoisinDroite)
		;    !)
	    ;    !),
	    (\+BordureBas
	     ->	(\+caseCovered(VoisinBas, CooYCurrentCase)
		->  update_risk_putrid_case(VoisinBas, CooYCurrentCase)
		;    !)
	    ;    !),
	    (\+BordureGauche
	     ->	(\+caseCovered(CooXCurrentCase, VoisinGauche)
		->  update_risk_putrid_case(CooXCurrentCase, VoisinGauche)
		;    !)
	    ;    !)
	;   VoisinHaut is CooXCurrentCase-1,
	    VoisinDroite is CooYCurrentCase+1,
	    VoisinBas is CooXCurrentCase+1,
	    VoisinGauche is CooYCurrentCase-1,
	    (	riskMonstruous(VoisinHaut,CooYCurrentCase)
	    ->	retract(riskMonstruous(VoisinHaut,CooYCurrentCase)),
	        update_risk_not_putrid_case(VoisinHaut, CooYCurrentCase)
	    ;	!),
	    (	 riskMonstruous(CooXCurrentCase, VoisinDroite)
	    ->	retract( riskMonstruous(CooXCurrentCase, VoisinDroite)),
	        update_risk_not_putrid_case(CooXCurrentCase, VoisinDroite)
	    ;	!),
	    (	riskMonstruous(VoisinBas, CooYCurrentCase)
	    ->	retract( riskMonstruous(VoisinBas, CooYCurrentCase)),
	        update_risk_not_putrid_case(VoisinBas, CooYCurrentCase)
	    ;	!),
	    (	riskMonstruous(CooXCurrentCase, VoisinGauche)
	    ->	retract(riskMonstruous(CooXCurrentCase, VoisinGauche)),
	        update_risk_not_putrid_case(CooXCurrentCase, VoisinGauche)
	    ;	!)),

	%Si la case actuelle avait des risques ou etait monstrueuse alors on supprime ces risques (car etant sur cette case et n'etant pas mort cette case n'etait pas a risque.
	(   riskMonstruous(CooXCurrentCase, CooYCurrentCase)
	->  retract(riskMonstruous(CooXCurrentCase, CooYCurrentCase))
	;   !),
	(   riskFall(CooXCurrentCase, CooYCurrentCase)
	->  retract(riskFall(CooXCurrentCase, CooYCurrentCase))
	;   !),
	(   monstruous(CooXCurrentCase,CooYCurrentCase)
	->  retract(monstruous(CooXCurrentCase,CooYCurrentCase))
	;   !),

	%Ajout de la case si la case est venteuse, si celle-ci a des voisins et qu'ils n'ont pas encore ete d�couverts alors elle lance update_risk_windy_case.
	%Si la case actuelle n'est pas windy alors si ces voisins avaient un risque qu'il y ait un gouffre, alors on supprime ce risque car il n'a pas lieu d'etre et
	%nous lan�ons la methode update_risk_not_windy_case
	(Windy
	 -> asserta(windy(CooXCurrentCase, CooYCurrentCase)),
	    (\+BordureHaut
	     ->	(\+caseCovered(VoisinHaut, CooYCurrentCase)
		->  update_risk_windy_case(VoisinHaut, CooYCurrentCase)
		;    !)
	    ;   !),
	    (\+BordureDroite
	     ->	(\+caseCovered(CooXCurrentCase, VoisinDroite)
		->  update_risk_windy_case(CooXCurrentCase, VoisinDroite)
		;    !)
	    ;    !),
	    (\+BordureBas
	     ->	(\+caseCovered(VoisinBas, CooYCurrentCase)
		->  update_risk_windy_case(VoisinBas, CooYCurrentCase)
		;    !)
	    ;    !),
	    (\+BordureGauche
	     ->	(\+caseCovered(CooXCurrentCase, VoisinGauche)
		->  update_risk_windy_case(CooXCurrentCase, VoisinGauche)
		;    !)
	    ;    !)
	;   VoisinHaut is CooXCurrentCase-1,
	    VoisinDroite is CooYCurrentCase+1,
	    VoisinBas is CooXCurrentCase+1,
	    VoisinGauche is CooYCurrentCase-1,
	    (	riskFall(VoisinHaut,CooYCurrentCase)
	    ->	retract(riskFall(VoisinHaut,CooYCurrentCase)),
	        update_risk_not_windy_case(VoisinHaut, CooYCurrentCase)
	    ;	!),
	    (	 riskFall(CooXCurrentCase, VoisinDroite)
	    ->	retract( riskFall(CooXCurrentCase, VoisinDroite)),
	        update_risk_not_windy_case(CooXCurrentCase, VoisinDroite)
	    ;	!),
	    (	riskFall(VoisinBas, CooYCurrentCase)
	    ->	retract( riskFall(VoisinBas, CooYCurrentCase)),
	        update_risk_not_windy_case(VoisinBas, CooYCurrentCase)
	    ;	!),
	    (	riskFall(CooXCurrentCase, VoisinGauche)
	    ->	retract(riskFall(CooXCurrentCase, VoisinGauche)),
	        update_risk_not_windy_case(CooXCurrentCase, VoisinGauche)
	    ;	!)),

	%Mise a jours des bordures pour la case actuelle
	((BordureDroite; BordureGauche; BordureHaut; BordureBas)
	->  asserta(border(CooXCurrentCase, CooYCurrentCase, BordureHaut, BordureDroite, BordureBas, BordureGauche))
	;   !),

	 %Ajout des voisins
	 (\+BordureHaut
	 ->NewCoord1 is CooXCurrentCase-1,
	   asserta(voisin((CooXCurrentCase,CooYCurrentCase),(NewCoord1,CooYCurrentCase)))
	 ;   !),

	 (\+BordureDroite
	 ->NewCoord2 is CooYCurrentCase+1,
	   asserta(voisin((CooXCurrentCase,CooYCurrentCase),(CooXCurrentCase,NewCoord2)))
	 ;   !),

	 (\+BordureBas
	 ->NewCoord3 is CooXCurrentCase+1,
	   asserta(voisin((CooXCurrentCase,CooYCurrentCase),(NewCoord3,CooYCurrentCase)))
	 ;  !),

	(\+BordureGauche
	->NewCoord4 is CooYCurrentCase-1,
	  asserta(voisin((CooXCurrentCase,CooYCurrentCase),(CooXCurrentCase,NewCoord4)))
	;  !),

	%Ajout de la case actuelle dans les cases parcourues
	asserta(caseCovered(CooXCurrentCase, CooYCurrentCase)),
	(currentCase(_,_)
	->  retract(currentCase(_,_))
	;   !),
	asserta(currentCase(CooXCurrentCase, CooYCurrentCase)).



%Methode de remise a zero des fais.
raz_internal_state():-
	retractall(putrid(_,_)),
	retractall(windy(_,_)),
	retractall(caseCovered(_,_)),
	retractall(border(_,_,_,_,_,_)),
	retractall(fall(_,_)),
	retractall(monstruous(_,_)),
	retractall(riskMonstruous(_,_)),
	retractall(riskFall(_,_)),
	retractall(currentCase(_,_)),
	retractall(voisin(_,_)).

% Methode dont le but est de prendre une decision sur les actions a
% faire. Elle retourne une liste d'action � effectuer et l'envoie a
% java.
takeDecisions(Reponse):-
	currentCase(CooX,_),
	currentCase(_,CooY),
	writeln("Cherchons un chemin s�r"),
	(   searchSureWay([[(CooX, CooY)]], SolutionSecure)
	->  writeln("Chemin s�r trouv�"),
	    length(SolutionSecure, LengthSolutionSecure),
	    (	LengthSolutionSecure=<10
	    -> inverseur(SolutionSecure, ListeSecure),
	       converter_coo_direction("Secure", ListeSecure,[],_,ListeFinale),
	       Reponse=ListeFinale

	    ; ( searchNearestRiskMonstruous([[(CooX, CooY)]],SolutionMonstruous)
	      ->  length(SolutionMonstruous, LengthSolutionMonstruous),
	          Calcul is LengthSolutionSecure-10-LengthSolutionMonstruous,
	         (	  Calcul>0
	          ->   inverseur(SolutionMonstruous, ListeMonstruous),
		       converter_coo_direction("Monster", ListeMonstruous, [], _, ListeFinale),
		       Reponse=ListeFinale

	          ;	 inverseur(SolutionSecure, ListeSecure),
		         converter_coo_direction("Secure", ListeSecure,[],_,ListeFinale),
	                 Reponse=ListeFinale )
	      ;	 writeln("Cherchons un chemin al�atoire"),
	         randomDirection(CooX, CooY,ListeRandom),
	         writeln("Chemin al�atoire trouv�"),
	         converter_coo_direction("Secure", ListeRandom, [], _, ListeFinale),
	         Reponse=ListeFinale ))

	;  writeln("Chemin s�r pas trouv�"),
	  ( searchNearestRiskMonstruous([[(CooX, CooY)]],SolutionMonstruous)
	  ->  inverseur(SolutionMonstruous, ListeMonstruous),
	      converter_coo_direction("Monster", ListeMonstruous, [], _, ListeFinale),
	      Reponse=ListeFinale

	  ;   writeln("Cherchons un chemin al�atoire"),
	      randomDirection(CooX, CooY,ListeRandom),
	      writeln("Chemin al�atoire trouv�"),
	      converter_coo_direction("Secure", ListeRandom, [], _, ListeFinale),
	      Reponse=ListeFinale)).



% ------------------------------------------------------------------------
% Methodes internes
% -----------------------------------------------------------------------

% on met le voisin de la case windy en riskMonstruous seulement si les
% cases autour du voisin sont soit putrid soit inconnu
update_risk_putrid_case(CooX, CooY):-
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
	(   (caseCovered(VoisinHaut, CooY),caseCovered(CooX, VoisinDroite),caseCovered(VoisinBas, CooY),caseCovered(CooX, VoisinGauche))
	-> asserta(riskMonstruous(CooX, CooY))
	; !).



% on met le voisin de la case windy en riskFall seulement si les cases
% autour du voisin sont soit windy soit inconnu
update_risk_windy_case(CooX, CooY):-
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
	(   (caseCovered(VoisinHaut, CooY),caseCovered(CooX, VoisinDroite),caseCovered(VoisinBas, CooY),caseCovered(CooX, VoisinGauche))
	-> asserta(riskFall(CooX, CooY))
	; !).



% Attention v�rifier auparavant qu'il existe un risque sur la case
% voisine et le supprimer avant de lancer cette m�thode
% Cette methode permet de mettre � jours les cases autour de la case
% o� on est et qui n'est pas putrid
update_risk_not_putrid_case(CooX, CooY):-
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
	(   caseCovered(VoisinHaut, CooY)
	->  (putrid(VoisinHaut, CooY)
	    -> detection_monster(VoisinHaut, CooY)
	    ;	!)
	;   !),
	(   caseCovered(CooX, VoisinDroite)
	->  (putrid(CooX, VoisinDroite)
	    ->	detection_monster(CooX, VoisinDroite)
	    ;	!)
	;   !),
	(   caseCovered(VoisinBas, CooY)
	->  (putrid(VoisinBas, CooY)
	    ->	detection_monster(VoisinBas, CooY)
	    ;	!)
	;   !),
	(   caseCovered(CooX, VoisinGauche)
	->  (putrid(VoisinBas, CooY)
	    ->	detection_monster(CooX, VoisinGauche)
	    ;	!)
	;   !).

detection_monster(CooX, CooY):-
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
        Compteur=0,
	(   riskMonstruous(VoisinHaut,CooY)
	->  Compteur=Compteur+1
	;   !),
	(   riskMonstruous(CooX, VoisinDroite)
	->  Compteur=Compteur+1
	;   !),
	(   riskMonstruous(VoisinBas, CooY)
	->  Compteur=Compteur+1
	;   !),
	(   riskMonstruous(CooX, VoisinGauche)
	->  Compteur=Compteur+1
	;   !),
	(   Compteur==1
	->  (riskMonstruous(VoisinHaut,CooY)
	    ->	retract(riskMonstruous(VoisinHaut,CooY)),
		asserta(monstruous(VoisinHaut, CooY))
	    ;	(riskMonstruous(CooX, VoisinDroite)
		->  retract(riskMonstruous(CooX, VoisinDroite)),
		    asserta(monstruous(CooX, VoisinDroite))
		;   (riskMonstruous(VoisinBas, CooY)
		      ->  retract(riskMonstruous(VoisinBas,CooY)),
			  asserta(monstruous(VoisinBas,CooY))
		      ;	  (riskMonstruous(CooX, VoisinGauche)
			  ->  retract(riskMonstruous(CooX, VoisinGauche)),
			      asserta(monstruous(CooX, VoisinGauche))
			  ;   !))))
	;   !).



% Attention verifier auparavant qu'il existe un risque sur la case
% voisine et le supprimer avant de lancer cette methode
% Cette methode permet de mettre � jours les cases autour de la case
% o� on est et qui n'est pas windy
update_risk_not_windy_case(CooX, CooY):-
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
	(   caseCovered(VoisinHaut, CooY)
	->  (windy(VoisinHaut, CooY)
	    -> detection_fall(VoisinHaut, CooY)
	    ;	!)
	;   !),
	(   caseCovered(CooX, VoisinDroite)
	->  (windy(CooX, VoisinDroite)
	    ->	detection_fall(CooX, VoisinDroite)
	    ;	!)
	;   !),
	(   caseCovered(VoisinBas, CooY)
	->  (windy(VoisinBas, CooY)
	    ->	detection_fall(VoisinBas, CooY)
	    ;	!)
	;   !),
	(   caseCovered(CooX, VoisinGauche)
	->  (windy(VoisinBas, CooY)
	    ->	detection_fall(CooX, VoisinGauche)
	    ;	!)
	;   !).

detection_fall(CooX, CooY):-
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
        Compteur=0,
	(   riskFall(VoisinHaut,CooY)
	->  Compteur=Compteur+1
	;   !),
	(   riskFall(CooX, VoisinDroite)
	->  Compteur=Compteur+1
	;   !),
	(   riskFall(VoisinBas, CooY)
	->  Compteur=Compteur+1
	;   !),
	(   riskFall(CooX, VoisinGauche)
	->  Compteur=Compteur+1
	;   !),
	(   Compteur==1
	->  (riskFall(VoisinHaut,CooY)
	    ->	retract(riskFall(VoisinHaut,CooY)),
		asserta(fall(VoisinHaut, CooY))
	    ;	(riskFall(CooX, VoisinDroite)
		->  retract(riskFall(CooX, VoisinDroite)),
		    asserta(fall(CooX, VoisinDroite))
		;   (riskFall(VoisinBas, CooY)
		      ->  retract(riskFall(VoisinBas,CooY)),
			  asserta(fall(VoisinBas,CooY))
		      ;	  (riskFall(CooX, VoisinGauche)
			  ->  retract(riskFall(CooX, VoisinGauche)),
			      asserta(fall(CooX, VoisinGauche))
			  ;   !))))
	;   !).


%%%%%%%%%%%%%%%
%searchSureWay%
%%%%%%%%%%%%%%%

caseUnknownNotRisky((X,Y)):-
	\+caseCovered(X,Y),
	\+riskMonstruous(X,Y),
	\+riskFall(X,Y).

expand(Parent,PathsoFar,ChildStates):-
 findall([Child|PathsoFar],operator(Parent,Child),ChildStates).

caseCovered2((X,Y)):-caseCovered(X,Y).

operator(Parent,Child):-
 voisin(Parent,Child),
 (\+caseUnknownNotRisky(Child)->
    caseCovered2(Child)
 ;   ! ).

%BFS2
%STOP criteria: si j'arrive sur une case inconnue non risqu�e
searchSureWay([[State|Path]|_],[State|Path]):-
	writeln(1),
 caseUnknownNotRisky(State),
 writeln(State+"case Inconnue Non Risquee Trouvee").

% Continue criteria si je ne viens pas d'ajouter une case inconnue non
% risqu�e et ajoute � Solution
searchSureWay([[State|Path]|RestFSet],Solution):-
 writeln(2),
 \+ caseUnknownNotRisky(State),
 writeln(State+caseInconnueNonRisqueeNonTrouvee),
 expand(State,[State|Path],ChildStates),
 prune_l(ChildStates,P_ChildStates),
 append(RestFSet,P_ChildStates,NewFSet),
 searchSureWay(NewFSet,Solution).

%Je passe au traitement de la queue dans le cas ou �a coince
searchSureWay([_|RestFSet],Solution):-
	writeln(3),
 searchSureWay(RestFSet,Solution).

%%%%%%%%%%%%%%%%%%%
%fin searchSureWay%
%%%%%%%%%%%%%%%%%%%




%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%searchNearestRiskMonstruous%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
expand2(Parent,PathsoFar,ChildStates):-
 findall([Child|PathsoFar],operator2(Parent,Child),ChildStates).

riskMonstruous2((X,Y)):-riskMonstruous(X,Y),\+riskFall(X,Y).

operator2(Parent,Child):-
 voisin(Parent,Child),
 (   \+riskMonstruous2(Child)->
 caseCovered2(Child);true).

%Search the riskMonstruous the nearest
%STOP criteria: si j'arrive sur une case riskMonstruous
searchNearestRiskMonstruous([[State|Path]|_],[State|Path]):-
	writeln(1+State),
 riskMonstruous2(State),
 writeln(State+"caseRisqueMonstruous Trouvee").


% Continue criteria si je ne viens pas d'ajouter une case riskMonstruous
% et ajoute � Solution
searchNearestRiskMonstruous([[State|Path]|RestFSet],Solution):-
	writeln(2+State),
 \+ riskMonstruous2(State),
 writeln(State+"caseRisqueMonstruous non Trouvee"),
 expand2(State,[State|Path],ChildStates),
 prune_l(ChildStates,P_ChildStates),
 append(RestFSet,P_ChildStates,NewFSet),
  writeln("Recherche dans: "+NewFSet),
 searchNearestRiskMonstruous(NewFSet,Solution).

%Je passe au traitement de la queue dans le cas ou �a coince
searchNearestRiskMonstruous([_|RestFSet],Solution):-
	writeln(3),
 searchNearestRiskMonstruous(RestFSet,Solution).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%fin searchNearestRiskMonstruous%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%




%%%%%%%%%%%%%%%%%
%randomDirection%
%%%%%%%%%%%%%%%%%

randomDirection(CooXCurrent, CooYCurrent,Liste2):-
	writeln("RANDOOOOOOOOOOM"),
	Liste=[(CooXCurrent,CooYCurrent)],
	VoisinHaut is CooXCurrent-1,
	VoisinDroite is CooYCurrent+1,
	VoisinBas is CooXCurrent+1,
	VoisinGauche is CooYCurrent-1,
	(   (border(CooXCurrent, CooYCurrent, false, _, _, _), \+caseCovered(VoisinHaut, CooYCurrent))
	    ->	append(Liste,[(VoisinHaut, CooYCurrent)],ListeInter)
	    ;	(   (border(CooXCurrent, CooYCurrent, _, false, _, _), \+caseCovered(CooXCurrent, VoisinDroite))
		->  append(Liste, [(CooXCurrent, VoisinDroite)], ListeInter)
		;   (   (border(CooXCurrent, CooYCurrent, _, _, false, _), \+caseCovered(VoisinBas, CooYCurrent))
		    ->  append(Liste, [(VoisinBas, CooYCurrent)], ListeInter)
		    ;	(   (border(CooXCurrent, CooYCurrent, _, _, false, _), \+caseCovered(CooXCurrent, VoisinGauche))
		        ->  append(Liste, [(CooXCurrent, VoisinGauche)], ListeInter)
			;   voisin(CooXCurrent, CooYCurrent, NewCooX, NewCooY),
			    append(Liste, [(NewCooX, NewCooY)],ListeInter))))),
	Liste2=ListeInter.


%%%%%%%%%%%%%%%%%%%%%
%fin randomDirection%
%%%%%%%%%%%%%%%%%%%%%



prune_l([],[]):-!.

prune_l([[State|Path]|RestChilds],[[State|Path]|RestPChilds]):-
  \+ member(State,Path),
  !,
  prune_l(RestChilds,RestPChilds).

prune_l([_|RestChilds],RestPChilds):-
  prune_l(RestChilds,RestPChilds).

%On convertit les coordonn�es en directions
%On convertit les coordonn�es en directions
converter_coo_direction(_,[Element], Liste3, Element, Solution2):-
	Solution2=Liste3.

converter_coo_direction(Intitule, [Tete|Queue], Liste, Solution1, Solution2):-
	nth0(0,[Tete|Queue],Element1),
	nth0(1,[Tete|Queue],Element2),
	process_Couple_CooX(Element1, CooX1),
	process_Couple_CooX(Element2, CooX2),
	process_Couple_CooY(Element1, CooY1),
	process_Couple_CooY(Element2, CooY2),
	length([Tete|Queue],Length),
	writeln("Length"+Length),
	(  (Intitule=="Monster", Length==2)
	->	(   CooX1<CooX2
		 -> append(Liste, [7], Liste2)
		;   !),
		(   CooX2<CooX1
		->  append(Liste, [5], Liste2)
		;   !),
		(CooY1<CooY2
		->  append(Liste, [6], Liste2)
		;   !),
		(CooY2<CooY1
		->  append(Liste,[8], Liste2)
		;   !),
	       ListeTest = Liste2
	;   ListeTest=Liste),
	(   CooX1<CooX2
	->  append(ListeTest, [3], Liste3)
	;   !),
	(   CooX2<CooX1
	->  append(ListeTest, [1], Liste3)
	;   !),
	(CooY1<CooY2
	->  append(ListeTest, [2], Liste3)
	;   !),
	(CooY2<CooY1
	->  append(ListeTest,[4], Liste3)
	;   !),
	converter_coo_direction(Intitule, Queue, Liste3, Solution1, Solution2).

%Methode pour inverser une liste
% l'inverse d'une liste vide est une liste vide
inverseur([],[]).

% pour inverser une liste, je mets le premier �l�mement de la liste
% � la fin du reste de la liste invers�
inverseur([X|Xs],Resultat) :-
    inverseur(Xs,ResultatInter),
    append(ResultatInter, [X], Resultat).


process_Couple_CooX((X,_), CooX):-
	CooX=X.

process_Couple_CooY((_,Y), CooY):-
	CooY=Y.