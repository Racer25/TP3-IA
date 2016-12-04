package impl.model;

import java.util.Observable;
import java.util.Observer;

import contract.model.AdventureMap;
import contract.model.CaseMap;

public class AdventureMapImpl implements AdventureMap, Observer
{
	private int[] changeReference;//pass from CoordCharacter to CoordMap
	private CaseMapImpl[][] cases;
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
		this.cases=(CaseMapImpl[][]) cases;
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

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
