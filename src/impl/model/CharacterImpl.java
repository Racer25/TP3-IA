package impl.model;

import java.util.Observable;

import contract.model.AdventureMapCharacter;
import contract.model.CaseCharacter;
import contract.model.Character;

public class CharacterImpl extends Observable implements Character, Runnable
{
	private final static Integer ORIENTATION_UP = 0;
	private final static Integer ORIENTATION_RIGHT = 1;
	private final static Integer ORIENTATION_DOWN = 2;
	private final static Integer ORIENTATION_LEFT = 3;
	
	private boolean alive;
	private Integer score;
	private Integer orientation;
	private CaseCharacter currentCase;
	private boolean levelComplete;
	
	//Memory
	private AdventureMapCharacter mapDiscovered;
	
	public CharacterImpl()
	{
		this.alive=true;
		this.score=0;
		this.orientation=ORIENTATION_RIGHT;
		this.currentCase=null;
		this.setLevelComplete(false);
	}

	@Override
	public void run()
	{
		
	}
	
	@Override
	public void move(String direction)
	{
		// TODO Auto-generated method stub
		
	}
	
	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
		notifyObservers(score);
		setChanged();
	}

	public Integer getOrientation()
	{
		return orientation;
	}

	public void setOrientation(Integer orientation)
	{
		this.orientation = orientation;
	}

	public CaseCharacter getCurrentCase()
	{
		return currentCase;
	}

	public void setCurrentCase(CaseCharacter currentCase)
	{
		this.currentCase = currentCase;
		notifyObservers(currentCase.getCoordForCharacter());
		setChanged();
		if(this.currentCase.isPortalPoint())
		{
			setLevelComplete(true);
		}
	}

	public AdventureMapCharacter getMapDiscovered()
	{
		return mapDiscovered;
	}

	public void setMapDiscovered(AdventureMapCharacter mapDiscovered)
	{
		this.mapDiscovered = mapDiscovered;
	}

	public boolean isLevelComplete()
	{
		return levelComplete;
	}

	public void setLevelComplete(boolean levelComplete)
	{
		this.levelComplete = levelComplete;
		notifyObservers(levelComplete);
		setChanged();
	}
}
