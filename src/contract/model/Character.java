package contract.model;

public interface Character
{	
	//Change currentCase and update memory of the agent
	public void move(String direction);
	
	public boolean isAlive();
	public void setAlive(boolean alive);
	public int getScore();
	public void setScore(int score);
	public String getOrientation();
	public void setOrientation(String orientation);
	public CaseCharacter getCurrentCase();
	public void setCurrentCase(CaseCharacter currentCase);
}
