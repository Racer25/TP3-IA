package impl.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import contract.model.AdventureMap;
import contract.view.LifeView;
import contract.view.ScoreView;
import contract.view.Window;
import impl.controller.MoveButtonListenerImpl;
import impl.model.CharacterImpl;

public class WindowImpl extends JFrame implements Window
{
	private static final long serialVersionUID = -7191389537408309634L;
	
	public WindowImpl(AdventureMap myMap, CharacterImpl character)
	{
		//Our Window
		this.setTitle("Adventure Guy");
		this.setLayout(new BorderLayout());
		this.setResizable(true);
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Image icon=Toolkit.getDefaultToolkit().getImage("img/aspi.jpg");
		//this.setIconImage(icon);
		
		//BIG CENTERED PANEL
		JPanel center1=new JPanel()
		{
			private static final long serialVersionUID = -1206593738493049332L;

			@Override
			protected void paintComponent(Graphics g) 
			{
				super.paintComponent(g);
				Image background = null;
				try {
					background = ImageIO.read(new File("img/herbe.jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
			}
		};
		center1.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
		center1.setBackground(Color.BLACK);
		this.getContentPane().add(center1, BorderLayout.CENTER);
		center1.setLayout(new GridBagLayout());
		
		//We add the map to the panel center1
		GridBagConstraints mapConstraints = new GridBagConstraints();
		mapConstraints.gridx = 0;
		mapConstraints.gridy = 0;
		//mapConstraints.insets = new Insets(10,10,20,20);
		AdventureMapViewImpl map=new AdventureMapViewImpl(myMap, character);
		map.setBackground(Color.BLACK);
		map.setPreferredSize(new Dimension(500,500));
		map.setMinimumSize(new Dimension(250,250));
		center1.add(map,mapConstraints);
		
		//RIGHT PANEL
		JPanel right=new JPanel();
		right.setBackground(Color.BLACK);
		GridBagConstraints rightConstraints = new GridBagConstraints();
		rightConstraints.gridx = 1;
		rightConstraints.gridy = 0;
		right.setPreferredSize(new Dimension(300,500));
		map.setMinimumSize(new Dimension(150,250));
		center1.add(right);
		right.setLayout(new GridLayout(3, 1));
		
		JPanel stats =new JPanel();
		stats.setBackground(Color.WHITE);
		stats.setLayout(new GridLayout(2, 1));
		
		//Panel healthbar
		LifeView life=new LifeViewImpl();
		character.addObserver((LifeViewImpl) life);
		
		//Font
		Font font = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,new File("./fonts/Pixeled.ttf"));
			ge.registerFont(font);
			font = font.deriveFont(Font.TRUETYPE_FONT,10);
		} 
		catch (FontFormatException | IOException e2) 
		{
			e2.printStackTrace();
		}
		
		//panel score
		/*****************************/
		ScoreView score=new ScoreViewImpl(font);
		character.addObserver((ScoreViewImpl)score);
		
		stats.add((LifeViewImpl)life);
		stats.add((ScoreViewImpl) score);
		
		JPanel actions=new JPanel();
		actions.setBackground(Color.WHITE);
		actions.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.BLACK));
		actions.setLayout(new GridLayout(2,1));
		JPanel instructions=new JPanel();
		instructions.setBackground(Color.WHITE);
		instructions.setLayout(new GridBagLayout());
		JLabel instructions1= new JLabel("CLICK ON MOVE TO");
		instructions1.setHorizontalAlignment(0);
		GridBagConstraints instructions1Constraints = new GridBagConstraints();
		instructions1Constraints.gridx = 0;
		instructions1Constraints.gridy = 0;
		JLabel instructions2= new JLabel("RESOLVE THE LEVEL !");
		instructions2.setHorizontalAlignment(0);
		GridBagConstraints instructions2Constraints = new GridBagConstraints();
		instructions2Constraints.gridx = 0;
		instructions2Constraints.gridy = 1;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,new File("./fonts/Pixeled.ttf"));
			ge.registerFont(font);
			font = font.deriveFont(Font.TRUETYPE_FONT,10);
			instructions1.setFont(font);
			instructions2.setFont(font);
		} 
		catch (FontFormatException | IOException e2) 
		{
			e2.printStackTrace();
		}
		JPanel panelButton=new JPanel();
		panelButton.setLayout(new GridBagLayout());
		panelButton.setBackground(Color.WHITE);
		JButton buttonMove=new JButton("MOVE");
		buttonMove.addActionListener(new MoveButtonListenerImpl(character));
		panelButton.add(buttonMove);
		
		instructions.add(instructions1,instructions1Constraints);
		instructions.add(instructions2,instructions2Constraints);
		actions.add(instructions);
		actions.add(panelButton);
		
		JPanel faceView=new JPanel();
		faceView.setBackground(Color.WHITE);
		faceView.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 3, Color.BLACK));
		ImageIcon img= new ImageIcon("img/walk.gif");
		img=new ImageIcon(img.getImage().getScaledInstance(250, 150, Image.SCALE_DEFAULT));
		JLabel gifLabel=new JLabel(img);
		gifLabel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		faceView.add(gifLabel);
		
		
		right.add(stats);
		right.add(actions);
		right.add(faceView);
		
		
		this.setVisible(true);
		
	}
}
