package impl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.jpl7.Atom;
import org.jpl7.Compound;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import contract.model.CaseCharacter;
import contract.model.Character;
import contract.model.Effector;
import contract.model.Sensor;
import utils.DirectionEnum;
import utils.OrientationEnum;

public class CharacterImpl extends Observable implements Character, Runnable
{	
	private Boolean alive;
	private Boolean active;
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
		this.active=false;
		this.score=0;
		this.orientation=OrientationEnum.RIGHT;
		this.currentCase=null;
		this.setLevelComplete(false);
	}

	@Override
	public void run()
	{
		//Consult the prolog file (just once)
		consultPrologFile();
		while(true)
		{
			//Actualisation every 0.1 second
			wait(100);
			while(this.alive && this.active && !this.levelComplete)
			{
				//Actualisation frequency
				wait(200);
				
				//Observe my environnement
				ObserveMyCase();
				
				//Am I arrived??
				if(this.currentCase.isPortalPoint())
				{
					this.setLevelComplete(true);
					System.out.println("The exit, finally!!");
				}
				//I'm not arrived...
				else
				{
					//Send current case informations to prolog
					updateInternalStateInProlog();					
					
					//Obtain a list of actions (1 integer=1 action) by searching the best way
					//to win points
					List<Integer> actions=chooseActionsToRealiseFromPrologState();
					
					//Do the actions
					realiseActions(actions);
				}
			}

		}
	}
	
	public void consultPrologFile() {
		Query q1=new Query("consult", new Term[]{new Atom("Etat_Interne/ForetEnchantee.pl")});
		q1.hasSolution();
	}
	

	private void ObserveMyCase()
	{
		this.currentCase.setPortalPoint((Boolean) this.sensorLight.answer());
		this.currentCase.setPutrid((Boolean) this.sensorPutrid.answer());
		this.currentCase.setWindy((Boolean) this.sensorWindy.answer());
		this.currentCase.setPossibleDirections((HashMap<DirectionEnum, Boolean>) this.sensorDirections.answer());
	}
	
	public void updateInternalStateInProlog()
	{
		//Envoie des informations à prolog
		Query internalStateQuery = new Query(new Compound("update_internal_state", new Term[] 
				{
						new org.jpl7.Integer(this.currentCase.getCoords()[0]), 
						new org.jpl7.Integer(this.currentCase.getCoords()[1]),
						new Atom(Boolean.toString(this.currentCase.isPutrid())),
						new Atom(Boolean.toString(this.currentCase.isWindy())),
						new Atom(Boolean.toString(!this.currentCase.getPossibleDirections().get(DirectionEnum.RIGHT))),
						new Atom(Boolean.toString(!this.currentCase.getPossibleDirections().get(DirectionEnum.LEFT))),
						new Atom(Boolean.toString(!this.currentCase.getPossibleDirections().get(DirectionEnum.UP))),
						new Atom(Boolean.toString(!this.currentCase.getPossibleDirections().get(DirectionEnum.DOWN)))
				}));
		internalStateQuery.hasSolution();
		internalStateQuery.close();
	}
	
	public List<Integer> chooseActionsToRealiseFromPrologState()
	{
		List<Integer> actions=new ArrayList<Integer>();
		
		Query q = new Query(new Compound("takeDecisions", new Term[] { new Variable("Reponse")}));

		System.out.println("Envoi de requête takeDecisions");
		while(q.hasMoreSolutions())
		{
			Map<String, Term> actionList = q.nextSolution();
			for (Term action : actionList.get("Reponse").args()) 
			{
				if(action.isInteger())
				{
					if(!action.toString().equals("'[]'"))
					{
						actions.add(Integer.valueOf(action.toString()));
					    System.out.println("ajout de l'action: "+action);
					}
				}
				//format du certains termes de la liste: '[|]'(4, '[]').....
				else if(!action.toString().equals("'[]'"))
				{
					String maString=action.toString().substring(6, action.toString().length());
					actions.add(transformToInteger(maString));
					System.out.println("ajout de l'action: "+transformToInteger(maString));
				}
			}
		}
		q.close();
		return actions;
	}
	
	private void realiseActions(List<Integer> actions)
	{
		int k=0;
		while(k<actions.size() && this.alive && !this.levelComplete && this.active)
		{
			System.out.println("action: "+actions.get(k));
			switch (actions.get(k)) 
			{
	            case 1:
	            	System.out.println("Haut");
	            	this.setOrientation(OrientationEnum.UP);
	            	this.effectorUp.doIt();
	            	break;
	            case 2:
	            	System.out.println("Droite");
	            	this.setOrientation(OrientationEnum.RIGHT);
	            	this.effectorRight.doIt();
	                break;
	            case 3:
	            	System.out.println("Down");
	            	this.setOrientation(OrientationEnum.DOWN);
	            	this.effectorDown.doIt();
	            	break;
	            case 4:
	            	System.out.println("Gauche");
	            	this.setOrientation(OrientationEnum.LEFT);
	            	this.effectorLeft.doIt();
	                break;
	            case 5:
					System.out.println("CaillouHaut");
					this.setOrientation(OrientationEnum.UP);
					this.effectorStone.doIt();
					break;
	            case 6:
					System.out.println("CaillouDroite");
					this.setOrientation(OrientationEnum.RIGHT);
					this.effectorStone.doIt();
					break;
	            case 7:
					System.out.println("CaillouBas");
					this.setOrientation(OrientationEnum.DOWN);
					this.effectorStone.doIt();
					break;
	            case 8:
					System.out.println("CaillouGauche");
					this.setOrientation(OrientationEnum.LEFT);
					this.effectorStone.doIt();
					break;
	            default: 
	            	System.out.println("Erreur dans l'entier retourné par prolog");
	                break;
            }
			wait(200);
			k++;
		}
	}
	
	private Integer transformToInteger(String maString)
	{
		Integer res= null;
		try
		{
			res=Integer.valueOf(maString);
		}
		catch(NumberFormatException e)
		{
			return transformToInteger(maString.substring(0, maString.length()-1));
		}
		return res;
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

	public Boolean isActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
		notifyObservers(new Object[]{"active", active});
		setChanged();
	}
	
	private void wait(int timeInMilleseconds)
	{
		try
		{
			Thread.sleep(timeInMilleseconds);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
