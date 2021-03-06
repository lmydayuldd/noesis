package ikor.math.statistics;

import ikor.math.random.Random;

/**
 * Exponential distribution (a.k.a. negative exponential distribution)
 * http://en.wikipedia.org/wiki/Exponential_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class ExponentialDistribution implements Distribution 
{
	private double lambda;
	
	public ExponentialDistribution (double lambda)
	{
		this.lambda = lambda;
	}
	
	@Override
	public double pdf (double x) 
	{
		if (x<0)
			return 0;
		else
			return lambda * Math.exp(-lambda*x);
	}

	@Override
	public double cdf (double x) 
	{
		if (x<0)
			return 0;
		else
			return 1 - Math.exp(-lambda*x);
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return 0;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return -Math.log(1-p)/lambda;
	}
	
	// Random number generator
	
	@Override
	public double random() 
	{
		double u;
		
		do { 
			u = Random.random(); 
		} while (u == 0.0);
		
		return -Math.log(u)/lambda;
	}
	
	// Statistics

	@Override
	public double mean() 
	{
		return 1/lambda;
	}

	@Override
	public double variance() 
	{
		return 1/(lambda*lambda);
	}

	@Override
	public double skewness() 
	{
		return 2;
	}

	@Override
	public double kurtosis() 
	{
		return 6;
	}

}

