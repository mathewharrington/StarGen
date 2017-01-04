import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

import star.Star;

public class StarGenerator {

	private static Star[][] STARS;
	private static OpenSimplexNoise NOISE;
	private static int MAX;
	private static int SMALL_STAR = 1;
	private static int MEDIUM_STAR = 3;
	private static int LARGE_STAR = 4;
	private static Color SMALL_COLOR = 	new Color(167, 238, 253);
	private static Color MEDIUM_COLOR = new Color(208, 226, 236);
	private static Color LARGE_COLOR = 	new Color(217, 248, 255);
	
	public StarGenerator(int rows, int columns) {
		STARS = new Star[rows][columns];
		NOISE = new OpenSimplexNoise();
		MAX = (rows > columns) ? rows : columns;
		
		initSky();
	}
	
	// set all to false
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
	 * Uses simplex noise to generate 'stars', if noise value is less than
	 * given desnisty value we turn make it visible. First round generated stars
	 * are set to a slightly larger size, other smaller stars will be generated 
	 * around these larger ones.
	 * @param probability Double between -1 and 1, lower = less probability.
	 */
	public void genStars(double probability)
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
					initStar(STARS[i][j], LARGE_STAR, LARGE_COLOR);
				}
			}
		}
	}
	
	/**
	 * Uses simplex noise to generate 'stars', if noise value is less than
	 * given desnisty value we turn make it visible. First round generated stars
	 * are set to a slightly larger size, other smaller stars will be generated 
	 * around these larger ones.
	 * @param probability Double between -1 and 1, lower = less probability.
	 */
	public void genStars1(double probability)
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
					initStar(STARS[i][j], SMALL_STAR, SMALL_COLOR);
				}
			}
		}
	}
	
	
	/**
	 * Turns on stars in teh matrix that are [density] positions away.
	 * @param density Int how dense (close) the neighbours are.
	 */
//	public void genStars2(int density)
//	{
//		Star[][] tmpPixels = deepCopyStarMatrix(stars);
//		
//		for(int i = 0; i < tmpPixels.length; i++)
//		{
//			for(int j = 0; j < tmpPixels[i].length; j++)
//			{
//				if(tmpPixels[i][j].isVisible())
//				{
//					if((i - density) > 0)
//					{
//						stars[i-density][j] = true;
//					}
//						
//					if((i + density) <= stars.length -density)
//					{
//						stars[i+density][j] = true;
//					}
//						
//					if((j - density) > 0)
//					{
//						stars[i][j-density] = true;
//					}
//						
//					if((j + density) <= stars[i].length -density)
//					{
//						stars[i][j+density] = true;
//					}
//				}
//			}
//		}
//	}
	
	
	/**
	 * Turns on stars in the matrix that are [density] positions away. Third 
	 * incarnation uses simplex noise on the neighbours to avoid geometric
	 * looking scenes.
	 * @param density Int how dense (close) the neighbours are.
	 */
//	public void genStars3(int density)
//	{
//		boolean[][] tmpPixels = deepCopyStarMatrix(stars);
//		
//		for(int i = 0; i < tmpPixels.length; i++)
//		{
//			for(int j = 0; j < tmpPixels[i].length; j++)
//			{
//				if(tmpPixels[i][j])
//				{
//					double d = ThreadLocalRandom.current().nextInt(0, max + 1);
//					double n = noise.eval(i, j, d);
//					n = round(n, 2);
//					
//					// up
//					if(n < -0.75)
//					{
//						if((i - density) > 0)
//							stars[i-density][j] = true;
//					}
//						
//					// down
//					else if(n < -0.5)
//					{
//						if((i + density) <= stars.length - density)
//							stars[i+density][j] = true;
//					}
//						
//					// left
//					else if(n < -0.25)
//					{
//						if((j - density) > 0)
//							stars[i][j-density] = true;
//					}
//						
//					// right
//					else if(n < 0)
//					{
//						if((j + density) <= stars[i].length - density)
//							stars[i][j+density] = true;
//					}
//					
//					// up left
//					else if(n < 0.25)
//					{
//						if((i - density) > 0 && (j - density) > 0)
//							stars[i-density][j-density] = true;
//					}
//						
//					// up right
//					else if(n < 0.5)
//					{
//						if((i - density) > 0 && (j + density) <= stars[i].length - density)
//							stars[i-density][j+density] = true;
//					}
//						
//					// down left
//					else if(n < 0.75)
//					{
//						if((j - density) > 0 && (i + density) <= stars.length - density)
//							stars[i+density][j-density] = true;
//					}
//						
//					// down right
//					else if(n < 1)
//					{
//						if((j + density) <= stars[i].length - density && (i + density) <= stars.length - density)
//							stars[i+density][j+density] = true;
//					}
//				}
//			}
//		}
//	}
	
//	public void genStars4(int density)
//	{
//		Star[][] tmpPixels = deepCopyStarMatrix(STARS);
//		
//		for(int i = 0; i < tmpPixels.length; i++)
//		{
//			for(int j = 0; j < tmpPixels[i].length; j++)
//			{
//				if(tmpPixels[i][j].isVisible())
//				{
//					double d = ThreadLocalRandom.current().nextInt(0, MAX + 1);
//					double n = NOISE.eval(i, j, d);
//					n = round(n, 2);
//					
//					// up left
//					if(n < -0.75)
//					{
//						if((i - density) > 0 && (j - density) > 0)
//							initStar(STARS[i-density][j-density], MEDIUM_STAR);					
//					}
//						
//					// down right
//					else if(n < -0.5)
//					{
//						if((j + density) <= STARS[i].length - density && (i + density) <= STARS.length - density)
//							initStar(STARS[i+density][j+density], SMALL_STAR);
//					}
//						
//					// down left
//					else if(n < -0.25)
//					{
//						if((j - density) > 0 && (i + density) <= STARS.length - density)
//							initStar(STARS[i+density][j-density], MEDIUM_STAR);
//					}
//						
//					// up right
//					else if(n < 0)
//					{
//						if((i - density) > 0 && (j + density) <= STARS[i].length - density)
//							initStar(STARS[i-density][j+density], MEDIUM_STAR);
//					}
//					
//					// left
//					else if(n < 0.25)
//					{
//						if((j - density) > 0)
//							initStar(STARS[i][j-density], MEDIUM_STAR);
//					}
//						
//					// right
//					else if(n < 0.5)
//					{
//						if((j + density) <= STARS[i].length - density)
//							initStar(STARS[i][j+density], SMALL_STAR);
//					}
//						
//					// up
//					else if(n < 0.75)
//					{
//						if((i - density) > 0)
//							initStar(STARS[i-density][j], SMALL_STAR);
//					}
//						
//					// down
//					else if(n < 1)
//					{
//						if((i + density) <= STARS.length - density)
//							initStar(STARS[i+density][j], SMALL_STAR);
//					}
//				}
//			}
//		}
//	}
	
	
	public void genStars4(int density)
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
							initStar(STARS[i-density][j-density], MEDIUM_STAR, MEDIUM_COLOR);					
					}
						
					// down right
					else if(n < -0.5)
					{
						if((j + density) <= STARS[i].length - density && (i + density) <= STARS.length - density)
							initStar(STARS[i+density][j+density], SMALL_STAR, SMALL_COLOR);
					}
						
					// down left
					else if(n < -0.25)
					{
						if((j - density) > 0 && (i + density) <= STARS.length - density)
							initStar(STARS[i+density][j-density], MEDIUM_STAR, MEDIUM_COLOR);
					}
						
					// up right
					else if(n < 0)
					{
						if((i - density) > 0 && (j + density) <= STARS[i].length - density)
							initStar(STARS[i-density][j+density], MEDIUM_STAR, MEDIUM_COLOR);
					}
					
					// up left
					else if(n < 0.25)
					{
						if((i - density) > 0 && (j - density) > 0)
							initStar(STARS[i-density][j-density], SMALL_STAR, SMALL_COLOR);
					}
						
					// down right
					else if(n < 0.5)
					{
						if((j + density) <= STARS[i].length - density && (i + density) <= STARS.length - density)
							initStar(STARS[i+density][j+density], MEDIUM_STAR, MEDIUM_COLOR);
					}
						
					// up right
					else if(n < 0.75)
					{
						if((i - density) > 0 && (j + density) <= STARS[i].length - density)
							initStar(STARS[i-density][j+density], SMALL_STAR, SMALL_COLOR);
					}
						
					// down left
					else if(n < 1)
					{
						if((j - density) > 0 && (i + density) <= STARS.length - density)
							initStar(STARS[i+density][j-density], SMALL_STAR, SMALL_COLOR);
					}
				}
			}
		}
	}

	/**
	 * Rounds a double to given number of decimal places.
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
	 * @param star
	 * @param size
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
	 * @param src
	 * @return
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
