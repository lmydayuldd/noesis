package test.ikor.collection;

import static org.junit.Assert.*;

import java.util.Iterator;

import ikor.collection.DynamicPriorityQueue;
import ikor.collection.PriorityQueue;

import org.junit.Before;
import org.junit.Test;


public class PriorityQueueTest 
{
	PriorityQueue<String> queue;
	
	private static final String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };
	
	@Before
	public void setUp() throws Exception 
	{
		queue = new DynamicPriorityQueue<String>(String.CASE_INSENSITIVE_ORDER);
	}


	@Test
	public void testAdd() 
	{
		assertEquals(0, queue.size());
		
		for (int i=0; i<strings.length; i++)
			queue.add(strings[i]);
		
		assertEquals(strings.length, queue.size());
	}
	
	
	@Test
	public void testGet() 
	{
		for (int i=0; i<strings.length; i++)
			queue.add(strings[i]);
		
		assertEquals("best", queue.get());
		assertEquals("it", queue.get());
		assertEquals("it", queue.get());
		assertEquals("of", queue.get());
		assertEquals("the", queue.get());
		assertEquals("the", queue.get());
		assertEquals("times", queue.get());
		assertEquals("was", queue.get());
		assertEquals("was", queue.get());
		assertEquals("worst", queue.get());
		assertEquals(null, queue.get());
		
		assertEquals(0, queue.size());
		
	}	
	
	@Test
	public void testIterator() 
	{
		for (int i=0; i<strings.length; i++)
			queue.add(strings[i]);
		
		Iterator<String> iterator = queue.iterator();
		assertEquals("best", iterator.next());
		assertEquals("it", iterator.next());
		assertEquals("it", iterator.next());
		assertEquals("of", iterator.next());
		assertEquals("the", iterator.next());
		assertEquals("the", iterator.next());
		assertEquals("times", iterator.next());
		assertEquals("was", iterator.next());
		assertEquals("was", iterator.next());
		assertEquals("worst", iterator.next());
		assertFalse(iterator.hasNext());
		
		assertEquals(strings.length, queue.size());
	}		
}
