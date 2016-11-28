package impl.model.effector;

import contract.model.AdventureMap;
import contract.model.CaseCharacter;
import contract.model.Effector;
import impl.model.CharacterImpl;

public class EffectorDownImpl implements Effector
{
	private CharacterImpl character;
	private AdventureMap map;

	public EffectorDownImpl(CharacterImpl character, AdventureMap map)
	{
		this.character=character;
		this.map=map;
	}
	
	@Override
	public void doIt()
	{
		CaseCharacter newCase=(CaseCharacter) 
				map.getCasesMap()
				[character.getCurrentCase().getCoordForCharacter()[0]+1]
				[character.getCurrentCase().getCoordForCharacter()[1]];
		character.setCurrentCase(newCase);
	}
}