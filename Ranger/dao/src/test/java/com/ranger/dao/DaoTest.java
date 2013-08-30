package com.ranger.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ranger.common.Tag;
import com.ranger.common.User;

public class DaoTest {
	@Test
	public void testBatchInsert() {
		Dao dao = (Dao) SpringUtil.getBean("dao");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.getBean("jdbcTemplate");
		jdbcTemplate.execute("DELETE FROM TAG WHERE NAME LIKE 'TEST%'");
		jdbcTemplate.execute("DELETE FROM PEOPLE WHERE NAME LIKE 'TEST%'");
		
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			User u = new User();
			users.add(u);
			// for each people, create some tags.
			// the number of tags varies from 0 to 4
			int tagNum = i % 5;
			List<Tag> tags = new ArrayList<Tag>();
			for (int j = 0; j < tagNum; j++) {
				Tag tag = new Tag(null, "Test_" + i + "_" + j);
				tags.add(tag);
			}
			u.setTags(tags);
		}
		List<User> result = dao.batchInsertUser(users);
		Assert.assertTrue(result.size() == 100);
		// further verification
		List<User> loadedUsers = dao.loadUserByNameLike("TEST");
		Assert.assertTrue(loadedUsers.size() == 100);
		Collections.sort(loadedUsers, new UserComparator());
		for(int j=0; j < 100; j++) {
			Assert.assertEquals(users.get(j), loadedUsers.get(j));
		}
		
	}
}

class UserComparator implements Comparator<User> {

	@Override
	public int compare(User u1, User u2) {
		
		return u1.getId().compareTo(u2.getId());
	}
	
}