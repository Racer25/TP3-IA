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
	;    !),

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
	;    !),

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
