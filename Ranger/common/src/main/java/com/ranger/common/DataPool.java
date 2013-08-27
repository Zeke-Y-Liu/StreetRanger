package com.ranger.common;

import java.util.ArrayList;
import java.util.List;

public class DataPool<T> {
	private List<T> _data = new ArrayList<T>();
	private int _size;
	public  DataPool(int size) {
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
	public synchronized List<T> dumpOut() {
		List<T> result = _data;
		_data = new ArrayList<T>();
		return result;
	}
}
