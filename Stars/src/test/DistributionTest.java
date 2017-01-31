package test;
import java.math.BigDecimal;
import java.math.RoundingMode;

import application.OpenSimplexNoise;

public class DistributionTest {

	public static void main(String[] args) {
		
		OpenSimplexNoise noise;
		int mean = 5;
		int standardDeviation = 6;
		int numIterations = 100; 
		double n, x = 0, y = 0;
		double[] results = new double[numIterations];
		
		noise = new OpenSimplexNoise();

		for(int i = 0; i < numIterations; i++)
		{
			n = noise.eval(x, y);
			
			// default standard deviation from eval function is 1. To change this
			// and stretch the results a bit further then multiply result by 
			// desired standard deviation.
			n *= standardDeviation;
			
			// if mean is 0 (which it is by default using eval function) and
			// we want to cluster around a different number then add that number
			// to the returned value
			n += mean;
			
			results[i] = round(n, 2);
			x++;
			y++;
		}

		System.out.println("Total <= 1: " + lessThanDist(results, 1));
		System.out.println("Total <= 2: " + lessThanDist(results, 2));
		System.out.println("Total <= 3: " + lessThanDist(results, 3));
		System.out.println("Total <= 4: " + lessThanDist(results, 4));
		System.out.println("Total <= 5: " + lessThanDist(results, 5));
		System.out.println("Total >= 5: " + greaterThanDist(results, 5));
		System.out.println("Total >= 6 " + greaterThanDist(results, 6));
		System.out.println("Total >= 7 " + greaterThanDist(results, 7));
		System.out.println("Total >= 8 " + greaterThanDist(results, 8));
		System.out.println("Total >= 9 " + greaterThanDist(results, 9));
	}
	
	public static int lessThanDist(double[] dist, double n)
	{
		int count = 0;
		
		for(int i = 0; i < dist.length; i++)
		{
			if(dist[i] <= n)
			{
				count++;
			}
		}
		
		return count;
	}
	
	public static int greaterThanDist(double[] dist, double n)
	{
		int count = 0;
		
		for(int i = 0; i < dist.length; i++)
		{
			if(dist[i] >= n)
			{
				count++;
			}
		}
		
		return count;
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
}
