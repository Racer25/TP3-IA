package impl.model.effector;

import contract.model.Effector;
import impl.model.CharacterImpl;

public class EffectorExit implements Effector
{
	private CharacterImpl character;
	
	public EffectorExit(CharacterImpl character)
	{
		this.character=character;
	}

	@Override
	public void doIt()
	{
		character.setLevelComplete(true);
	}

}
