package impl.model;

import contract.model.AdventureMap;
import contract.model.CaseMap;

public class AdventureMapImpl implements AdventureMap
{
	private int[] changeReference;
	private CaseImpl[][] cases;
	private int taille;

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

	@Override
	public void setTaille(int taille)
	{
		this.taille=taille;
	}

}
