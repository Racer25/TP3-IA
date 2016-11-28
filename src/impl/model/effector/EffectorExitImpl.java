package impl.model.effector;

import contract.model.Effector;
import impl.model.CharacterImpl;

public class EffectorExitImpl implements Effector
{
	private CharacterImpl character;
	
	public EffectorExitImpl(CharacterImpl character)
	{
		this.character=character;
	}

	@Override
	public void doIt()
	{
		character.setLevelComplete(true);
	}

}
