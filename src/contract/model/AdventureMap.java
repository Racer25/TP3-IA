package contract.model;

import java.util.List;

public interface AdventureMap 
{
	public int[] getChangeReference();
	public void setChangeReference(int[] changeReference);
	
	List<CaseMap> getCasesMap();
	public void setCasesMap(List<CaseMap> cases);
	
	public CaseMap getSpawnPoint();
	public CaseMap getPortalPoint();
	
}
