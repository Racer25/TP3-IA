package impl.model.effector;

import contract.model.AdventureMap;
import contract.model.CaseCharacter;
import contract.model.Effector;
import impl.model.CharacterImpl;

public class EffectorStoneImpl implements Effector
{
	private CharacterImpl character;
	private AdventureMap map;
	
	public EffectorStoneImpl(CharacterImpl character, AdventureMap map)
	{
		this.character=character;
		this.map=map;
	}

	@Override
	public void doIt()
	{
		CaseCharacter caseAttacked = null;
		int lineCurrentCase=character.getCurrentCase().getCoords()[0]+map.getChangeReference()[0];
		int columnCurrentCase=character.getCurrentCase().getCoords()[1]+map.getChangeReference()[1];
		//case attacked depends on the character orientation!!!
		 switch (character.getOrientation()) 
		 {
         case UP: 
        	 caseAttacked=(CaseCharacter) 
        			 map.getCasesMap()
     				[lineCurrentCase-1]
     				[columnCurrentCase];
        	 break;
         case RIGHT:  
        	 caseAttacked=(CaseCharacter) 
        			 map.getCasesMap()
        			 [lineCurrentCase]
        				[columnCurrentCase+1];
        	 break;
         case DOWN:  
        	 caseAttacked=(CaseCharacter) 
        			 map.getCasesMap()
        			 [lineCurrentCase+1]
        	     	[columnCurrentCase];
             break;
         case LEFT:  
        	 caseAttacked=(CaseCharacter) 
        			 map.getCasesMap()
        			 [lineCurrentCase]
        	     	[columnCurrentCase-1];
        	 break;
         default:
        	 System.err.println("Bad Orientation");
             break;
             }
		 if(caseAttacked!=null)
		 {
			 caseAttacked.setMonstruous(false);
		 }
	}
}
