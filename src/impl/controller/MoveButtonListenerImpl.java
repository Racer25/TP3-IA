package impl.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import impl.model.CharacterImpl;

public class MoveButtonListenerImpl implements ActionListener
{
	private CharacterImpl character;
	
	public MoveButtonListenerImpl(CharacterImpl character)
	{
		this.character=character;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.character.setAlive(false);
		this.character.setScore(this.character.getScore()+5);
	}

}
