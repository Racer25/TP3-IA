package impl.view;

import java.util.Observable;
import java.util.Observer;

import contract.model.CaseMap;
import contract.view.CaseView;
import impl.model.CharacterImpl;

public class CaseViewImpl implements CaseView, Observer
{
	private CaseMap maCase;
	private Character character;

	@Override
	public void update(Observable arg0, Object arg1)
	{
		if(arg0 instanceof CharacterImpl)
		{
			//appear/diseappear character
		}
		else if(arg0 instanceof CaseMap)
		{
			//Change caseView Visual
		}
		
	}
}
