:-dynamic(putrid/1).
:-dynamic(windy/1).
:-dynamic(caseCovered/2).
:-dynamic(risk/4).
:-dynamic(bordure/6).

score_character(0).
%putrid(cooX, cooY).
%windy(cooX, cooY).
%caseCovered(cooX, cooY).
%risk(cooX, cooY, nombrePutrid, nombreWindy).
% bordure(cooX, cooY, bordureHaut, bordureDroite, bordureBas,
% bordureGauche).

initialisation(Putrid, Windy, BordureDroite, BordureGauche, BordureHaut, BordureBas):-	(((BordureHaut == true, BordureGauche == true);(BordureHaut == true, BordureDroite == true);(BordureBas == true, BordureGauche == true); (BordureBas == true, BordureDroite == true))
	->  NbBordure = 2
	;   ((BordureHaut == true; BordureGauche == true; BordureDroite == true; BordureBas == true)
	    ->	 NbBordure = 1
	    ;		NbBordure = 0)),



	((Putrid == true, Windy == true)
	 ->  (NbBordure == 2
	     ->	 ((BordureHaut == true, BordureGauche == true)
		 ->  asserta(risk(0,1,3,3)),
		     asserta(risk(1,0,3,3))
		 ;   ((BordureHaut == true, BordureDroite == true)
		     ->	 asserta(risk(0,-1,3,3)),
			 asserta(risk(1,0,3,3))
		      ;	((BordureBas == true, BordureGauche == true)
			 ->  asserta(risk(0,1,3,3)),
			     asserta(risk(-1,0,3,3))
			 ;   ((BordureBas == true, BordureDroite == true)
			     ->	 asserta(risk(0-1,3,3)),
				 asserta(risk(-1,0,3,3))))))
	     ;	 (NbBordure == 1
		 ->  (BordureHaut == true
		     ->	 asserta(risk(0,-1,2,2)),
			 asserta(risk(0,1,2,2)),
			 asserta(risk(-1,0,2,2))
		     ;	 (BordureDroite == true
			 ->  asserta(risk(-1,0,2,2)),
			     asserta(risk(1,0,2,2)),
			     asserta(risk(0,-1,2,2))
			 ;   (BordureBas == true
			     ->	 asserta(risk(0,-1,2,2)),
				 asserta(risk(0,1,2,2)),
			         asserta(risk(1,0,2,2))
			     ;	 (BordureGauche == true
				 ->  asserta(risk(-1,0,2,2)),
				     asserta(risk(1,0,2,2)),
				     asserta(risk(0,1,2,2))))))
		 ;   (NbBordure == 0
		     ->	 asserta(risk(1,0,1,1)),
			 asserta(risk(-1,0,1,1)),
		         asserta(risk(0,1,1,1)),
		         asserta(risk(0,-1,1,1)))))


	;   (Putrid == true
	    ->	 (NbBordure == 2
	     ->	 ((BordureHaut == true, BordureGauche == true)
		 ->  asserta(risk(0,1,3,0)),
		     asserta(risk(1,0,3,0))
		 ;   ((BordureHaut == true, BordureDroite == true)
		     ->	 asserta(risk(0,-1,3,0)),
			 asserta(risk(1,0,3,0))
		      ;	 ((BordureBas == true, BordureGauche == true)
			 ->  asserta(risk(0,1,3,0)),
			     asserta(risk(-1,0,3,0))
			 ;   ((BordureBas == true, BordureDroite == true)
			     ->	 asserta(risk(0-1,3,0)),
				 asserta(risk(-1,0,3,0))))))
	     ;	 (NbBordure == 1
		 ->  (BordureHaut == true
		     ->	 asserta(risk(0,-1,2,0)),
			 asserta(risk(0,1,2,0)),
			 asserta(risk(-1,0,2,0))
		     ;	 (BordureDroite == true
			 ->  asserta(risk(-1,0,2,0)),
			     asserta(risk(1,0,2,0)),
			     asserta(risk(0,-1,2,0))
			 ;   (BordureBas == true
			     ->	 asserta(risk(0,-1,2,0)),
				 asserta(risk(0,1,2,0)),
			         asserta(risk(1,0,2,0))
			     ;	 (BordureGauche == true
				 ->  asserta(risk(-1,0,2,0)),
				     asserta(risk(1,0,2,0)),
				     asserta(risk(0,1,2,0))))))
		 ;   (NbBordure == 0
		     ->	 asserta(risk(1,0,1,0)),
			 asserta(risk(-1,0,1,0)),
		         asserta(risk(0,1,1,0)),
		         asserta(risk(0,-1,1,0)))))

	    ;	(Windy == true
		->  (NbBordure == 2
	     ->	 ((BordureHaut == true, BordureGauche == true)
		 ->  asserta(risk(0,1,0,3)),
		     asserta(risk(1,0,0,3))
		 ;   ((BordureHaut == true, BordureDroite == true)
		     ->	 asserta(risk(0,-1,0,3)),
			 asserta(risk(1,0,0,3))
		      ;	 ((BordureBas == true, BordureGauche == true)
			 ->  asserta(risk(0,1,0,3)),
			     asserta(risk(-1,0,0,3))
			 ;   ((BordureBas == true, BordureDroite == true)
			     ->	 asserta(risk(0-1,0,3)),
				 asserta(risk(-1,0,0,3))))))
	     ;	 (NbBordure == 1
		 ->  (BordureHaut == true
		     ->	 asserta(risk(0,-1,0,2)),
			 asserta(risk(0,1,0,2)),
			 asserta(risk(-1,0,0,2))
		     ;	 (BordureDroite == true
			 ->  asserta(risk(-1,0,0,2)),
			     asserta(risk(1,0,0,2)),
			     asserta(risk(0,-1,0,2))
			 ;   (BordureBas == true
			     ->	 asserta(risk(0,-1,0,2)),
				 asserta(risk(0,1,0,2)),
			         asserta(risk(1,0,0,2))
			     ;	 (BordureGauche == true
				 ->  asserta(risk(-1,0,0,2)),
				     asserta(risk(1,0,0,2)),
				     asserta(risk(0,1,0,2))))))
		 ;   (NbBordure == 0
		     ->	 asserta(risk(1,0,0,1)),
			 asserta(risk(-1,0,0,1)),
		         asserta(risk(0,1,0,1)),
		         asserta(risk(0,-1,0,1)))))
		;   (NbBordure == 2
	     ->	 ((BordureHaut == true, BordureGauche == true)
		 ->  asserta(risk(0,1,0,0)),
		     asserta(risk(1,0,0,0))
		 ;   ((BordureHaut == true, BordureDroite == true)
		     ->	 asserta(risk(0,-1,0,0)),
			 asserta(risk(1,0,0,0))
		      ;	 ((BordureBas == true, BordureGauche == true)
			 ->  asserta(risk(0,1,0,0)),
			     asserta(risk(-1,0,0,0))
			 ;   ((BordureBas == true, BordureDroite == true)
			     ->	 asserta(risk(0-1,0,0)),
				 asserta(risk(-1,0,0,0))))))
	     ;	 (NbBordure == 1
		 ->  (BordureHaut == true
		     ->	 asserta(risk(0,-1,0,0)),
			 asserta(risk(0,1,0,0)),
			 asserta(risk(-1,0,0,0))
		     ;	 (BordureDroite == true
			 ->  asserta(risk(-1,0,0,0))),
			     asserta(risk(1,0,0,0)),
			     asserta(risk(0,-1,0,0))
			 ;   (BordureBas == true
			     ->	 asserta(risk(0,-1,0,0)),
				 asserta(risk(0,1,0,0)),
			         asserta(risk(1,0,0,0))
			     ;	 (BordureGauche == true
				 ->  asserta(risk(-1,0,0,0)),
				     asserta(risk(1,0,0,0)),
				     asserta(risk(0,1,0,0))))))
		 ;   (NbBordure == 0
		     ->	 asserta(risk(1,0,0,0)),
			 asserta(risk(-1,0,0,0)),
		         asserta(risk(0,1,0,0)),
		         asserta(risk(0,-1,0,0))))))).


update_internal_state(Score, CooXCurrentCase, CooYCurrentCase, Putrid, Windy, BordureDroite, BordureGauche, BordureHaut, BordureBas):-
	%Mise à jours score du personnage
	retract(score_personnage(_)),
	asserta(score_personnage(Score)),
	%Ajout de la case si la case est putride
	(Putrid== true
	->  asserta(putride(CooXCurrentCase, CooYCurrentCase))),
	%Ajout de la case si la case est venteuse
	(Windy == true
	 -> asserta(venteux(CooXCurrentCase, CooYCurrentCase))),
	%Mise à jours des bordures pour la case actuelle
	((BordureDroite == true, BordureGauche == true, BordureHaut == true, BordureBas ==true)
	->  asserta(bordure(CooXCurrentCase, CooYCurrentCase, BordureHaut, BordureDroite, BordureBas, BordureGauche))),
	%Ajout de la case actuelle dans les cases parcourues
	asserta(caseParcourue(CooXCurrentCase, CooYCurrentCase)).


update_risk_case(CooX, CooY):-
	%On efface le risque de la case actuelle si il existe
	retract(risk(CooX, CooY, _)),

	%On fait un premier if car si la case à un risque ou non, les conséquences seront différentes
	( (putrid(CooX, CooY) , windy(CooX, CooY))
	  -> /* Pour chaque voisin, on regarde d'abord si il existait deja un risque sur cette case.
	        Si il existe et qu'il est différent de 0 alors on supprime l'ancienne risque,
		on calcule le nouveau risque et on l'insère à la liste des faits
		Sinon, si il n'existait pas on regarde si la case actuelle possède une bordure.
                Si la case voisine n'est pas un bord alors on calcule le risque et on l'ajoute à la liste des faits*/
	   (risk(CooX+1, CooY, _)
	     ->  risk(CooX+1, CooY, OldRisk),
		 (OldRisk\=0
		  -> retract(risk(CooX+1, CooY, _)),
		     %Calcul du risque pour les cases voisines de la case actuelle
	             calculation_risk(CalculateRisk),
		     asserta(risk(CooX+1, CooY, CalculateRisk)))
	    ;((\+bordure(CooX, CooY, true, _, _, _))
	       ->  %Calcul du risque pour les cases voisines de la case actuelle
		    calculation_risk(CalculateRisk),
	            asserta(risk(CooX+1, CooY, CalculateRisk)))),

	    (risk(CooX-1, CooY, _)
	     ->  risk(CooX-1, CooY, OldRisk),
		 (OldRisk\=0
		  -> retract(risk(CooX-1, CooY, _)),
		     calculation_risk(CalculateRisk),
		     asserta(risk(CooX-1, CooY, CalculateRisk)))
	    ;((\+bordure(CooX, CooY, _, _, true, _))
	       ->  %Calcul du risque pour les cases voisines de la case actuelle
		   calculation_risk(CalculateRisk),
                   asserta(risk(CooX-1, CooY, CalculateRisk)))),

	    (risk(CooX, CooY+1, _)
	     ->  risk(CooX, CooY+1, OldRisk),
		 (OldRisk\=0
		  -> retract(risk(CooX, CooY+1, _)),
		     calculation_risk(CalculateRisk),
		     asserta(risk(CooX, CooY+1, CalculateRisk)))
	    ;((\+bordure(CooX, CooY, _, true, _, _))
	       -> %Calcul du risque pour les cases voisines de la case actuelle
		  calculation_risk(CalculateRisk),
	          asserta(risk(CooX, CooY+1, CalculateRisk)))),


	    (risk(CooX, CooY-1, _)
	     ->  risk(CooX, CooY-1, OldRisk),
		 (OldRisk\=0
		  -> retract(risk(CooX, CooY-1, _)),
		     calculation_risk(CalculateRisk),
		     asserta(risk(CooX, CooY-1, CalculateRisk)))
	    ;((\+bordure(CooX, CooY, _, _, _, true))
	       ->  %Calcul du risque pour les cases voisines de la case actuelle
	             calculation_risk(CalculateRisk),
	             asserta(risk(CooX, CooY-1, CalculateRisk))))



	 ;((\+putrid(CooX, CooY) ; \+windy(CooX, CooY))
	  /*Si il n'y a pas de risque sur la case actuelle alors on supprimer les risques des cases voisines si ils existent*/
	  -> ((risk(CooX+1, CooY, _))
	     ->	 retract(risk(CooX+1, CooY, _))),

	      ((risk(CooX-1, CooY, _))
	      ->  retract(risk(CooX-1,CooY, _))),

	       ((risk(CooX, CooY+1, _))
	      ->  retract(risk(CooX,CooY+1, _))),

	       ((risk(CooX, CooY-1, _))
	      ->  retract(risk(CooX,CooY-1, _))),

	       /* Après avoir supprimer les risques des cases voisines,
	          alors on verifie si la case actuelle possède des bords et si la case voisine existe
	         (donc que ce n'est pas en dehors de la map alors on dit que le risque est de 0 dans la case voisine*/
	      ((\+bordure(CooX, CooY, true, _, _, _))
	       ->  asserta(risk(CooX+1, CooY, 0))),

	      ((\+bordure(CooX, CooY, _, _, true, _))
	       ->  asserta(risk(CooX-1, CooY, 0))),

	      ((\+bordure(CooX, CooY, _, true, _, _))
	       ->  asserta(risk(CooX, CooY+1, 0))),

	      ((\+bordure(CooX, CooY, _, _, _, true))
	       ->  asserta(risk(CooX, CooY-1, 0)))

	)).



%calculation_risk(CalculateRisk):-.

/*Pour chaque case putride/venteuse de la map
Faire
Pour chaque voisin inconnu de cette case (la bordure en fait)
Faire
Si ce voisin inconnu possède un voisin connu ni putride ni venteux.
Accorder un score de 0
Sinon
Accorder un score de 4-
nombre de voisins connus*/





