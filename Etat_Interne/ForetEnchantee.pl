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
	    (\+BordureHaut
	     ->	(\+caseCovered(CooXCurrentCase-1, CooYCurrentCase)
		->  asserta(riskMonstruous(CooXCurrentCase-1, CooYCurrentCase)))),
	    (\+BordureDroite
	     ->	(\+caseCovered(CooXCurrentCase, CooYCurrentCase+1)
		->  asserta(riskMonstruous(CooXCurrentCase, CooYCurrentCase+1)))),
	    (\+BordureBas
	     ->	(\+caseCovered(CooXCurrentCase+1, CooYCurrentCase)
		->  asserta(riskMonstruous(CooXCurrentCase+1, CooYCurrentCase)))),
	    (\+BordureGauche
	     ->	(\+caseCovered(CooXCurrentCase+1, CooYCurrentCase)
		->  asserta(riskMonstruous(CooXCurrentCase+1, CooYCurrentCase))))),		%Ajout de la case si la case est venteuse
	(Windy == true
	 -> asserta(windy(CooXCurrentCase, CooYCurrentCase)),
	    (\+BordureHaut
	     ->	(\+caseCovered(CooXCurrentCase-1, CooYCurrentCase)
		->  asserta(riskMonstruous(CooXCurrentCase-1, CooYCurrentCase)))),
	    (\+BordureDroite
	     ->	(\+caseCovered(CooXCurrentCase, CooYCurrentCase+1)
		->  asserta(riskMonstruous(CooXCurrentCase, CooYCurrentCase+1)))),
	    (\+BordureBas
	     ->	(\+caseCovered(CooXCurrentCase+1, CooYCurrentCase)
		->  asserta(riskMonstruous(CooXCurrentCase+1, CooYCurrentCase)))),
	    (\+BordureGauche
	     ->	(\+caseCovered(CooXCurrentCase+1, CooYCurrentCase)
		->  asserta(riskMonstruous(CooXCurrentCase+1, CooYCurrentCase))))),
	%Mise a jours des bordures pour la case actuelle
	((BordureDroite == true, BordureGauche == true, BordureHaut == true, BordureBas ==true)
	->  asserta(border(CooXCurrentCase, CooYCurrentCase, BordureHaut, BordureDroite, BordureBas, BordureGauche))),
	%Ajout de la case actuelle dans les cases parcourues
	asserta(caseCovered(CooXCurrentCase, CooYCurrentCase)),
	retract(currentCase(_)),
	asserta(currentCase(CooXCurrentCase, CooYCurrentCase)).

