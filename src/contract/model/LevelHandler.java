package contract.model;

public interface LevelHandler
{
	public int getLevel();
	public void setLevel(int level);
	
	public AdventureMapGenerator getGenerator();
	public void setGenerator(AdventureMapGenerator generator);
}
