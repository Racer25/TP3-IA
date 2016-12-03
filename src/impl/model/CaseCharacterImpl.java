package impl.model;

import java.util.HashMap;

import contract.model.CaseCharacter;
import utils.DirectionEnum;

public class CaseCharacterImpl implements CaseCharacter
{
	//Coord
	private int[] coords;
	
	//Independant attributes between them
	private boolean spawnPoint;
	private boolean portalPoint;
	private boolean monstruous;
	private boolean fall;
	//Combinable attributes
	private boolean putrid;
	private boolean windy;
	
	//Give possible directions
	private HashMap<DirectionEnum, Boolean> possibleDirections;
	//key: UP/RIGHT/DOWN/LEFT -> value:true
	
	public CaseCharacterImpl(
			int[] coords,
			boolean spawnPoint, 
			boolean portalPoint, 
			boolean monstruous, 
			boolean fall,
			boolean putrid,
			boolean windy,
			HashMap<DirectionEnum, Boolean> possibleDirections)
	{
		this.coords=coords;
		this.spawnPoint=spawnPoint;
		this.portalPoint=portalPoint;
		this.monstruous=monstruous;
		this.fall=fall;
		this.putrid=putrid;
		this.windy=windy;
		this.possibleDirections=possibleDirections;
	}
	
	public boolean isMonstruous()
	{
		return monstruous;
	}
	public void setMonstruous(boolean monstruous)
	{
		this.monstruous = monstruous;
	}
	public int[] getCoords()
	{
		return coords;
	}
	public boolean isPortalPoint()
	{
		return portalPoint;
	}

	public boolean isFall()
	{
		return fall;
	}
	
	public boolean isPutrid()
	{
		return putrid;
	}

	public boolean isWindy()
	{
		return windy;
	}

	public HashMap<DirectionEnum, Boolean> getPossibleDirections()
	{
		return possibleDirections;
	}

	public boolean isSpawnPoint()
	{
		return spawnPoint;
	}

	public void setSpawnPoint(boolean spawnPoint)
	{
		this.spawnPoint = spawnPoint;
	}
	public void setCoords(int[] coords)
	{
		this.coords = coords;
	}
	
	public void setPortalPoint(boolean portalPoint)
	{
		this.portalPoint = portalPoint;
	}

	public void setFall(boolean fall)
	{
		this.fall = fall;
	}

	public void setPutrid(boolean putrid)
	{
		this.putrid = putrid;
	}

	public void setWindy(boolean windy)
	{
		this.windy = windy;
	}

	public void setPossibleDirections(HashMap<DirectionEnum, Boolean> possibleDirections)
	{
		this.possibleDirections = possibleDirections;
	}

}
