package impl.model;

import java.util.ArrayList;
import java.util.List;

import contract.model.AdventureMap;
import contract.model.CaseMap;

public class AdventureMapImpl implements AdventureMap
{
	private int[] changeReference;
	private List<CaseImpl> cases;

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
	public List<CaseMap> getCasesMap()
	{
		List<CaseMap> list2=new ArrayList<CaseMap>();
		for(CaseImpl maCase : this.cases)
		{
			list2.add(maCase);
		}
		return list2;
	}

	@Override
	public void setCasesMap(List<CaseMap> cases)
	{
		List<CaseImpl> list2=new ArrayList<CaseImpl>();
		for(CaseMap maCase : cases)
		{
			list2.add((CaseImpl) maCase);
		}
		this.cases=list2;
	}

}
