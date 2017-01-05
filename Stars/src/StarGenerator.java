import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

import star.Star;
import star.StarAttribute;

public class StarGenerator {

	private static Star[][] STARS;
	private static OpenSimplexNoise NOISE;
	private static int MAX;
	
	public StarGenerator(int rows, int columns) {
		STARS = new Star[rows][columns];
		NOISE = new OpenSimplexNoise();
		MAX = (rows > columns) ? rows : columns;
		
		initSky();
	}
	
	/**
	 * Initialises the sky with new stars.
	 */
	private void initSky()
	{
		for(int i = 0; i < STARS.length; i++)
		{
			for(int j = 0; j < STARS[i].length; j++)
			{
				STARS[i][j] = new Star();
			}
		}
	}
	
	/**
	 * Generates the initial layer of stars, these are larger and brighter.
	 */
	public void generateInitial()
	{
		generateStars(StarAttribute.INITIAL_PROB, StarAttribute.LARGE_SIZE, StarAttribute.LARGE_COLOR);
	}
	
	
	/**
	 * Generates a 'dusting' of stars, these are smaller and more frequently 
	 * occuring that the larger ones, designed to be used as the final round of
	 * generation.
	 */
	public void generateDusting()
	{
		generateStars(StarAttribute.DUSTING_PROB, StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
	}
	
	
	/**
	 * Uses simplex noise to generate 'stars', if noise value is less than
	 * given desnisty value we turn make it visible. First round generated stars
	 * are set to a slightly larger size, other smaller stars will be generated 
	 * around these larger ones.
	 * 
	 * @param probability double between -1 and 1, lower = less probability.
	 */
	public void generateStars(double probability, int size, Color color)
	{	
		for(int i = 0; i < STARS.length; i++)
		{
			for(int j = 0; j < STARS[i].length; j++)
			{
				double d = ThreadLocalRandom.current().nextInt(0, MAX + 1);
				double n = NOISE.eval(i, j, d);
				n = round(n, 2);
				if(n < probability)
				{
					initStar(STARS[i][j], size, color);
				}
			}
		}
	}
	
	
	/**
	 * Attempts to generate clusters of stars be using simplex noise to add more
	 * stars surrounding an initial star. A temporary copy of the star array is
	 * used to iterate and additions are added directly to the original star
	 * array.
	 * 
	 * @param density integer representing the required density, how close to 
	 * the initial star the next should be formed.
	 */
	public void generateCluster(int density)
	{
		Star[][] tmpPixels = deepCopyStarMatrix(STARS);
		
		for(int i = 0; i < tmpPixels.length; i++)
		{
			for(int j = 0; j < tmpPixels[i].length; j++)
			{
				if(tmpPixels[i][j].isVisible())
				{
					double d = ThreadLocalRandom.current().nextInt(0, MAX + 1);
					double n = NOISE.eval(i, j, d);
					n = round(n, 2);
					
					// up left
					if(n < -0.75)
					{
						if((i - density) > 0 && (j - density) > 0)
							initStar(STARS[i-density][j-density], StarAttribute.MEDIUM_SIZE, StarAttribute.MEDIUM_COLOR);					
					}
						
					// down right
					else if(n < -0.5)
					{
						if((j + density) <= STARS[i].length - density && (i + density) <= STARS.length - density)
							initStar(STARS[i+density][j+density], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
					}
						
					// down left
					else if(n < -0.25)
					{
						if((j - density) > 0 && (i + density) <= STARS.length - density)
							initStar(STARS[i+density][j-density], StarAttribute.MEDIUM_SIZE, StarAttribute.MEDIUM_COLOR);
					}
						
					// up right
					else if(n < 0)
					{
						if((i - density) > 0 && (j + density) <= STARS[i].length - density)
							initStar(STARS[i-density][j+density], StarAttribute.MEDIUM_SIZE, StarAttribute.MEDIUM_COLOR);
					}
					
					// up left
					else if(n < 0.25)
					{
						if((i - density) > 0 && (j - density) > 0)
							initStar(STARS[i-density][j-density], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
					}
						
					// down right
					else if(n < 0.5)
					{
						if((j + density) <= STARS[i].length - density && (i + density) <= STARS.length - density)
							initStar(STARS[i+density][j+density], StarAttribute.MEDIUM_SIZE, StarAttribute.MEDIUM_COLOR);
					}
						
					// up right
					else if(n < 0.75)
					{
						if((i - density) > 0 && (j + density) <= STARS[i].length - density)
							initStar(STARS[i-density][j+density], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
					}
						
					// down left
					else if(n < 1)
					{
						if((j - density) > 0 && (i + density) <= STARS.length - density)
							initStar(STARS[i+density][j-density], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
					}
				}
			}
		}
	}

	
	/**
	 * Rounds a double to given number of decimal places.
	 * 
	 * @param value a double to be rounded
	 * @param places an integer representing required number of decimal places
	 * @return the rounded double
	 */
	public static double round(double value, int places) {
	    if (places < 0) 
	    		throw new IllegalArgumentException();

	    BigDecimal bigD = new BigDecimal(value);
	    bigD = bigD.setScale(places, RoundingMode.HALF_UP);
	    
	    return bigD.doubleValue();
	}
	
	
	/**
	 * Initializes a star object, sets the size and visibility.
	 * 
	 * @param star the star object being initialized
	 * @param size the size of the star
	 * @param color the color of the star
	 */
	private void initStar(Star star, int size, Color color)
	{
		star.setVisible(true);
		star.setSize(size);
		star.setColor(color);
	}
	
	/**
	 * Makes a deep copy of given matrix, quickly put together for task at hand,
	 * should be made generic and places into proper class.
	 * 
	 * @param src the source array
	 * @return a copy of the source array
	 */
	private Star[][] deepCopyStarMatrix(Star[][] src)
	{
		if(src == null)
			return null;
		
		Star[][] copy = new Star[src.length][];
		
		for(int i = 0; i < src.length; i++)
		{
			copy[i] = src[i].clone();
		}
		return copy;
	}

	public Star[][] getStars()
	{
		return STARS;
	}
}
