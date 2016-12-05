package impl.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import contract.model.AdventureMap;
import impl.model.AdventureMapImpl;
import impl.model.CharacterImpl;

public class MoveButtonListenerImpl implements ActionListener
{
	private CharacterImpl character;
	private AdventureMapImpl a;
	
	public MoveButtonListenerImpl(CharacterImpl character, AdventureMap a)
	{
		this.character=character;
		this.a = (AdventureMapImpl) a;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(!this.character.isAlive())
		{
			this.character.setAlive(true);
		}
		//character.getEffectorDown().doIt();
	}

}
