package impl.controller;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import contract.controller.CaseController;
import contract.model.CaseMap;
import contract.view.CaseView;
import impl.model.AdventureMapImpl;
import impl.model.CaseCharacterImpl;
import impl.model.CaseMapImpl;
import impl.model.CharacterImpl;
import impl.view.CaseViewImpl;
import impl.view.CharacterViewImpl;

public class CaseControllerImpl implements CaseController, Observer
{
	private CaseMap maCase;
	private CaseView maCaseView;
	private CharacterImpl character;
	private AdventureMapImpl adventureMap;
	private CharacterViewImpl characterView;

	public CaseControllerImpl(CharacterViewImpl characterView, AdventureMapImpl adventureMap, CaseMap maCase, CaseView maCaseview, CharacterImpl character)
	{
		this.maCase=maCase;
		this.maCaseView=maCaseview;
		this.character=character;
		this.adventureMap=adventureMap;
		this.characterView = characterView;
		
		((CaseMapImpl) maCase).addObserver(this);
		character.addObserver(this);
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		if(arg0 instanceof CharacterImpl)
		{
			if(arg1 instanceof Object[])
			{
				//appear/diseappear character
				Object[] object=(Object[])arg1;
				if(object[0].equals("case"))
				{
					((CaseViewImpl) maCaseView).setCharacterVisible(false);
					int x = ((CaseCharacterImpl) object[1]).getCoords()[0] + adventureMap.getChangeReference()[0];
					int y = ((CaseCharacterImpl) object[1]).getCoords()[1] + adventureMap.getChangeReference()[1];
					int[] coord = new int[2];
					coord[0] = x;
					coord[1] = y;
					if(maCase.getCoords()[0]==coord[0] && maCase.getCoords()[1]==coord[1])
					{
						((CaseViewImpl) maCaseView).setCharacterVisible(true);
						if(maCase.isPutrid() && maCase.isWindy())
						{
							sonWindZombi();
						}
						else if(maCase.isPutrid())
						{
							sonZombi();
						}
						else if(maCase.isWindy())
						{
							sonWind();
						}
					}
				}
					//maCaseView.setPlayerVisible(true)
				else
				{
					//maCaseView.setPlayerVisible(false)
				}
			}
			
		}
		else if(arg0 instanceof CaseMapImpl)
		{
			//Change caseView Visual
			Object[] object=(Object[])arg1;
			if(object[0].equals("character"))
			{
				
			}
		}
	}
	
	public void sonZombi()
	{
		Runnable son = new Runnable()
		{
			@Override
			public void run()
			{
				try{
		            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("son/zombi.wav"));
		            Clip test = AudioSystem.getClip();  

		            test.open(ais);
		            test.start();

		            while (!test.isRunning())
		                Thread.sleep(10);
		            while (test.isRunning())
		                Thread.sleep(10);

		            test.close();
		        }catch(Exception ex){
		            ex.printStackTrace();
		        }
			}
		};
		new Thread (son).start();
	
	}


	public void sonWind()
	{
		Runnable son = new Runnable()
		{
			@Override
			public void run()
			{
				try{
		            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("son/wind.wav"));
		            Clip test = AudioSystem.getClip();  
	
		            test.open(ais);
		            test.start();
	
		            while (!test.isRunning())
		                Thread.sleep(10);
		            while (test.isRunning())
		                Thread.sleep(10);
	
		            test.close();
		        }catch(Exception ex){
		            ex.printStackTrace();
		        }
			}
		};
		new Thread (son).start();
	
	}
	
	public void sonWindZombi()
	{
		Runnable son = new Runnable()
		{
			@Override
			public void run()
			{
				try{
		            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("son/zombiwind.wav"));
		            Clip test = AudioSystem.getClip();  
	
		            test.open(ais);
		            test.start();
	
		            while (!test.isRunning())
		                Thread.sleep(10);
		            while (test.isRunning())
		                Thread.sleep(10);
	
		            test.close();
		        }catch(Exception ex){
		            ex.printStackTrace();
		        }
			}
		};
		new Thread (son).start();
	
	}
}