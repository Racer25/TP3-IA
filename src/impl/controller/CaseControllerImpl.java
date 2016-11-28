package impl.controller;

import java.util.Observable;
import java.util.Observer;

import contract.controller.CaseController;
import contract.model.CaseMap;
import contract.view.CaseView;
import impl.model.CaseMapImpl;
import impl.model.CharacterImpl;
import impl.view.CaseViewImpl;

public class CaseControllerImpl implements CaseController, Observer
{
	private CaseMap maCase;
	private CaseView maCaseView;
	private CharacterImpl character;

	public CaseControllerImpl(CaseMap maCase, CaseView maCaseview, CharacterImpl character)
	{
		this.maCase=maCase;
		this.maCaseView=maCaseview;
		this.character=character;
		
		((CaseMapImpl) maCase).addObserver(this);
		character.addObserver(this);
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		if(arg0 instanceof CharacterImpl)
		{
			if(arg1 instanceof Boolean)
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
			
		}
		else if(arg0 instanceof CaseMapImpl)
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
