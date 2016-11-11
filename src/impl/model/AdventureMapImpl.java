package impl.model;

import contract.model.AdventureMap;
import contract.model.CaseMap;

public class AdventureMapImpl implements AdventureMap
{
	private int[] changeReference;
	private CaseImpl[][] cases;
	private int taille;

	@Override
	public CaseImpl getSpawnPoint()
	{
		CaseImpl spawnPoint=null;
		for(int i=0; i < this.cases.length; i++)
		{
			for(int j=0; j < this.cases[i].length; j++)
			{
				
			}
		}
		return spawnPoint;
	}

	@Override
	public CaseImpl getPortalPoint()
	{
		return null;
	}

	public int[] getChangeReference()
	{
		return changeReference;
	}

	public void setChangeReference(int[] changeReference)
	{
		this.changeReference = changeReference;
	}

	@Override
	public CaseMap[][] getCasesMap()
	{
		return (CaseMap[][])this.cases;
	}

	@Override
	public void setCasesMap(CaseMap[][] cases)
	{
		this.cases=(CaseImpl[][]) cases;
	}

	@Override
	public int getTaille()
	{
		return this.taille;
	}

}
