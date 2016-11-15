package impl.view;


import java.awt.GridLayout;

import javax.swing.JPanel;

import contract.controller.CaseController;
import contract.model.AdventureMap;
import contract.view.AdventureMapView;
import contract.view.CaseView;
import impl.controller.CaseControllerImpl;
import impl.model.CaseImpl;
import impl.model.CharacterImpl;

public class AdventureMapViewImpl extends JPanel implements AdventureMapView
{
	private static final long serialVersionUID = 5650978771844115866L;
	
	private CaseView[][] casesView;

	public AdventureMapViewImpl(AdventureMap myMap, CharacterImpl character)
	{
		this.setLayout(new GridLayout(myMap.getTaille(), myMap.getTaille()));
		this.casesView=new CaseView[myMap.getTaille()][myMap.getTaille()];
		for(int i=0; i<myMap.getTaille(); i++)
		{
			for(int j=0; j<myMap.getTaille(); j++)
			{
				casesView[i][j]=new CaseViewImpl(myMap.getCasesMap()[i][j]);
				@SuppressWarnings("unused")
				CaseController caseController=new CaseControllerImpl(
						(CaseImpl) myMap.getCasesMap()[i][j],
						(CaseViewImpl)casesView[i][j], 
						character);
				this.add((CaseViewImpl) casesView[i][j]);
			}
		}
	}
	
	public CaseView[][] getCasesView()
	{
		return casesView;
	}

	public void setCasesView(CaseView[][] casesView)
	{
		this.casesView = casesView;
	}
}
