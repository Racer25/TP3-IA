package impl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import contract.model.AdventureMapCharacter;
import contract.model.CaseCharacter;
import contract.model.Character;
import contract.model.Effector;
import contract.model.Sensor;
import utils.OrientationEnum;

public class CharacterImpl extends Observable implements Character, Runnable
{	
	private Boolean alive;
	private Integer score;
	private OrientationEnum orientation;
	private CaseCharacter currentCase;
	private boolean levelComplete;
	
	//Effectors
	private Effector effectorUp;
	private Effector effectorRight;
	private Effector effectorDown;
	private Effector effectorLeft;
	
	private Effector effectorStone;
	private Effector effectorExit;
	
	//Sensors
	private Sensor sensorPutrid;
	private Sensor sensorWindy;
	private Sensor sensorLight;
	private Sensor sensorDirections;
	
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
		consultPrologFile();
		while(true)
		{
			while(this.alive)
			{
				List<Integer> actions=new ArrayList<Integer>();
				Query q = new Query(new Compound("takeDecisions", new Term[] { new Variable("X")}));
				while (q.hasMoreSolutions())
				{
					Map<String, Term> action = q.nextSolution();
				    actions.add(Integer.valueOf(action.get("X").toString()));
				}
				
				for(Integer action: actions)
				{
					switch (action) 
					{
						case 0:
							System.out.println("Caillou");
							this.effectorStone.doIt();
							break;
			            case 1:
			            	System.out.println("Haut");
			            	this.effectorUp.doIt();     
			            	break;
			            case 2:
			            	System.out.println("Droite");
			            	this.effectorRight.doIt();
			                break;
			            case 3:
			            	System.out.println("Down");
			            	this.effectorDown.doIt();
			            	break;
			            case 4:
			            	System.out.println("Gauche");
			            	this.effectorLeft.doIt();
			                break;
			            default: 
			            	System.out.println("Erreur dans l'entier retourner par prolog");
			                break;
		            }
				}
			}
		}
	}
	
	public boolean consultPrologFile() {
		Query q1=new Query("consult", new Term[]{new Atom("Etat_Interne/ForetEnchantee.pl")});
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
		notifyObservers(new Object[]{"score", score});
		setChanged();
	}

	public OrientationEnum getOrientation()
	{
		return orientation;
	}

	public void setOrientation(OrientationEnum orientation)
	{
		this.orientation = orientation;
		notifyObservers(new Object[]{"orientation", orientation});
		setChanged();
	}

	public CaseCharacter getCurrentCase()
	{
		return currentCase;
	}

	public void setCurrentCase(CaseCharacter currentCase)
	{
		this.currentCase = currentCase;
		notifyObservers(currentCase.getCoords());
		setChanged();
		if(this.currentCase.isPortalPoint())
		{
			setLevelComplete(true);
		}
		notifyObservers(new Object[]{"case", currentCase});
		setChanged();
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

	public Effector getEffectorUp()
	{
		return effectorUp;
	}

	public void setEffectorUp(Effector effectorUp)
	{
		this.effectorUp = effectorUp;
	}

	public Effector getEffectorRight()
	{
		return effectorRight;
	}

	public void setEffectorRight(Effector effectorRight)
	{
		this.effectorRight = effectorRight;
	}

	public Effector getEffectorDown()
	{
		return effectorDown;
	}

	public void setEffectorDown(Effector effectorDown)
	{
		this.effectorDown = effectorDown;
	}

	public Effector getEffectorLeft()
	{
		return effectorLeft;
	}

	public void setEffectorLeft(Effector effectorLeft)
	{
		this.effectorLeft = effectorLeft;
	}

	public Effector getEffectorStone()
	{
		return effectorStone;
	}

	public void setEffectorStone(Effector effectorStone)
	{
		this.effectorStone = effectorStone;
	}

	public Effector getEffectorExit()
	{
		return effectorExit;
	}

	public void setEffectorExit(Effector effectorExit)
	{
		this.effectorExit = effectorExit;
	}

	public Sensor getSensorPutrid()
	{
		return sensorPutrid;
	}

	public void setSensorPutrid(Sensor sensorPutrid)
	{
		this.sensorPutrid = sensorPutrid;
	}

	public Sensor getSensorWindy()
	{
		return sensorWindy;
	}

	public void setSensorWindy(Sensor sensorWindy)
	{
		this.sensorWindy = sensorWindy;
	}

	public Sensor getSensorLight()
	{
		return sensorLight;
	}

	public void setSensorLight(Sensor sensorLight)
	{
		this.sensorLight = sensorLight;
	}

	public Sensor getSensorDirections()
	{
		return sensorDirections;
	}

	public void setSensorDirections(Sensor sensorDirections)
	{
		this.sensorDirections = sensorDirections;
	}	
}
