package contract.model;

public interface Character
{	
	//Change currentCase and update memory of the agent
	public void move(String direction);
	
	public Boolean isAlive();
	public void setAlive(Boolean alive);
	public int getScore();
	public void setScore(int score);
	public Integer getOrientation();
	public void setOrientation(Integer orientation);
	public CaseCharacter getCurrentCase();
	public void setCurrentCase(CaseCharacter currentCase);
}
