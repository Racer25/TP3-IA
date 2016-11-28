package impl.model;

import java.util.HashMap;
import java.util.Observable;

import contract.model.CaseCharacter;
import contract.model.CaseMap;
import utils.DirectionEnum;

public class CaseMapImpl extends Observable implements CaseMap
{
	//Independant attributes between them
	private boolean spawnPoint;
	private boolean portalPoint;
	private boolean monstruous;
	private boolean fall;
	
	//Combinable attributes
	private boolean putrid;
	private boolean windy;
	
	public CaseMapImpl()
	{
		//Lors de la creation de la case,  on set tous les booleens a false
		this.spawnPoint = false;
		this.portalPoint = false;
		this.monstruous = false;
		this.fall = false;
		this.putrid = false;
		this.windy = false;
	}
	
	//Give possible directions
	private HashMap<DirectionEnum, Boolean> possibleDirections;
	//key: UP/RIGHT/DOWN/LEFT -> value:true
	
	private int[] coordMap;
	private int[] coordForCharacter;
	
	public boolean isSpawnPoint()
	{
		return spawnPoint;
	}
	public void setSpawnPoint(boolean spawnPoint)
	{
		this.spawnPoint = spawnPoint;
	}
	public boolean isPortalPoint()
	{
		return portalPoint;
	}
	public void setPortalPoint(boolean portalPoint)
	{
		this.portalPoint = portalPoint;
	}
	public boolean isPutrid()
	{
		return putrid;
	}
	public void setPutrid(boolean putrid)
	{
		this.putrid = putrid;
		notifyObservers(putrid);
		setChanged();
	}
	public boolean isMonstruous()
	{
		return monstruous;
	}
	public void setMonstruous(boolean monstruous)
	{
		this.monstruous = monstruous;
		notifyObservers(monstruous);
		setChanged();
	}
	public boolean isWindy()
	{
		return windy;
	}
	public void setWindy(boolean windy)
	{
		this.windy = windy;
	}
	public boolean isFall()
	{
		return fall;
	}
	public void setFall(boolean fall)
	{
		this.fall = fall;
	}
	public int[] getCoordMap()
	{
		return coordMap;
	}
	public void setCoordMap(int[] coordMap)
	{
		this.coordMap = coordMap;
	}
	public int[] getCoordForCharacter()
	{
		return coordForCharacter;
	}
	public void setCoordForCharacter(int[] coordForCharacter)
	{
		this.coordForCharacter = coordForCharacter;
	}
	public HashMap<DirectionEnum, Boolean> getPossibleDirections()
	{
		return possibleDirections;
	}
	public void setPossibleDirections(HashMap<DirectionEnum, Boolean> possibleDirections)
	{
		this.possibleDirections = possibleDirections;
	}
}
