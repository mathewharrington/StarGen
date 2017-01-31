package application;
import java.awt.Graphics;

import javax.swing.JPanel;

import star.Star;
import star.StarAttribute;

public class Sky extends JPanel {

	private Star[][] stars;
	
	public Sky(Star[][] stars) {
		this.stars = stars;
		this.setVisible(false);
		this.setBackground(StarAttribute.SKY_COLOR);
	}
	
	/**
	 * Used in the first few rounds of generation for testing.
	 */
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
	/**
	 * Draws the stars in the JPanel, smallest stars (size of 1) are drawn with
	 * drawLine(), the rest or drawn with fillOval().
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		
		for(int i = 0 ; i < stars.length; i++)
		{
			for(int j = 0; j < stars[i].length; j++)
			{
				if(stars[i][j].isVisible())
				{
					g.setColor(stars[i][j].getColor());
					if(stars[i][j].getSize() == StarAttribute.SMALL_SIZE)
						g.drawLine(i, j, i, j);
					else
						g.fillOval(i, j, stars[i][j].getSize(), stars[i][j].getSize());
				}
			}
		}
		this.setVisible(true);
	}
}
