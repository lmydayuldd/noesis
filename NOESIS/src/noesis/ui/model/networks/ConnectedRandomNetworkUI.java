package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.RandomLayout;

import noesis.model.random.ConnectedRandomNetwork;


public class ConnectedRandomNetworkUI extends NewNetworkUI 
{
	Editor<Integer> nodeCountEditor;
	Editor<Double>  probabilityEditor;
	
	public ConnectedRandomNetworkUI (Application app) 
	{
		super(app, "New connected random network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(1000);
		
		nodeCountEditor = new Editor<Integer>("Number of network nodes", nodeCountModel);
		nodeCountEditor.setIcon( app.url("icons/calculator.png") );
		nodeCountEditor.setData(100);
		add(nodeCountEditor);
		
		RealModel probabilityModel = new RealModel();
		probabilityModel.setMinimumValue( 0.0 );
		probabilityModel.setMaximumValue( 1.0 );
		
		probabilityEditor = new Editor<Double>("Link probability", probabilityModel);
		probabilityEditor.setIcon( app.url("icons/calculator.png") );
		probabilityEditor.setData( 0.1 );
		add(probabilityEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ConnectedRandomNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class ConnectedRandomNetworkAction extends Action 
	{
		private ConnectedRandomNetworkUI ui;

		public ConnectedRandomNetworkAction (ConnectedRandomNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeCountEditor.getData();
			int links = Math.max( nodes-1, (int) ( ui.probabilityEditor.getData() * nodes * (nodes-1))/2 );
			
			ConnectedRandomNetwork random = new ConnectedRandomNetwork(nodes,links);
			AttributeNetwork network = createAttributeNetwork(random, "Connected random network", new RandomLayout ());
									
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
