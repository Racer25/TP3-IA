package app;

import contract.model.AdventureMap;
import contract.model.Character;
import impl.model.CharacterImpl;

public class MainClass
{

	public static void main(String[] args)
	{
		AdventureMap myMap=null;
		Character character=new CharacterImpl();
		new Thread ((CharacterImpl) character).start();
		//tretretert
	}

}
