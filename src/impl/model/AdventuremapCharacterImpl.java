package impl.model;

import java.util.List;

import contract.model.AdventureMapCharacter;
import contract.model.CaseCharacter;

public class AdventuremapCharacterImpl implements AdventureMapCharacter
{
	private List<CaseCharacter> cases;

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

}
