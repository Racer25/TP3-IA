package impl.model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import contract.model.AdventureMapGenerator;
import contract.model.CaseCharacter;
import contract.model.CaseMap;
import contract.model.LevelHandler;
import impl.model.effector.EffectorDownImpl;
import impl.model.effector.EffectorExitImpl;
import impl.model.effector.EffectorLeftImpl;
import impl.model.effector.EffectorRightImpl;
import impl.model.effector.EffectorStoneImpl;
import impl.model.effector.EffectorUpImpl;
import impl.model.sensor.SensorDirectionsImpl;
import impl.model.sensor.SensorLightImpl;
import impl.model.sensor.SensorPutridImpl;
import impl.model.sensor.SensorWindyImpl;
import utils.DirectionEnum;
import utils.OrientationEnum;

public class LevelHandlerImpl implements LevelHandler, Observer
{
	private int level;
	private AdventureMapGenerator generator;
	private CharacterImpl character;
	
	public LevelHandlerImpl(CharacterImpl character)
	{
		this.level=1;
		this.character=character;
		this.generator=new AdventureMapGeneratorImpl();
		generateLevel();
		generator.getAdventureMap().setChangeReference(generator.getSpawnPoint().getCoords());
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		if(arg0 instanceof CharacterImpl)
		{
			//The Character warned me
			if(arg1 instanceof Object[])
			{
				Object[] object=(Object[])arg1;
				if(object[0].equals("levelComplete"))
				{
					//The Character warned me that the level is complete or not
					if((Boolean)object[1])
					{
						//The Character warned me that the level is complete
						this.level++;
						generateLevel();
						configureCharacter();
						character.setScore((int) (character.getScore()+10*Math.pow(generator.getAdventureMap().getTaille(),2)));
					}
				}
				else if(object[0].equals("alive"))
				{
					//The Character warned me that he's alive or not
					if(!(Boolean)object[1])
					{
						//The Character warned me that he's not alive
						//generateLevel();
					}
				}
				else if(object[0].equals("case"))
				{
					character.setScore((int) (character.getScore()-1));
					//The Character warned me that his coordinates are changing
					CaseCharacter newCaseCharacter=(CaseCharacter) object[1];
					//We take the new CaseMap for sensors
					int lineCaseMap=newCaseCharacter.getCoords()[0]+this.generator.getAdventureMap().getChangeReference()[0];
					int columnCaseMap=newCaseCharacter.getCoords()[1]+this.generator.getAdventureMap().getChangeReference()[1];
					CaseMap newCase=this.generator.getAdventureMap().getCasesMap()[lineCaseMap][columnCaseMap];
					
					if(newCase.isFall() || newCase.isMonstruous())
					{
						character.setAlive(false);
						generateLevel();
						configureCharacter();
					}
					
					//Update
					this.character.getSensorPutrid().setCaseMap(newCase);
					this.character.getSensorWindy().setCaseMap(newCase);
					this.character.getSensorLight().setCaseMap(newCase);
					this.character.getSensorDirections().setCaseMap(newCase);
				}
			}
		}
	}
	
	public void generateLevel()
	{
		int dim=this.level+2;
		int nbFall=dim*dim*15/100;
		int nbMonstruous=dim*dim*15/100;
		//Generate Map
		this.generator.createMap(dim, nbFall, nbMonstruous);
		//Character configuration
		character.addObserver((Observer) this.generator.getAdventureMap());
		configureCharacter();
		
	}
	
	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public AdventureMapGenerator getGenerator()
	{
		return generator;
	}

	public void setGenerator(AdventureMapGenerator generator)
	{
		this.generator = generator;
	}
	
	private void configureCharacter()
	{
		this.character.setLevelComplete(false);
		this.character.setOrientation(OrientationEnum.RIGHT);
		this.character.setAlive(true);
		this.character.setActive(false);
		
		//Spawnpoint impact
		int[] tab={0,0};
		CaseCharacter spawnPointCharacter=new CaseCharacterImpl(tab, true, false, false, false, false, false, new HashMap<DirectionEnum, Boolean>());
		this.character.setCurrentCase(spawnPointCharacter);//Current Case
		
		//Initailize sensors and effectors
		CaseMap spawnPointMap=this.generator.getSpawnPoint();
		this.character.setSensorPutrid(new SensorPutridImpl(spawnPointMap));
		this.character.setSensorWindy(new SensorWindyImpl(spawnPointMap));
		this.character.setSensorLight(new SensorLightImpl(spawnPointMap));
		this.character.setSensorDirections(new SensorDirectionsImpl(spawnPointMap));
		//Implies an update method for each movement
		this.character.setEffectorUp(new EffectorUpImpl(character));
		this.character.setEffectorRight(new EffectorRightImpl(character));
		this.character.setEffectorDown(new EffectorDownImpl(character));
		this.character.setEffectorLeft(new EffectorLeftImpl(character));
		this.character.setEffectorStone(new EffectorStoneImpl(character, this.generator.getAdventureMap()));
		this.character.setEffectorExit(new EffectorExitImpl(character));
	}

}
