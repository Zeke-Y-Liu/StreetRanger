package com.ranger.common;

import java.util.Collection;
import java.util.List;


public interface DataPool<T> {

	
	/*
	 *  param t, data to add
	 *  return true if pool is not full else return false
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
	public  Collection<T> dumpOut();
}
