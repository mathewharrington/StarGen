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
	 * Initialises the sky with new stars, not visible by default.
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
	 * given probability/desnisty value it is made visible. First round generated 
	 * stars are set to a slightly larger size, other smaller stars will be generated 
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
	 * Generates clusters of stars using simplex noise.
	 * OpenSimplex noise is used to determine the distance between the new star 
	 * and the original, the direction random for now.
	 * 
	 * @param mean the average number of pixels to have between stars.
	 * @param stdDeviation the desired standard deviation to apply to the noise
	 */
	public void buildCluster(double mean, double stdDeviation)
	{
		Star[][] tmpPixels = deepCopyStarMatrix(STARS);
		Double numSpaces, halfNumSpaces;
		int direction;
		
		for(int i = 0; i < tmpPixels.length; i++)
		{
			for(int j = 0;j < tmpPixels[i].length; j++)
			{
				if(tmpPixels[i][j].isVisible())
				{
					// how many pixels next star will be from current
					numSpaces = NOISE.eval(i, j);
					
					numSpaces = map(numSpaces, mean, stdDeviation);
					
					// obtain half value
					halfNumSpaces = numSpaces / 2;
					
					
					// round result to two decimal places
					numSpaces = round(numSpaces, 1);
					
					// x and y axis coords use integers
					int inumSpaces = numSpaces.intValue();
					int ihalfNumSpaces = halfNumSpaces.intValue();
					
					// choose direction
					direction = ThreadLocalRandom.current().nextInt(1, 9);
					
					switch(direction)
					{
						// up up left
						case 1:
							if((i - inumSpaces) > 0 && (j - ihalfNumSpaces) > 0)
							{
								initStar(STARS[i - inumSpaces][j - ihalfNumSpaces], StarAttribute.MEDIUM_SIZE, StarAttribute.MEDIUM_COLOR);
							}
							break;
						
						// left left up
						case 2:
							if((i - ihalfNumSpaces) > 0 && (j - inumSpaces) > 0)
							{
								initStar(STARS[i - ihalfNumSpaces][j - inumSpaces], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
							}
							break;
						
						// up up right
						case 3:
							if((i - inumSpaces) > 0 && (j + ihalfNumSpaces) < tmpPixels[i].length)
							{
								initStar(STARS[i - inumSpaces][j + ihalfNumSpaces], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
							}
							break;
						
						// right right up
						case 4:
							if((i - ihalfNumSpaces) > 0 && (j + inumSpaces) < tmpPixels[i].length)
							{
								initStar(STARS[i - ihalfNumSpaces][j + inumSpaces], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
							}
							break;
						
						// down down right
						case 5:
							if((i + inumSpaces) < tmpPixels.length && (j + ihalfNumSpaces) < tmpPixels[i].length)
							{
								initStar(STARS[i + inumSpaces][j + ihalfNumSpaces], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
							}
							break;
						
						// right right down
						case 6:
							if((i + ihalfNumSpaces) < tmpPixels.length && (j + inumSpaces) < tmpPixels[i].length)
							{
								initStar(STARS[i + ihalfNumSpaces][j + inumSpaces], StarAttribute.MEDIUM_SIZE, StarAttribute.MEDIUM_COLOR);
							}
							break;
						
						// down down left
						case 7:
							if((i + inumSpaces) < tmpPixels.length && (j - ihalfNumSpaces) > 0)
							{
								initStar(STARS[i + inumSpaces][j - ihalfNumSpaces], StarAttribute.SMALL_SIZE, StarAttribute.SMALL_COLOR);
							}
							break;
						
						// left left down
						case 8:
							if((i + ihalfNumSpaces) < tmpPixels.length && (j - inumSpaces) > 0)
							{
								initStar(STARS[i + ihalfNumSpaces][j - inumSpaces], StarAttribute.MEDIUM_SIZE, StarAttribute.MEDIUM_COLOR);
							}
							break;
					}
				}
			}
		}
	}
	
	
	/**
	 * OpenSimplex noise returns a mean of zero and a standard deviation of 1, 
	 * so to use this for distance in terms of pixels we need need to map the
	 * returned value using a given mean and standard deviation. 
	 * 
	 * Maps value returned from OpenSimplexNoise eval() to more usable
	 * number by adjusting the mean and standard deviation.
	 * 
	 * @param noiseVal the value returned from OpenSimplexNoise eval()
	 * @param mean the amount to shift mean
	 * @param stdDeviation the required adjustment to the standard deviation
	 * @return the noise value mapped to more usable number
	 */
	private double map(double noiseVal, double mean, double stdDeviation)
	{
		// stretch to given standard deviation
		noiseVal *= stdDeviation;
		
		// shift to given mean
		noiseVal += mean;
		
		return noiseVal;
	}
	
	
	/**
	 * Rounds a double to given number of decimal places.
	 * 
	 * @param value a double to be rounded
	 * @param places an integer representing required number of decimal places
	 * @return the rounded double
	 */
	private double round(double value, int places) {
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
