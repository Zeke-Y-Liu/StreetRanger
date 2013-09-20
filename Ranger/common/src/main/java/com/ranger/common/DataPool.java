package com.ranger.common;

import java.util.Collection;
import java.util.List;


public interface DataPool<T> {

	
	/*
	 *  param t, data to add
	 *  return true if pool is not full else return false, in either case, the element will be added to pool
	 *
	 */
	public  boolean add(T t);
	
	public  boolean isFull();
	/*
	 * clean the pool
	 */
	public  void clean();
	
	public  boolean isEmpty();
	/*
	 * get all the data out of the pool
	 * and clean the data cache
	 */
	public  List<T> dumpOut();
	
//	/*
//	 * take one element from element
//	 * remove the element from pool
//	 */
//	public T takeOne();
	
	public int size();
	
//	/*
//	 * get current element
//	 * 
//	 */
//	public T getCurrent();
	/*
	 * move the cursor to next element
	 * 
	 */
	public T next();
	
	/*
	 * move the cursor to first element if pool is not empty
	 * else current cursor is -1 
	 */
	public void reset();
	
	public boolean hasNext();
}
