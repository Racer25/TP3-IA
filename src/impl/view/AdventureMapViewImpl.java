package impl.view;


import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import contract.controller.CaseController;
import contract.model.AdventureMap;
import contract.view.AdventureMapView;
import contract.view.CaseView;
import impl.controller.CaseControllerImpl;
import impl.model.AdventureMapImpl;
import impl.model.CaseMapImpl;
import impl.model.CharacterImpl;

public class AdventureMapViewImpl extends JPanel implements AdventureMapView, Observer
{
	private static final long serialVersionUID = 5650978771844115866L;
	
	private CaseView[][] casesView;
	private AdventureMap myMap;
	private CharacterViewImpl characterView;
	private CharacterImpl character;
	private List<CaseController> caseControllers;

	

	public AdventureMapViewImpl(CharacterViewImpl characterView, AdventureMap myMap, CharacterImpl character)
	{
		this.character = character;
		this.characterView = characterView;
		this.myMap = myMap;
		this.setLayout(new GridLayout(this.myMap.getTaille(), this.myMap.getTaille()));
		this.casesView=new CaseView[this.myMap.getTaille()][this.myMap.getTaille()];
		this.caseControllers=new ArrayList<CaseController>();
		for(int i=0; i<this.myMap.getTaille(); i++)
		{
			for(int j=0; j<this.myMap.getTaille(); j++)
			{
				casesView[i][j]=new CaseViewImpl(this.myMap.getCasesMap()[i][j], characterView);
				this.caseControllers.add(new CaseControllerImpl(characterView,
						(AdventureMapImpl) this.myMap,
						(CaseMapImpl) this.myMap.getCasesMap()[i][j],
						(CaseViewImpl)casesView[i][j], 
						character));
				this.add((CaseViewImpl) casesView[i][j]);
				if(this.myMap.getCasesMap()[i][j].isSpawnPoint())
				{
					((CaseViewImpl) casesView[i][j]).setCharacterVisible(true);
				}
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

	@Override
	public void update(Observable arg0, Object arg1) 
	{
		// TODO Auto-generated method stub
		
		
		this.myMap = (AdventureMap) arg1;
		this.removeAll();
		this.validate();
		
		this.setLayout(new GridLayout(this.myMap.getTaille(), this.myMap.getTaille()));
		this.casesView=new CaseView[this.myMap.getTaille()][this.myMap.getTaille()];
		this.caseControllers=new ArrayList<CaseController>();
		for(int i=0; i<this.myMap.getTaille(); i++)
		{
			for(int j=0; j<this.myMap.getTaille(); j++)
			{
				casesView[i][j]=new CaseViewImpl(this.myMap.getCasesMap()[i][j], characterView);
				this.caseControllers.add(new CaseControllerImpl(characterView,
						(AdventureMapImpl) this.myMap,
						(CaseMapImpl) this.myMap.getCasesMap()[i][j],
						(CaseViewImpl)casesView[i][j], 
						character));
				this.add((CaseViewImpl) casesView[i][j]);
				if(this.myMap.getCasesMap()[i][j].isSpawnPoint())
				{
					((CaseViewImpl) casesView[i][j]).setCharacterVisible(true);
				}
			}
		}
		
		this.revalidate();
		this.repaint();
	}
	
	public List<CaseController> getCaseControllers()
	{
		return caseControllers;
	}

	public void setCaseControllers(List<CaseController> caseControllers)
	{
		this.caseControllers = caseControllers;
	}

}
