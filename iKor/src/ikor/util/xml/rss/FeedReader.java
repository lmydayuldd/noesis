package ikor.util.xml.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Iterator;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

/**
 * XML Feed Reader (base class)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class FeedReader 
{
	private URL url;
	
	// Constructor
	
	public FeedReader (String feedURL) 
	{
		try {
			
			try {
				this.url = new URL(expandURL(feedURL));
			} catch (IOException e) {
				this.url = new URL(feedURL);
			}
			
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
    private String expandURL (String shortenedURL) throws IOException 
    {
        URL url = new URL(shortenedURL);    
        
        // open connection
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY); 
        
        // stop following browser redirect
        httpURLConnection.setInstanceFollowRedirects(false);
         
        // extract location header containing the actual destination URL
        String expandedURL = httpURLConnection.getHeaderField("Location");
        httpURLConnection.disconnect();
         
        return expandedURL;
    }	
    
    
	// Abstract method
	
	public abstract Feed read ();
	
	// Utility methods...
	// ------------------
	
	// XML readers

	protected XMLEventReader getStAXreader() 
		throws FactoryConfigurationError, XMLStreamException, IOException 
	{
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		return inputFactory.createXMLEventReader(url.openStream());
	}
	
	protected XMLStreamReader getStreamReader () 
		throws XMLStreamException, IOException
	{
		InputStream input = url.openStream();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		
		return factory.createXMLStreamReader(input);
	}

	protected InputStream getInputStream () 
		throws IOException
	{
		return url.openStream();
	}

	// Data access
	
	protected String getCharacterData(XMLEvent event, XMLEventReader eventReader)
		throws XMLStreamException 
	{
		String result = "";

		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}


	protected String getElementText(XMLEvent event, XMLEventReader eventReader)
		throws XMLStreamException 
	{
		return eventReader.getElementText();
	}

	protected String getAttribute(XMLEvent event, XMLEventReader eventReader, String id)
	{
		String result = "";
		
		Iterator<Attribute> iterator = ((StartElement)event).getAttributes();
		
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			if (attribute.getName().getLocalPart().equals(id))
				result = attribute.getValue();
		}
		
		return result;
	}
	
}