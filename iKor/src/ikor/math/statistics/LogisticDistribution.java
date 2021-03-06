package ikor.math.statistics;

import ikor.math.random.Random;

/**
 * Logistic distribution
 * http://en.wikipedia.org/wiki/Logistic_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LogisticDistribution implements Distribution 
{
	private double mu;
	private double sigma;
	
	private static final double FACTOR = Math.PI / (2.0*Math.sqrt(3));
	private static final double IFACTOR = Math.sqrt(3) / Math.PI;
	
	public LogisticDistribution (double location, double scale)
	{
		this.mu = location;
		this.sigma = scale;
	}
	
	@Override
	public double pdf (double x) 
	{
		double e = Math.exp( -FACTOR*Math.abs(x-mu)/sigma );
		
		return FACTOR * e / (sigma*(1+e)*(1+e));
	}

	@Override
	public double cdf (double x) 
	{
		double e = Math.exp( -FACTOR*Math.abs(x-mu)/sigma );
		
		if (x>=mu)
			return 1/(1+e);
		else
			return e/(1+e);
	}

	@Override
	public double idf (double p) 
	{
		return mu + IFACTOR*sigma*Math.log(p/(1-p));
	}
	
	// Random number generator
	
	@Override
	public double random() 
	{
		double u;
		
		do {
			u = Random.random(); 
		} while (u*(1.-u) == 0.);
		
		return idf(u);
	}

	@Override
	public double mean() 
	{
		return mu;
	}

	@Override
	public double variance() 
	{
		return sigma*sigma*Math.PI*Math.PI / 3;
	}

	@Override
	public double skewness() 
	{
		return 0;
	}

	@Override
	public double kurtosis() 
	{
		return 6.0/5.0;
	}

}
