package impl.model;

import java.util.ArrayList;
import java.util.List;

import contract.model.AdventureMapCharacter;
import contract.model.CaseCharacter;

public class AdventureMapCharacterImpl implements AdventureMapCharacter
{
	private List<CaseCharacter> cases;
	
	public AdventureMapCharacterImpl()
	{
		this.cases=new ArrayList<CaseCharacter>();
	}

	@Override
	public List<CaseCharacter> getCasesCharacter()
	{
		return this.cases;
	}

	@Override
	public void setCasesCharacter(List<CaseCharacter> cases)
	{
		this.cases=cases;
	}

	@Override
	public void addCase(CaseCharacter maCase)
	{
		cases.add(maCase);
	}

}
