:-dynamic(putrid/1).
:-dynamic(windy/1).
:-dynamic(caseCovered/2).
:-dynamic(border/6).
:-dynamic(fall/2).
:-dynamic(monstruous/2).
:-dynamic(riskMonstruous/2).
:-dynamic(riskFall/2).
:-dynamic(currentCase/2).
:-dynamic(voisin/2).

% putrid(cooX, cooY).
% windy(cooX, cooY).
% caseCovered(cooX, cooY).
% border(cooX, cooY, bordureHaut,
% bordureDroite, bordureBas, bordureGauche).

update_internal_state(CooXCurrentCase, CooYCurrentCase, Putrid, Windy, BordureDroite, BordureGauche, BordureHaut, BordureBas):-
        %Ajout de la case si la case est putride
	(Putrid== true
	->  asserta(putrid(CooXCurrentCase, CooYCurrentCase)),
	    VoisinHaut is CooXCurrentCase+1,
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
	;   VoisinHaut is CooXCurrentCase+1,
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

	%Ajout de la case si la case est venteuse
	(Windy == true
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
	;    VoisinHaut is CooXCurrentCase+1,
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
	((BordureDroite == true; BordureGauche == true; BordureHaut == true; BordureBas ==true)
	->  asserta(border(CooXCurrentCase, CooYCurrentCase, BordureHaut, BordureDroite, BordureBas, BordureGauche)),
	    %Ajout des voisins
	    (BordureHaut=\=true->
	    NewCoord is CooXCurrentCase-1,
	    asserta(voisin((CooXCurrentCase,CooYCurrentCase),(NewCoord,CooYCurrentCase)));!),
	 (BordureDroite=\=true->
	    NewCoord is CooYCurrentCase+1,
	    asserta(voisin((CooXCurrentCase,CooYCurrentCase),(CooXCurrentCase,NewCoord)));!),
	 (BordureBas=\=true->
	    NewCoord is CooXCurrentCase+1,
	    asserta(voisin((CooXCurrentCase,CooYCurrentCase),(NewCoord,CooYCurrentCase)));!),
	  (BordureGauche=\=true->
	    NewCoord is CooYCurrentCase-1,
	    asserta(voisin((CooXCurrentCase,CooYCurrentCase),(CooXCurrentCase,NewCoord)));!)
	;    !),

	%Ajout de la case actuelle dans les cases parcourues
	asserta(caseCovered(CooXCurrentCase, CooYCurrentCase)),
	(currentCase(_,_)
	->  retract(currentCase(_,_))
	;   !),
	asserta(currentCase(CooXCurrentCase, CooYCurrentCase)).


raz_internal_state():-
	retractall(putrid(_)),
	retractall(windy(_)),
	retractall(caseCovered(_,_)),
	retractall(border(_,_,_,_,_,_)),
	retractall(fall(_,_)),
	retractall(monstruous(_,_)),
	retractall(riskMonstruous(_,_)),
	retractall(riskFall(_,_)),
	retractall(currentCase(_,_)).

takeDecisions(Reponse):-
	currentCase(CooX,_),
	currentCase(_,CooY),
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
	Compteur=4,
        (   (border(CooX, CooY, true, _,_,_); caseCovered(VoisinHaut, CooY); riskMonstruous(VoisinHaut, CooY); riskFall(VoisinHaut, CooY); monstruous(VoisinHaut, CooY); fall(VoisinHaut, CooY))
	->  Compteur=Compteur-1
	;   !),
	(   (border(CooX, CooY, _, true,_,_); caseCovered(CooX, VoisinDroite); riskMonstruous(CooX, VoisinDroite); riskFall(CooX, VoisinDroite); monstruous(CooX, VoisinDroite); fall(CooX, VoisinDroite))
	->  Compteur=Compteur-1
	;   !),
	(   (border(CooX, CooY, _, _,true,_); caseCovered(VoisinBas, CooY); riskMonstruous(VoisinBas, CooY); riskFall(VoisinBas, CooY); monstruous(VoisinBas, CooY); fall(VoisinBas, CooY))
	->  Compteur=Compteur-1
	;   !),
        (   (border(CooX, CooY, _, _,_,true); caseCovered(CooX, VoisinGauche); riskMonstruous(CooX, VoisinGauche); riskFall(CooX, VoisinGauche); monstruous(CooX, VoisinGauche); fall(CooX, VoisinGauche))
	->  Compteur=Compteur-1
	;   !),
        (   Compteur>0
	-> ValMax is Compteur+1,
	   random(1,ValMax,ValRandom),
	   CompteurPossibilite=0
	;  ( search_closest_secure_case(ReponseSecure)
	   ->  length(ReponseSecure, LongueurSecure),
	       (   LongueurSecure>10
	       ->  ( search_closest_monstruous_case(ReponseMonstruous)
		   ->  length(ReponseMonstruous, LongueurMonstruous),
		       Calcul is LongueurSecure-10-LongueurMonstruous,
		       (   Calcul>0
		       ->  Reponse=ReponseMonstruous
		       ;   Reponse=ReponseSecure)
		   ;   !)
	       ;   Reponse=ReponseSecure)
	   ;   ( search_closest_monstruous_case(ReponseMonstruous)
	       ->  Reponse=ReponseMonstruous
	       ;   !))).


update_risk_putrid_case(CooX, CooY):-
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
	Continuer=true,
	(   caseCovered(VoisinHaut, CooY)
	-> (\+putrid(VoisinHaut, CooY)
	    -> Continuer=false
	   ;   !)
	;   !),
	(   (Continuer, caseCovered(CooX, VoisinDroite))
	-> (\+putrid(CooX, VoisinDroite)
	    -> Continuer=false
	   ;   !)
	;  ! ),
	(   (Continuer, caseCovered(VoisinBas, CooY))
	-> (\+putrid(VoisinBas, CooY)
	    -> Continuer=false
	   ;  ! )
	;  ! ),
	(   (Continuer, caseCovered(CooX, VoisinGauche))
	-> (\+putrid(CooX, VoisinGauche)
	    -> Continuer=false
	   ;  ! )
	;   !),
        (   Continuer
	-> asserta(riskMonstruous(CooX, CooY))
	; !).

update_risk_windy_case(CooX, CooY):-
	VoisinHaut is CooX-1,
	VoisinDroite is CooY+1,
	VoisinBas is CooX+1,
	VoisinGauche is CooY-1,
	Continuer=true,
	(   caseCovered(VoisinHaut, CooY)
	-> (\+windy(VoisinHaut, CooY)
	    -> Continuer=false
	   ;   !)
	;   !),
	(   (Continuer, caseCovered(CooX, VoisinDroite))
	-> (\+windy(CooX, VoisinDroite)
	    -> Continuer=false
	   ;   !)
	;  ! ),
	(   (Continuer, caseCovered(VoisinBas, CooY))
	-> (\+windy(VoisinBas, CooY)
	    -> Continuer=false
	   ;  ! )
	;  ! ),
	(   (Continuer, caseCovered(CooX, VoisinGauche))
	-> (\+windy(CooX, VoisinGauche)
	    -> Continuer=false
	   ;  ! )
	;   !),
        (   Continuer
	-> asserta(riskFall(CooX, CooY))
	; !).

% Attention vérifier auparavant qu'il existe un risque sur la case
% voisine et le supprimer avant de lancer cette méthode
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


% Attention vérifier auparavant qu'il existe un risque sur la case
% voisine et le supprimer avant de lancer cette méthode
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
    caseCovered2(Child);true).

%BFS2
%STOP criteria: si j'arrive sur une case inconnue non risquée
searchSureWay([[State|Path]|_],[State|Path]):-
	writeln(1),
 caseUnknownNotRisky(State),
 writeln(State+caseInconnueNonRisqueeTrouvee).

% Continue criteria si je ne viens pas d'ajouter une case inconnue non
% risquée et ajoute à Solution
searchSureWay([[State|Path]|RestFSet],Solution):-
	writeln(2),
 \+ caseUnknownNotRisky(State),
 writeln(State+caseInconnueNonRisqueeNonTrouvee),
 expand(State,[State|Path],ChildStates),
 prune_l(ChildStates,P_ChildStates),
 append(RestFSet,P_ChildStates,NewFSet),
 searchSureWay(NewFSet,Solution).

%Je passe au traitement de la queue dans le cas ou ça coince
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
% et ajoute à Solution
searchNearestRiskMonstruous([[State|Path]|RestFSet],Solution):-
	writeln(2+State),
 \+ riskMonstruous2(State),
 writeln(State+"caseRisqueMonstruous non Trouvee"),
 expand2(State,[State|Path],ChildStates),
 prune_l(ChildStates,P_ChildStates),
 append(RestFSet,P_ChildStates,NewFSet),
  writeln("Recherche dans: "+NewFSet),
 searchNearestRiskMonstruous(NewFSet,Solution).

%Je passe au traitement de la queue dans le cas ou ça coince
searchNearestRiskMonstruous([_|RestFSet],Solution):-
	writeln(3),
 searchNearestRiskMonstruous(RestFSet,Solution).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%fin searchNearestRiskMonstruous%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


prune_l([],[]):-!.
prune_l([[State|Path]|RestChilds],[[State|Path]|RestPChilds]):-
 \+ member(State,Path),
 !,
 prune_l(RestChilds,RestPChilds).

prune_l([_|RestChilds],RestPChilds):-
 prune_l(RestChilds,RestPChilds).

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
LongueurSecure	->  Compteur=Compteur+1
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
