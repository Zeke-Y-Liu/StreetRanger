package com.ranger.dao;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SpringUtilTest {
	@Test
	public void testGetBean() {
		Assert.assertNotNull(SpringUtil.getBean("dao"));
	}
}
