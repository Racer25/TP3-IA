package impl.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import contract.model.AdventureMap;
import contract.view.Window;
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
		JPanel center1=new JPanel();
		this.getContentPane().add(center1, BorderLayout.CENTER);
		center1.setLayout(new GridBagLayout());
		
		GridBagConstraints mapConstraints = new GridBagConstraints();
		mapConstraints.gridx = 0;
		mapConstraints.gridy = 0;
		//mapConstraints.insets = new Insets(10,10,20,20);
		
		AdventureMapViewImpl map=new AdventureMapViewImpl(myMap, character);
		
		//Rajouté eugé
		map.setPreferredSize(new Dimension(500,500));
		map.setMinimumSize(new Dimension(300,300));
		
		center1.add(map,mapConstraints);
		
		JPanel rigth=new JPanel();
		this.getContentPane().add(rigth, BorderLayout.EAST);
		rigth.setLayout(new GridLayout(3, 1));
		
		JPanel stats =new JPanel();
		stats.setLayout(new GridLayout(2, 1));
		
		//Panel healthbar
		JPanel life=new JPanel();
		JLabel heart=new JLabel();
		
		JPanel score=new JPanel();
		
		stats.add(life);
		stats.add(score);
		
		JPanel actions=new JPanel();
		actions.setLayout(new GridLayout(2, 1));
		JLabel instructions= new JLabel("Click on MOVE to resolve the level");
		JPanel panelButton=new JPanel();
		panelButton.setLayout(new GridBagLayout());
		JButton buttonMove=new JButton("MOVE");
		panelButton.add(buttonMove);
		
		actions.add(instructions);
		actions.add(panelButton);
		
		JPanel faceView=new JPanel();
		ImageIcon img= new ImageIcon("img/giphy.gif");
		img=new ImageIcon(img.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		JLabel gifLabel=new JLabel(img);
		faceView.add(gifLabel);
		
		
		rigth.add(stats);
		rigth.add(actions);
		rigth.add(faceView);
		
		
		this.setVisible(true);
		
	}
}
