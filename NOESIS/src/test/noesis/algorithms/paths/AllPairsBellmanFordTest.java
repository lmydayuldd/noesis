package test.noesis.algorithms.paths;

import static org.junit.Assert.*;

import org.junit.Test;

import test.noesis.SampleNetworks;
import test.noesis.algorithms.DirectLinkEvaluator;

import noesis.LinkEvaluator;
import noesis.Network;
import noesis.algorithms.paths.*;

public class AllPairsBellmanFordTest extends AllPairsShortestPathFinderTest
{
	// Path finder
	
	@Override
	public AllPairsShortestPathFinder pathFinder (Network net, LinkEvaluator linkEvaluator)
	{		
		return new AllPairsBellmanFord(net,linkEvaluator);
	}
	
	
	// Unit tests
	
	@Test
	public void testConnected() 
	{
		checkConnected();
		assertFalse(finder.negativeCycleDetected());		
	}	

	@Test
	public void testUnreachable() 
	{
		checkUnreachable();
		assertFalse(finder.negativeCycleDetected());
	}	
	
	@Test
	public void testDisconnected() 
	{
		checkDisconnected();
		assertFalse(finder.negativeCycleDetected());
	}		

	@Test
	public void testNegativeWeights ()
	{
		network = SampleNetworks.negativeWeightsGraph();
		
		finder  = new AllPairsBellmanFord(network, new DirectLinkEvaluator(network));
		
		finder.run();
		
		for (int i=0; i<network.size(); i++)
			for (int j=0; j<network.size(); j++)
				checkDistance(i,j, SampleNetworks.NEGATIVE_DISTANCE[i][j]);

		assertFalse( finder.negativeCycleDetected() );
	}
	
	@Test
	public void testNegativeCycle ()
	{
		Network<String,Integer> cycle = SampleNetworks.negativeCycleGraph();
		
		finder = new AllPairsBellmanFord (cycle, new DirectLinkEvaluator(cycle));
		
		finder.run();

		assertTrue( finder.negativeCycleDetected() );
	}
	
}
