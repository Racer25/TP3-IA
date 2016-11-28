package impl.model.effector;

import contract.model.AdventureMap;
import contract.model.CaseCharacter;
import contract.model.Effector;
import impl.model.CharacterImpl;

public class EffectorUpImpl implements Effector
{
	private CharacterImpl character;
	private AdventureMap map;
	
	public EffectorUpImpl(CharacterImpl character, AdventureMap map)
	{
		this.character=character;
		this.map=map;
	}

	@Override
	public void doIt()
	{
		CaseCharacter newCase=(CaseCharacter) 
				map.getCasesMap()
				[character.getCurrentCase().getCoords()[0]+map.getChangeReference()[0]-1]
				[character.getCurrentCase().getCoords()[1]+map.getChangeReference()[1]];
		character.setCurrentCase(newCase);
	}

}
