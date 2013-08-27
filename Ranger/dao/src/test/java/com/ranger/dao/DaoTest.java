package com.ranger.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ranger.common.People;
import com.ranger.common.Tag;

public class DaoTest {
	@Test
	public void testBatchInsert() {
		Dao dao = (Dao) SpringUtil.getBean("dao");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.getBean("jdbcTemplate");
		jdbcTemplate.execute("DELETE FROM TAG WHERE NAME LIKE 'TEST%'");
		jdbcTemplate.execute("DELETE FROM PEOPLE WHERE NAME LIKE 'TEST%'");
		
		List<People> people = new ArrayList<People>();
		for (int i = 0; i < 100; i++) {
			People p = new People(null, "TEST" + i, 'M', 88, "xxxx@gmail.com");
			people.add(p);
			// for each people, create some tags.
			// the number of tags varies from 0 to 4
			int tagNum = i % 5;
			List<Tag> tags = new ArrayList<Tag>();
			for (int j = 0; j < tagNum; j++) {
				Tag tag = new Tag(null, "Test_" + i + "_" + j);
				tags.add(tag);
			}
			p.setTags(tags);
		}
		List<People> result = dao.batchInsertPeople(people);
		Assert.assertTrue(result.size() == 100);
		// further verification
		List<People> loadedPeople = dao.loadPeopleByNameLike("TEST");
		Assert.assertTrue(loadedPeople.size() == 100);
		Collections.sort(loadedPeople, new PeopleComparator());
		for(int j=0; j < 100; j++) {
			Assert.assertEquals(people.get(j), loadedPeople.get(j));
		}
		
	}
}

class PeopleComparator implements Comparator<People> {

	@Override
	public int compare(People p1, People p2) {
		
		return p1.getId().compareTo(p2.getId());
	}
	
}