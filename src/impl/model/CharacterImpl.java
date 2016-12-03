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

import contract.model.AdventureMapCharacter;
import contract.model.CaseCharacter;
import contract.model.Character;
import contract.model.Effector;
import contract.model.Sensor;
import utils.DirectionEnum;
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
		this.alive=false;
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
				//MAJ case actuelle
				updateCurrentCase();
				if(this.currentCase.isPortalPoint())
				{
					this.setLevelComplete(true);
				}
				else
				{
					//Envoie des informations à prolog
					Query internalStateQuery = new Query(new Compound("update_internal_state", new Term[] 
							{
									new Atom(Integer.toString(this.currentCase.getCoords()[0])), 
									new Atom(Integer.toString(this.currentCase.getCoords()[1])),
									new Atom(Boolean.toString(this.currentCase.isPutrid())),
									new Atom(Boolean.toString(this.currentCase.isWindy())),
									new Atom(Boolean.toString(!this.currentCase.getPossibleDirections().get(DirectionEnum.RIGHT))),
									new Atom(Boolean.toString(!this.currentCase.getPossibleDirections().get(DirectionEnum.LEFT))),
									new Atom(Boolean.toString(!this.currentCase.getPossibleDirections().get(DirectionEnum.UP))),
									new Atom(Boolean.toString(!this.currentCase.getPossibleDirections().get(DirectionEnum.DOWN)))
							}));
					internalStateQuery.hasSolution();
					
					
					//Récupération des actions à réaliser
					List<Integer> actions=new ArrayList<Integer>();
					Query q = new Query(new Compound("takeDecisions", new Term[] { new Variable("Reponse")}));
					System.out.println("Envoi de requête takeDecisions");
					while (q.hasMoreSolutions())
					{
						Map<String, Term> action = q.nextSolution();
					    actions.add(Integer.valueOf(action.get("Reponse").toString()));
					    System.out.println("ajout de l'action: "+action);
					}
					
					//Réalisation des actions
					for(Integer action: actions)
					{
						System.out.println("action: "+action);
						switch (action) 
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
				            	System.out.println("Erreur dans l'entier retourner par prolog");
				                break;
			            }
					}
				}
			}
			if(!this.alive)
			{
				Query resetQuery=new Query("raz_internal_state", new Term[]{});
				resetQuery.hasSolution();
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
	
	private void updateCurrentCase()
	{
		this.currentCase.setPortalPoint((Boolean) this.sensorLight.answer());
		this.currentCase.setPutrid((Boolean) this.sensorPutrid.answer());
		this.currentCase.setWindy((Boolean) this.sensorWindy.answer());
		this.currentCase.setPossibleDirections((HashMap<DirectionEnum, Boolean>) this.sensorDirections.answer());
	}
}
