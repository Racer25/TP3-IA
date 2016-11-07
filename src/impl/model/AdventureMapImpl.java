package impl.model;

import contract.model.AdventureMap;
import contract.model.CaseMap;

public class AdventureMapImpl implements AdventureMap
{
	private int[] changeReference;
	private CaseImpl[][] cases;

	@Override
	public CaseImpl getSpawnPoint()
	{
		return null;
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

}
