package impl.model;

import java.util.Observable;
import java.util.Observer;

import contract.model.AdventureMapGenerator;
import contract.model.LevelHandler;
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
		generator.getAdventureMap().setChangeReference(generator.getSpawnPoint().getCoordMap());
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
		this.generator.createMap(dim, nbFall, nbMonstruous);
		//Character configuration
		this.character.setLevelComplete(false);
		this.character.setMapDiscovered(null);
		this.character.setOrientation(OrientationEnum.RIGHT);
		this.character.setAlive(true);
		//this.character.setCurrentCase(this.generator.getSpawnPoint());
		//Replace by capteur utilisation
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

}
