package impl.model.effector;

import java.util.HashMap;

import contract.model.CaseCharacter;
import contract.model.Effector;
import impl.model.CaseCharacterImpl;
import impl.model.CharacterImpl;
import utils.DirectionEnum;

public class EffectorRightImpl implements Effector
{
	private CharacterImpl character;

	public EffectorRightImpl(CharacterImpl character)
	{
		this.character=character;
	}
	
	@Override
	public void doIt()
	{
		int[] newCoord={
				this.character.getCurrentCase().getCoords()[0],
				this.character.getCurrentCase().getCoords()[1]+1};
		CaseCharacter newCase=new CaseCharacterImpl(newCoord, false, false, false, false, false, false, new HashMap<DirectionEnum, Boolean>());
		this.character.setCurrentCase(newCase);
		this.character.setScore(this.character.getScore()-1);
	}
}
