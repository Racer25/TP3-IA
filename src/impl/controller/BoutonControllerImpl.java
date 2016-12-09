package impl.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import contract.controller.BoutonController;
import impl.model.CaseCharacterImpl;
import impl.model.CharacterImpl;
import impl.view.CaseViewImpl;

public class BoutonControllerImpl implements BoutonController, Observer {
	
	private CharacterImpl character;
	private JButton move;

	public BoutonControllerImpl(CharacterImpl character, JButton move)
	{
		this.character = character;
		character.addObserver(this);
		this.move = move;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof CharacterImpl)
		{
			if (arg instanceof Object[])
			{
				// appear/diseappear character
				Object[] object = (Object[]) arg;
				if (object[0].equals("active"))
				{
					if(!(boolean) object[1])
					{
						move.setText("MOVE");
					}
				}
			}
		}
					
	}

}
