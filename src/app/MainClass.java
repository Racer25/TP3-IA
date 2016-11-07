package app;

import contract.model.AdventureMap;
import contract.model.Character;
import contract.view.Window;
import impl.model.CharacterImpl;
import impl.view.WindowImpl;

public class MainClass
{

	public static void main(String[] args)
	{
		AdventureMap myMap=null;
		CharacterImpl character=new CharacterImpl();
		Window window=new WindowImpl(myMap, character);
		new Thread ((CharacterImpl) character).start();
	}

}
