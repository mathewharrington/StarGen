import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import star.Star;

public class Stars extends JPanel {

	private Star[][] stars;
	
	public Stars(Star[][] stars) {
		this.stars = stars;
		this.setVisible(false);
		this.setBackground(new Color(19,22,24));
	}
	
	public void printStarsToConsole()
	{
		for(int i = 0; i < stars.length; i++)
		{
			for(int j = 0; j < stars[i].length; j++)
			{
				if(stars[i][j].isVisible())
					System.out.print(".");
				else 
					System.out.print(" ");
			}
			System.out.print("\n");
		}
	}
	
	public void paintStars()
	{
		this.paint(this.getGraphics());
	}
	
	@Override 
	public void paint(Graphics g)
	{
		super.paint(g);
		
		//g.setColor(Color.WHITE);
		for(int i = 0 ; i < stars.length; i++)
		{
			for(int j = 0; j < stars[i].length; j++)
			{
				if(stars[i][j].isVisible())
				{
					g.setColor(stars[i][j].getColor());
					if(stars[i][j].getSize() == 1)
						g.drawLine(i, j, i, j);
					else
						g.fillOval(i, j, stars[i][j].getSize(), stars[i][j].getSize());
				}
			}
		}
		this.setVisible(true);
	}
}
