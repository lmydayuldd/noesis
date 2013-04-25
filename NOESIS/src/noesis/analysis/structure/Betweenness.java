package noesis.analysis.structure;

import ikor.math.Vector;

import ikor.parallel.*;
import ikor.parallel.combiner.VectorAccumulator;

import noesis.Network;


// Betweenness centrality, between (2n-1) and n^2-(n-1)

public class Betweenness extends NodeMetrics 
{
	public Betweenness (Network network)
	{
		super(network);
	}	

	@Override
	public String getName() 
	{
		return "betweenness-score";
	}	

	@Override
	public String getDescription() 
	{
		return "Betweenness score";
	}			
	
	public double compute(int node) 
	{
		checkDone();	
		
		return get(node);
	}	

	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
	
		// Iterative algorithm
		/*
		BetweennessScore score;
		
		for (int node=0; node<size; node++) {
			
			score = new BetweennessScore(net,node);
			score.compute();
			
			for (int i=0; i<size; i++) {
			    set (i, get(i) + score.get(i) );
			}
		}
		*/
		
		// Parallel algorithm
		
		Vector score = (Vector) Parallel.reduce( new BetweennessKernel(net), new VectorAccumulator(size), 0, size-1);

		for (int i=0; i<size; i++) {
		    set (i, score.get(i) );
		}
		
		done = true;
	}	
	
	
	class BetweennessKernel implements Kernel<Vector>
	{
		private Network net;
		
		public BetweennessKernel (Network net)
		{
			this.net = net;
		}
		
		@Override
		public Vector call (int index) 
		{
			BetweennessScore score = new BetweennessScore(net, index);
			
			score.compute();
			
			return score;
		}
	}		
	
}
