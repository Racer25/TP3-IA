package impl.model;

import java.util.Observable;

import contract.model.AdventureMapCharacter;
import contract.model.CaseCharacter;
import contract.model.Character;

public class CharacterImpl extends Observable implements Character, Runnable
{
	private boolean alive;
	private int score;
	private String orientation;//up, right, down or left
	private CaseCharacter currentCase;
	
	//Memory
	private AdventureMapCharacter mapDiscovered;

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

	public String getOrientation()
	{
		return orientation;
	}

	public void setOrientation(String orientation)
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
	}

	public AdventureMapCharacter getMapDiscovered()
	{
		return mapDiscovered;
	}

	public void setMapDiscovered(AdventureMapCharacter mapDiscovered)
	{
		this.mapDiscovered = mapDiscovered;
	}
}
