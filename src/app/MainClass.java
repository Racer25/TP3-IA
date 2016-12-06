package app;

import java.util.Observer;

import contract.model.LevelHandler;
import impl.model.CharacterImpl;
import impl.model.LevelHandlerImpl;
import impl.view.WindowImpl;

public class MainClass
{

	public static void main(String[] args)
	{
		CharacterImpl character=new CharacterImpl();
		LevelHandler levelHandler=new LevelHandlerImpl(character);
		//LevelHandler observe character
		character.addObserver((Observer) levelHandler);
		new WindowImpl(levelHandler.getGenerator(), character);
		new Thread ((CharacterImpl) character).start();
	}

}
