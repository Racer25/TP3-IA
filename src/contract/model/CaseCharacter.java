package contract.model;

import java.util.HashMap;

import utils.DirectionEnum;

public interface CaseCharacter
{
	public boolean isPortalPoint();
	public void setPortalPoint(boolean portalPoint);
	public boolean isPutrid();
	public void setPutrid(boolean putrid);
	public boolean isMonstruous();
	public void setMonstruous(boolean monstruous);
	public boolean isWindy();
	public void setWindy(boolean windy);
	public boolean isFall();
	public void setFall(boolean fall);
	public HashMap<DirectionEnum, Boolean> getPossibleDirections();
	public void setPossibleDirections(HashMap<DirectionEnum, Boolean> directions);
	
	public int[] getCoords();
}
