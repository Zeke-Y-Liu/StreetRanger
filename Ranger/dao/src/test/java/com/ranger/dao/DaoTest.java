package com.ranger.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ranger.common.People;

public class DaoTest {
	@Test
	public void testBatchInsert() {
		Dao dao = (Dao)SpringUtil.getBean("dao");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.getBean("jdbcTemplate");
		jdbcTemplate.execute("DELETE FROM PEOPLE WHERE NAME LIKE 'TEST%'");
		List<People> people = new ArrayList<People>();
		
		for(int i=0; i< 1000; i ++){
			People p = new People("TEST" + i, 'M', 88, "xxxx@gmail.com");
			people.add(p);
		}
		int[] result = dao.batchInsert(people);
		Assert.assertTrue(result.length == 1000);
	}
}
