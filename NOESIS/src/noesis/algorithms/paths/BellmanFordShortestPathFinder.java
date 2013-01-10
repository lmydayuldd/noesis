package noesis.algorithms.paths;

import ikor.collection.Evaluator;

import noesis.Network;

public class BellmanFordShortestPathFinder<V,E> extends SingleSourceShortestPathFinder<V,E> implements PathFinder<V, E>
{
	private boolean negativeCycles;
	
	public BellmanFordShortestPathFinder (Network<V,E> net, int origin, Evaluator<E> linkEvaluator)
	{
		super(net,origin,linkEvaluator);
	}

	
	/* (non-Javadoc)
	 * @see noesis.algorithms.paths.PathFinder#run()
	 */
	@Override
	public void run()
	{
		double linkValue;
		int    size = network.size();
		double newDistance[];
		double tmp[];
		
		// Initialization
		
		predecessor = new int[size];
		distance    = new double[size];
		newDistance = new double[size];
		
		for (int i=0; i<size; i++) {
			predecessor[i] = -1;
			distance[i] = Double.POSITIVE_INFINITY;
		}
		
		distance[origin] = 0;
		
	
		// Dynamic programming algorithm
		
		boolean changes = true; // for early-stopping
		int     iterations;
		
		for (iterations=1; (iterations<=size) && changes; iterations++) {
			
			changes = false;
			
			for (int v=0; v<size; v++) {
			
				newDistance[v] = distance[v];
				            
		       	int[] links = network.inLinks(v);
	        	
	        	if (links!=null)
	        		for (int j=0; j<links.length; j++) {
	        				
	       				linkValue = linkEvaluator.evaluate( network.get(links[j], v) );
	        				
	       				if (newDistance[v] > distance[links[j]] + linkValue) {
	       					predecessor[v] = links[j];
	       					newDistance[v] = distance[links[j]] + linkValue;
	       					changes = true;
	       				}
	        		}
			}
			
			tmp = distance;
			distance = newDistance;
			newDistance = tmp;
		}
		
		if ((iterations>size) && changes)
			negativeCycles = true;
		else
			negativeCycles = false;			
	}		
	
	
	public boolean negativeCycleDetected ()
	{
		return negativeCycles;
	}
}

