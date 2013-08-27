package com.ranger.common;

import java.util.List;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class DataPoolTest {
	@Test
	public void testAdd() {
		DataPool<String> pool = new DataPool<String>(2);
		Assert.assertTrue(pool.add("aaaa"));
		Assert.assertTrue(pool.add("bbbb"));
		Assert.assertFalse(pool.add("cccc"));
	}
	
	@Test
	public void testIsFull() {
		DataPool<String> pool = new DataPool<String>(3);
		pool.add("aaaa");
		pool.add("bbbb");
		pool.add("cccc");
		Assert.assertTrue(pool.isFull());
	}
	
	@Test
	public void testIsEmpty() {
		DataPool<String> pool = new DataPool<String>(3);
		Assert.assertTrue(pool.isEmpty());
	}
	
	@Test
	public void testClean() {
		DataPool<String> pool = new DataPool<String>(3);
		pool.add("aaaa");
		pool.add("bbbb");
		pool.add("cccc");
		pool.clean();
		Assert.assertTrue(pool.isEmpty());
		Assert.assertTrue(pool.add("aaaa"));
		Assert.assertTrue(pool.add("bbbb"));
		Assert.assertTrue(pool.add("cccc"));
		Assert.assertFalse(pool.add("dddd"));
	}
	
	@Test
	public void testDumpOut() {
		DataPool<String> pool = new DataPool<String>(8);
		pool.add("aaaa");
		pool.add("bbbb");
		pool.add("cccc");
		pool.add("dddd");
		pool.add("eeee");
		pool.add("ffff");
		pool.add("gggg");
		pool.add("hhhh");
		List<String> output = pool.dumpOut();
		Assert.assertTrue(output.size() == 8);
		Assert.assertTrue(pool.isEmpty());
	}
	
}
