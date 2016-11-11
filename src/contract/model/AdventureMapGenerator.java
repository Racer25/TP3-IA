package contract.model;

import impl.model.AdventureMapImpl;
import impl.model.CaseImpl;

public interface AdventureMapGenerator
{
	public AdventureMapImpl createMap(int taille, int nbFall, int nbMonstruous);
	
	public AdventureMap getAdventureMap();
	public void setAdventureMap(AdventureMap adventureMap);
	public CaseImpl getSpawnPoint();
}
