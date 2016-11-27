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
		//case attacked depends on the character orientation!!!
		 switch (character.getOrientation()) 
		 {
         case UP: 
        	 caseAttacked=(CaseCharacter) 
				map.getCasesMap()
				[character.getCurrentCase().getCoordForCharacter()[0]-1]
				[character.getCurrentCase().getCoordForCharacter()[1]];
        	 break;
         case RIGHT:  
        	 caseAttacked=(CaseCharacter) 
				map.getCasesMap()
				[character.getCurrentCase().getCoordForCharacter()[0]]
				[character.getCurrentCase().getCoordForCharacter()[1]+1];
        	 break;
         case DOWN:  
        	 caseAttacked=(CaseCharacter) 
				map.getCasesMap()
				[character.getCurrentCase().getCoordForCharacter()[0]+1]
				[character.getCurrentCase().getCoordForCharacter()[1]];
             break;
         case LEFT:  
        	 caseAttacked=(CaseCharacter) 
				map.getCasesMap()
				[character.getCurrentCase().getCoordForCharacter()[0]]
				[character.getCurrentCase().getCoordForCharacter()[1]-1];
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
