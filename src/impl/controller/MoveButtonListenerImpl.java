package impl.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JButton;

import contract.model.AdventureMap;
import impl.model.AdventureMapImpl;
import impl.model.CaseCharacterImpl;
import impl.model.CharacterImpl;
import impl.view.CaseViewImpl;

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
		if(!this.character.isActive())
		{
			this.character.setActive(true);
			System.out.println("Mis à jour de active vers: "+this.character.isActive());
			((JButton) e.getSource()).setText("STOP");
		}
		else
		{
			this.character.setActive(false);
			System.out.println("Mis à jour de active vers: "+this.character.isActive());
			((JButton) e.getSource()).setText("MOVE");
		}
	}

}
