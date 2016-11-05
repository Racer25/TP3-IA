package contract.model;

public interface CaseMap 
{
	public boolean isSpawnPoint();
	public void setSpawnPoint(boolean spawnPoint);
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
	
	public int[] getCoordMap();
	public void setCoordMap(int[] coordCharacter);
	
	public int[] getCoordForCharacter();
	public void setCoordForCharacter(int[] coordCharacter);
}
