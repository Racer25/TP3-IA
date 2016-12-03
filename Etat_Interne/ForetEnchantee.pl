:-dynamic(putrid/1).
:-dynamic(windy/1).
:-dynamic(caseCovered/2).
:-dynamic(border/6).
:-dynamic(fall/2).
:-dynamic(monstruous/2).
:-dynamic(riskMonstruous/2).
:-dynamic(riskFall/2).
:-dynamic(currentCase/2).

% putrid(cooX, cooY).
% windy(cooX, cooY).
% caseCovered(cooX, cooY).
% border(cooX, cooY, bordureHaut,
% bordureDroite, bordureBas, bordureGauche).

takeDecisions(Reponse):-
	currentCase(CooX,_),
	currentCase(_,CooY),
	(   (putrid(CooX, CooY))),
	Reponse=2.

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
	((BordureDroite == true, BordureGauche == true, BordureHaut == true, BordureBas ==true)
	->  asserta(border(CooXCurrentCase, CooYCurrentCase, BordureHaut, BordureDroite, BordureBas, BordureGauche))
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
