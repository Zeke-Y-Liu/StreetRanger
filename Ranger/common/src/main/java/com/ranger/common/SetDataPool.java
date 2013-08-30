package com.ranger.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetDataPool<T> implements DataPool<T> {

	private Set<T> _data = new HashSet<T>();
	private int _size;
	public  SetDataPool(int size) {
		this._size = size;
	}
	
	/*
	 *  param t, data to add
	 *  return true if pool is not full else return false
	 *
	 */
	public synchronized boolean add(T t) {
		if(_data.size() < _size) {
			_data.add(t);
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized boolean isFull() {
		return _data.size() >= _size;
	}
	/*
	 * clean the pool
	 */
	public synchronized void clean() {
		_data.clear();
	}
	
	public synchronized boolean isEmpty() {
		return _data.isEmpty();
	}
	/*
	 * get all the data out of the pool
	 * and clean the data cache
	 */
	public synchronized Set<T> dumpOut() {
		Set<T> result = _data;
		_data = new HashSet<T>();
		return result;
	}
	
}
