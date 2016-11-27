:-dynamic(putride/1).
:-dynamic(venteuse/1).
:-dynamic(caseParcourue/1).
:-dynamic(risque/1).

score_personnage(0).
% /putride et venteux correspondent à l'état de la case sur laquelle se
% trouve le personnage. Ainsi, si la case actuelle n'est pas venteuse ou
% putride le mot sera 'rien' sinon si elle est bien venteuse ou putride
% le terme sera 'caseActuelle'.
%/
putride(rien).
venteux(rien).
%caseParcourue(cooX, cooY).
%risque(Case, nombre).

maj_etat_interne(Score, CasePutride, CaseVenteuse, CooXCaseActuelle, CooYCaseActuelle ):-
	retract(score_personnage(_)),
	asserta(score_personnage(Score)),
	retract(putride(_)),
	asserta(putride(CasePutride)),
	retract(venteux(_)),
	asserta(venteux(CaseVenteuse)),
	asserta(caseParcourue(CooXCaseActuelle, CooYCaseActuelle)).

%Risques à ajouter
%maj_case_risque(cooX, cooY, nombre).





