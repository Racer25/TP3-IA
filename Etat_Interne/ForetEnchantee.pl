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
	    Haut is CooXCurrentCase+1,
	    Droite is CooYCurrentCase+1,
	    Bas is CooXCurrentCase+1,
	    Gauche is CooYCurrentCase-1,
	    (\+BordureHaut
	     ->	(\+caseCovered(Haut, CooYCurrentCase)
		->  asserta(riskMonstruous(Haut, CooYCurrentCase))
		;    writeln(""))
	    ;    writeln("")),
	    (\+BordureDroite
	     ->	(\+caseCovered(CooXCurrentCase, Droite)
		->  asserta(riskMonstruous(CooXCurrentCase, Droite))
		;    writeln(""))
	    ;    writeln("")),
	    (\+BordureBas
	     ->	(\+caseCovered(Bas, CooYCurrentCase)
		->  asserta(riskMonstruous(Bas, CooYCurrentCase))
		;    writeln(""))
	    ;    writeln("")),
	    (\+BordureGauche
	     ->	(\+caseCovered(CooXCurrentCase, Gauche)
		->  asserta(riskMonstruous(CooXCurrentCase, Gauche))
		;    writeln(""))
	    ;    writeln(""))
	;    writeln("")),

	%Ajout de la case si la case est venteuse
	(Windy == true
	 -> asserta(windy(CooXCurrentCase, CooYCurrentCase)),
	    (\+BordureHaut
	     ->	(\+caseCovered(CooXCurrentCase-1, CooYCurrentCase)
		->  asserta(riskWindy(CooXCurrentCase-1, CooYCurrentCase))
		;    writeln(""))
	    ;    writeln("")),
	    (\+BordureDroite
	     ->	(\+caseCovered(CooXCurrentCase, CooYCurrentCase+1)
		->  asserta(riskWindy(CooXCurrentCase, CooYCurrentCase+1))
		;    writeln(""))
	    ;    writeln("")),
	    (\+BordureBas
	     ->	(\+caseCovered(CooXCurrentCase+1, CooYCurrentCase)
		->  asserta(riskWindy(CooXCurrentCase+1, CooYCurrentCase))
		;    writeln(""))
	    ;    writeln("")),
	    (\+BordureGauche
	     ->	(\+caseCovered(CooXCurrentCase, CooYCurrentCase-1)
		->  asserta(riskWindy(CooXCurrentCase, CooYCurrentCase-1))
		;    writeln(""))
	    ;    writeln(""))
	;    writeln("")),

	%Mise a jours des bordures pour la case actuelle
	((BordureDroite == true, BordureGauche == true, BordureHaut == true, BordureBas ==true)
	->  asserta(border(CooXCurrentCase, CooYCurrentCase, BordureHaut, BordureDroite, BordureBas, BordureGauche))
	;    writeln("")),
	%Ajout de la case actuelle dans les cases parcourues
	asserta(caseCovered(CooXCurrentCase, CooYCurrentCase)),
	(currentCase(_,_)
	->  retract(currentCase(_,_))
	;   writeln("")),
	asserta(currentCase(CooXCurrentCase, CooYCurrentCase)).

