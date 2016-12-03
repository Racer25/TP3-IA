package impl.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

import impl.model.CharacterImpl;
import utils.OrientationEnum;

public class CharacterViewImpl extends ImageIcon implements Observer{

	private static final long serialVersionUID = 8666019746918414661L;
	private CharacterImpl character;
	private String path;
	
	CharacterViewImpl(CharacterImpl character)
	{
		this.character = character;
		character.addObserver(this);
		if(character.getOrientation()==OrientationEnum.DOWN)
		{
			path = "img/stevedown.png";
		}
		else if(character.getOrientation()==OrientationEnum.UP)
		{
			path = "img/steveup.png";
		}
		else if(character.getOrientation()==OrientationEnum.RIGHT)
		{
			path = "img/steveright.png";
		}
		else if(character.getOrientation()==OrientationEnum.LEFT)
		{
			path = "img/steveleft.png";
		}
		Image image =  Toolkit.getDefaultToolkit().getImage(path);
		this.setImage(image);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg0 instanceof CharacterImpl)
		{
			if(arg1 instanceof Object[])
			{
				Object[] object=(Object[])arg1;
				if(object[0].equals("orientation"))
				{
					if(object[1]==OrientationEnum.DOWN)
					{
						path = "img/stevedown.png";
					}
					else if(object[1]==OrientationEnum.UP)
					{
						path = "img/steveup.png";
					}
					else if(object[1]==OrientationEnum.RIGHT)
					{
						path = "img/steveright.png";
					}
					else if(object[1]==OrientationEnum.LEFT)
					{
						path = "img/steveleft.png";
					}
					Image image =  Toolkit.getDefaultToolkit().getImage(path);
					this.setImage(image);
				}
			}
		}
	}
	
}
