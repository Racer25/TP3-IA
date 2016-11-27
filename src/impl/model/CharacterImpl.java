package impl.model;

import java.util.Observable;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import contract.model.AdventureMapCharacter;
import contract.model.CaseCharacter;
import contract.model.Character;
import utils.OrientationEnum;

public class CharacterImpl extends Observable implements Character, Runnable
{	
	private Boolean alive;
	private Integer score;
	private OrientationEnum orientation;
	private CaseCharacter currentCase;
	private boolean levelComplete;
	
	//Memory
	private AdventureMapCharacter mapDiscovered;
	
	public CharacterImpl()
	{
		this.alive=true;
		this.score=0;
		this.orientation=OrientationEnum.RIGHT;
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
	
	public boolean ConsultPrologFile() {
		Query q1=new Query("consult", new Term[]{new Atom("Etat_Interne/ForetEnchante.pl")});
		return q1.hasSolution();
	}
	
	public Boolean isAlive()
	{
		return alive;
	}

	public void setAlive(Boolean alive)
	{
		this.alive = alive;
		notifyObservers(new Object[]{"alive", alive});
		setChanged();
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

	public OrientationEnum getOrientation()
	{
		return orientation;
	}

	public void setOrientation(OrientationEnum orientation)
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
		notifyObservers(new Object[]{"levelComplete", levelComplete});
		setChanged();
	}
}
