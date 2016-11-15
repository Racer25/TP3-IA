package contract.model;

public interface AdventureMap 
{
	public int[] getChangeReference();
	public void setChangeReference(int[] changeReference);
	
	CaseMap[][] getCasesMap();
	public void setCasesMap(CaseMap[][] cases);
	
	public int getTaille();
	public void setTaille(int taille);
}
