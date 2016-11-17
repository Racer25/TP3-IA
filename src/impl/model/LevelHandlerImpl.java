package impl.model;

import java.util.Observable;
import java.util.Observer;

import contract.model.AdventureMapGenerator;
import contract.model.LevelHandler;

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
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{	
		if(arg1 instanceof Boolean)
		{
			if((Boolean)arg1 ==true)
			{
				this.level++;
				generateLevel();
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
		this.character.setOrientation(1);
		this.character.setAlive(true);
		this.character.setCurrentCase(this.generator.getSpawnPoint());
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
