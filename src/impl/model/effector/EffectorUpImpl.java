package impl.model.effector;

import contract.model.CaseCharacter;
import contract.model.Effector;
import impl.model.CaseCharacterImpl;
import impl.model.CharacterImpl;

public class EffectorUpImpl implements Effector
{
	private CharacterImpl character;
	
	public EffectorUpImpl(CharacterImpl character)
	{
		this.character=character;
	}

	@Override
	public void doIt()
	{
		int[] newCoord={
				this.character.getCurrentCase().getCoords()[0]-1,
				this.character.getCurrentCase().getCoords()[1]};
		CaseCharacter newCase=new CaseCharacterImpl(newCoord, false, false, false, false, false, false);
		this.character.setCurrentCase(newCase);
	}

}
