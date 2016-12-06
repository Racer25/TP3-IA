package impl.view;


import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import contract.controller.CaseController;
import contract.model.AdventureMap;
import contract.view.AdventureMapView;
import contract.view.CaseView;
import impl.controller.CaseControllerImpl;
import impl.model.AdventureMapImpl;
import impl.model.CaseMapImpl;
import impl.model.CharacterImpl;
import utils.OrientationEnum;

public class AdventureMapViewImpl extends JPanel implements AdventureMapView, Observer
{
	private static final long serialVersionUID = 5650978771844115866L;
	
	private CaseView[][] casesView;
	private AdventureMap myMap;
	private CharacterViewImpl characterView;
	private CharacterImpl character;

	public AdventureMapViewImpl(CharacterViewImpl characterView, AdventureMap myMap, CharacterImpl character)
	{
		this.character = character;
		this.characterView = characterView;
		this.myMap = myMap;
		this.setLayout(new GridLayout(this.myMap.getTaille(), this.myMap.getTaille()));
		this.casesView=new CaseView[this.myMap.getTaille()][this.myMap.getTaille()];
		for(int i=0; i<this.myMap.getTaille(); i++)
		{
			for(int j=0; j<this.myMap.getTaille(); j++)
			{
				casesView[i][j]=new CaseViewImpl(this.myMap.getCasesMap()[i][j], characterView);
				@SuppressWarnings("unused")
				CaseController caseController=new CaseControllerImpl(characterView,
						(AdventureMapImpl) this.myMap,
						(CaseMapImpl) this.myMap.getCasesMap()[i][j],
						(CaseViewImpl)casesView[i][j], 
						character);
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
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		this.myMap = (AdventureMap) arg1;
		System.out.println("Ah que coucou la vue ");

		removeAll();
		
		this.setLayout(new GridLayout(this.myMap.getTaille(), this.myMap.getTaille()));
		this.casesView=new CaseView[this.myMap.getTaille()][this.myMap.getTaille()];
		for(int i=0; i<this.myMap.getTaille(); i++)
		{
			for(int j=0; j<this.myMap.getTaille(); j++)
			{
				casesView[i][j]=new CaseViewImpl(this.myMap.getCasesMap()[i][j], characterView);
				@SuppressWarnings("unused")
				CaseController caseController=new CaseControllerImpl(characterView,
						(AdventureMapImpl) this.myMap,
						(CaseMapImpl) this.myMap.getCasesMap()[i][j],
						(CaseViewImpl)casesView[i][j], 
						character);
				this.add((CaseViewImpl) casesView[i][j]);
				if(this.myMap.getCasesMap()[i][j].isSpawnPoint())
				{
					((CaseViewImpl) casesView[i][j]).setCharacterVisible(true);
				}
			}
		}
		
		revalidate();
	}

}
