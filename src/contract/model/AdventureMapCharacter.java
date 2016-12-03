package contract.model;

import java.util.List;

public interface AdventureMapCharacter
{
	//Give the CaseCharacter discovered
	public List<CaseCharacter> getCasesCharacter();
	public void setCasesCharacter(List<CaseCharacter> cases);
	public void addCase(CaseCharacter maCase);
	
	
}
