package contract.model;

import utils.OrientationEnum;

public interface Character
{	
	//Change currentCase and update memory of the agent	
	public Boolean isAlive();
	public void setAlive(Boolean alive);
	public int getScore();
	public void setScore(int score);
	public OrientationEnum getOrientation();
	public void setOrientation(OrientationEnum orientation);
	public CaseCharacter getCurrentCase();
	public void setCurrentCase(CaseCharacter currentCase);
	
	public boolean consultPrologFile();
}
