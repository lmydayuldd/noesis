package ikor.collection;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Set implementation using java.util.HashSet, which offers O(1) performance for basic operations (add, remove, size & contains)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 * @param <T> Element type
 */
public class DynamicSet<T> implements Set<T> 
{
	private HashSet<T> set = new HashSet();

	@Override
	public int add(T object) 
	{
		if (set.add(object))
			return size()-1;
		else
			return -1;
	}

	@Override
	public boolean remove(T object) 
	{
		return set.remove(object);
	}

	@Override
	public void clear() 
	{
		set.clear();
	}

	@Override
	public int size() 
	{
		return set.size();
	}

	@Override
	public boolean contains(T object) 
	{
		return set.contains(object);
	}

	@Override
	public Iterator<T> iterator() 
	{
		return set.iterator();
	}

	// Set operations
	
	@Override
	public Set<T> union(Set<T> other) 
	{
		Set<T> result = new DynamicSet();
		
		for (T element: this) 
			result.add(element);
		
		for (T element: other)
			result.add(element);
		
		return result;
	}

	@Override
	public Set<T> intersection(Set<T> other) 
	{
		Set<T> result = new DynamicSet();
		
		for (T element: this) {
			if (other.contains(element))
				result.add(element);
		}
		
		return result;
	}

	@Override
	public Set<T> difference(Set<T> other) 
	{
		Set<T> result = new DynamicSet();
		
		for (T element: this) {
			if (!other.contains(element))
				result.add(element);
		}
		
		return result;
	}

}
