package contract.model;

import impl.model.AdventureMapImpl;
import impl.model.CaseMapImpl;

public interface AdventureMapGenerator
{
	public AdventureMapImpl createMap(int taille, int nbFall, int nbMonstruous);
	
	public AdventureMap getAdventureMap();
	public void setAdventureMap(AdventureMap adventureMap);
	public CaseMapImpl getSpawnPoint();
	
	public void printMap();
}
