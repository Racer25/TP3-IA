package impl.controller;

import java.util.Observable;
import java.util.Observer;

import contract.controller.CaseController;
import impl.model.CaseImpl;
import impl.model.CharacterImpl;
import impl.view.CaseViewImpl;

public class CaseControllerImpl implements CaseController, Observer
{
	private CaseImpl maCase;
	private CaseViewImpl maCaseView;
	private CharacterImpl character;

	public CaseControllerImpl(CaseImpl maCase, CaseViewImpl maCaseview, CharacterImpl character)
	{
		this.maCase=maCase;
		this.maCaseView=maCaseview;
		this.character=character;
		
		maCase.addObserver(this);
		character.addObserver(this);
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		if(arg0 instanceof CharacterImpl)
		{
			//appear/diseappear character
			if((boolean)arg1)
			{
				//maCaseView.setPlayerVisible(true)
			}
			else
			{
				//maCaseView.setPlayerVisible(false)
			}
		}
		else if(arg0 instanceof CaseImpl)
		{
			//Change caseView Visual
			if((boolean)arg1)
			{
				
			}
			else
			{
				
			}
		}
		
	}

}
