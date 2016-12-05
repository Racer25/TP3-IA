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
		System.out.println("Bonjour");
		if(!this.character.isActive())
		{
			this.character.setActive(true);
			System.out.println("Mis à jour de active vers: "+this.character.isActive());
		}
		//character.getEffectorDown().doIt();
	}

}
