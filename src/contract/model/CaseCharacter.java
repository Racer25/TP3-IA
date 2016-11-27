package contract.model;

public interface CaseCharacter
{
	public boolean isSpawnPoint();
	public boolean isPortalPoint();
	public boolean isPutrid();
	public boolean isMonstruous();
	public void setMonstruous(boolean monstruous);
	public boolean isWindy();
	public boolean isFall();
	
	public int[] getCoordForCharacter();
	public void setCoordForCharacter(int[] coordCharacter);
}
