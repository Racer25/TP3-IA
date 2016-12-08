package impl.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import contract.view.ScoreView;

public class ScoreViewImpl extends JPanel implements ScoreView, Observer
{
	private static final long serialVersionUID = 6606535453973182324L;
	
	private JLabel scorebar;
	
	public ScoreViewImpl(Font font)
	{
		this.scorebar=new JLabel("0");
		this.scorebar.setFont(font);
		this.scorebar.setPreferredSize(new Dimension(200,20));
		this.scorebar.setBackground(Color.WHITE);
		GridBagConstraints scorebarConstraints = new GridBagConstraints();
		scorebarConstraints.gridx = 1;
		scorebarConstraints.gridy = 0;
		
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.BLACK));
		
		JLabel cup=new JLabel();
		ImageIcon cupIcon = new ImageIcon("img/cup.jpg");
		cup.setIcon(cupIcon);
		cup.setBackground(Color.WHITE);
		GridBagConstraints cupConstraints = new GridBagConstraints();
		cupConstraints.gridx = 0;
		cupConstraints.gridy = 0;
		
		
		
		this.add(cup);
		this.add(this.scorebar);
	}

	public void setScore(int score)
	{
		this.scorebar.setText(Integer.toString(score));
	}

	@Override
	public void update(Observable o, Object arg)
	{
		//The Character warned me that the score is changing
		if(arg instanceof Integer)
		{
			this.setScore((Integer) arg);
			this.repaint();
			this.revalidate();
		}
	}

}
