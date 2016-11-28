package impl.model;

import java.util.Observable;
import java.util.Observer;

import contract.model.AdventureMap;
import contract.model.AdventureMapCharacter;
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
import impl.model.sensor.SensorLightImpl;
import impl.model.sensor.SensorPutridImpl;
import impl.model.sensor.SensorWindyImpl;
import utils.OrientationEnum;

public class LevelHandlerImpl implements LevelHandler, Observer
{
	private int level;
	private AdventureMapGenerator generator;
	private CharacterImpl character;
	
	public LevelHandlerImpl(CharacterImpl character)
	{
		this.level=50;
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
			if(arg1 instanceof Object[])
			{
				Object[] object=(Object[])arg1;
				if(object[0].equals("levelComplete"))
				{
					if((Boolean)object[1])
					{
						this.level++;
						generateLevel();
					}
				}
				else if(object[0].equals("alive"))
				{
					if(!(Boolean)object[1])
					{
						generateLevel();
					}
				}
				else if(object[0].equals("coordonnees"))
				{
					
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
		AdventureMap map=this.generator.createMap(dim, nbFall, nbMonstruous);
		//Character configuration
		configureCharacter(map);
		
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
	
	private void configureCharacter(AdventureMap map)
	{
		this.character.setLevelComplete(false);
		AdventureMapCharacter mapCharacter=new AdventureMapCharacterImpl();
		this.character.setMapDiscovered(mapCharacter);
		this.character.setOrientation(OrientationEnum.RIGHT);
		this.character.setAlive(true);
		
		//Spawnpoint impact
		int[] tab={0,0};
		CaseCharacter spawnPointCharacter=new CaseCharacterImpl(tab, true, false, false, false, false, false);
		this.character.setCurrentCase(spawnPointCharacter);//Current Case
		this.character.getMapDiscovered().addCase(spawnPointCharacter);//Memory
		
		//Initailize sensors and effectors
		CaseMap spawnPointMap=this.generator.getSpawnPoint();
		this.character.setSensorPutrid(new SensorPutridImpl(spawnPointMap));
		this.character.setSensorWindy(new SensorWindyImpl(spawnPointMap));
		this.character.setSensorLight(new SensorLightImpl(spawnPointMap));
		//Implies an update method for each movement
		this.character.setEffectorUp(new EffectorUpImpl(character));
		this.character.setEffectorRight(new EffectorRightImpl(character));
		this.character.setEffectorDown(new EffectorDownImpl(character));
		this.character.setEffectorLeft(new EffectorLeftImpl(character));
		this.character.setEffectorStone(new EffectorStoneImpl(character, map));
		this.character.setEffectorExit(new EffectorExitImpl(character));
	}

}
