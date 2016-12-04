package impl.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import contract.view.LifeView;

public class LifeViewImpl extends JPanel implements LifeView, Observer
{
	private static final long serialVersionUID = -249419079691313905L;

	private JPanel part1;
	private JPanel part2;
	private JPanel part3;
	private JPanel part4;

	public LifeViewImpl()
	{
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 3, Color.BLACK));

		JLabel heart = new JLabel();
		ImageIcon heartIcon = new ImageIcon("img/heart.jpg");
		heart.setIcon(heartIcon);
		heart.setBackground(Color.WHITE);
		GridBagConstraints heartConstraints = new GridBagConstraints();
		heartConstraints.gridx = 0;
		heartConstraints.gridy = 0;

		JPanel healthbar = new JPanel();
		healthbar.setPreferredSize(new Dimension(200, 20));
		healthbar.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		healthbar.setLayout(new GridLayout(1, 4));

		this.part1 = new JPanel();
		this.part2 = new JPanel();
		this.part3 = new JPanel();
		this.part4 = new JPanel();
		this.part1.setBackground(Color.GREEN);
		this.part2.setBackground(Color.GREEN);
		this.part3.setBackground(Color.GREEN);
		this.part4.setBackground(Color.GREEN);

		healthbar.add(this.part1);
		healthbar.add(this.part2);
		healthbar.add(this.part3);
		healthbar.add(this.part4);

		this.add(heart);
		this.add(healthbar);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (arg instanceof Object[])
		{
			Object[] object=(Object[])arg;
			if(object[0].equals("alive"))
			{
				//The Character warned me that he's alive or not
				if ((Boolean) object[1])
				{
					//The Character warned me that he's alive
					part1.setBackground(Color.GREEN);
					part2.setBackground(Color.GREEN);
					part3.setBackground(Color.GREEN);
					part4.setBackground(Color.GREEN);
				} 
				else
				{
					//The Character warned me that he's dead
					Runnable die = new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								part1.setBackground(Color.YELLOW);
								part2.setBackground(Color.YELLOW);
								part3.setBackground(Color.YELLOW);
								part4.setBackground(Color.WHITE);
		
								Thread.sleep(500);
		
								part1.setBackground(Color.ORANGE);
								part2.setBackground(Color.ORANGE);
								part3.setBackground(Color.WHITE);
		
								Thread.sleep(500);
		
								part1.setBackground(Color.RED);
								part2.setBackground(Color.WHITE);
		
								Thread.sleep(500);
		
								part1.setBackground(Color.WHITE);
							} 
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					};
					new Thread (die).start();

				}
			}
		}
	}

}
