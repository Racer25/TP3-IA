package impl.model.effector;

import contract.model.AdventureMap;
import contract.model.CaseCharacter;
import contract.model.Effector;
import impl.model.CharacterImpl;

public class EffectorLeftImpl implements Effector
{
	private CharacterImpl character;
	private AdventureMap map;

	public EffectorLeftImpl(CharacterImpl character, AdventureMap map)
	{
		this.character=character;
		this.map=map;
	}
	
	@Override
	public void doIt()
	{
		CaseCharacter newCase=(CaseCharacter) 
				map.getCasesMap()
				[character.getCurrentCase().getCoordForCharacter()[0]]
				[character.getCurrentCase().getCoordForCharacter()[1]-1];
		character.setCurrentCase(newCase);
	}
}
