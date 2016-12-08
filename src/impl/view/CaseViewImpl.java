package impl.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import contract.model.CaseMap;
import contract.view.CaseView;
import impl.model.CaseMapImpl;
import impl.model.CharacterImpl;
import utils.OrientationEnum;

public class CaseViewImpl extends JPanel implements CaseView
{
	private static final long serialVersionUID = -3853422303860566481L;
	
	//Independant attributes between them
	private boolean spawnPoint;
	private boolean portalPoint;
	private boolean monstruous;
	private boolean fall;
	private boolean portal;
	private boolean character;
	private CharacterViewImpl characterView;
	
	//Combinable attributes
	private boolean putrid;
	private boolean windy;
	
	private JLabel label;

	public CaseViewImpl(CaseMap caseMap, CharacterViewImpl characterView)
	{
		//Creation with caseMap attributes
		fall = caseMap.isFall();
		monstruous = caseMap.isMonstruous();
		portal = caseMap.isPortalPoint();
		windy = caseMap.isWindy();
		this.characterView = characterView;
		this.setBackground(Color.WHITE);
		this.setSize(50, 50);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));	
		label = new JLabel(this.characterView);
		this.add(label);
		label.setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Image background = null;
		try {
			if(windy)
			{
				if(monstruous)
				{
					background = ImageIO.read(new File("img/zombiewind.jpg"));
				}
				else if(fall)
				{
					background = ImageIO.read(new File("img/fall.jpg"));
				}
				else if(portal)
				{
					background = ImageIO.read(new File("img/portalwind.jpg"));
				}
				else
				{
					background = ImageIO.read(new File("img/bedrockwind.jpg"));
				}
			}
			else if(monstruous)
			{
				background = ImageIO.read(new File("img/zombie.jpg"));
			}
			else if(fall)
			{
				background = ImageIO.read(new File("img/fall.jpg"));
			}
			else if(portal)
			{
				background = ImageIO.read(new File("img/portal.jpg"));
			}
			else
			{
				background = ImageIO.read(new File("img/bedrock.jpg"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void setCharacterVisible(boolean visible)
	{
		label.setVisible(visible);
	}
	
	public void setMonstruous(boolean monstruous) {
		this.monstruous = monstruous;
	}

}
