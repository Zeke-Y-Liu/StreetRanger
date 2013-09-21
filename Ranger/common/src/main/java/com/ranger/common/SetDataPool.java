package com.ranger.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*
 * a wrapper class of ArrayList to prevent duplicated element 
 */
public class SetDataPool<T> implements DataPool<T> {

	private List<T> _data = new ArrayList<T>();
	private int _size;
	private int cursor = -1;
	
	/*
	 * size == 0 means Integer.MAX_VALUE
	 * 
	 */
	public  SetDataPool(int size) {
		if(size == 0) {
			this._size = Integer.MAX_VALUE;
		} else {
			this._size = size;
		}
		
	}
	
	/*
	 *  param t, data to add
	 *  return true if pool is not full else return false
	 *
	 */
	@Override
	public synchronized boolean add(T t) {
		if(cursor == -1){
			cursor = 0;
		}
		if(!_data.contains(t)) {
			_data.add(t);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public synchronized boolean isFull() {
		return _data.size() >= _size;
	}
	/*
	 * clean the pool
	 */
	@Override
	public synchronized void clean() {
		_data.clear();
	}
	@Override
	public synchronized boolean isEmpty() {
		return _data.isEmpty();
	}
	/*
	 * get all the data out of the pool
	 * and clean the data cache
	 */
	@Override
	public synchronized List<T> dumpOut() {
		List<T> result = _data;
		_data = new ArrayList<T>();
		return result;
	}
	
//	@Override
//	public T takeOne() {
//		if(!_data.isEmpty()) {
//			cursor --;
//			return _data.remove(0);
//		} else {
//			return null;
//		}
//	}

	@Override
	public int size() {
		return _data.size();
	}

//	@Override
//	public T getCurrent() {
//		if(!_data.isEmpty()) {
//			return _data.get(cursor);
//		} else {
//			return null;
//		}
//	}

	@Override
	public T next() {
		cursor ++;
		if(cursor < _data.size()) {
			return _data.get(cursor);
		} else {
			return null;
		}
	}

	@Override
	public void reset() {
		if(_data.isEmpty()) {
			cursor = -1;
		} else {
			cursor = 0;
		}
	}

	@Override
	public boolean hasNext() {
		return cursor < _data.size()-1;
	}
}
