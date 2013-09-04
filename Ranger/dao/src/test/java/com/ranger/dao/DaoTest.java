package com.ranger.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ranger.common.Source;
import com.ranger.common.SourceConstant;
import com.ranger.common.Tag;
import com.ranger.common.User;

public class DaoTest {
	// @Test
	public void testBatchInsertUser() {
		Dao dao = (Dao) SpringUtil.getBean("dao");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.getBean("jdbcTemplate");
		jdbcTemplate.execute("DELETE FROM `WB_TAG` WHERE `WEIGHT` LIKE 'TEST%'");
		jdbcTemplate.execute("DELETE FROM `WB_USER` WHERE `NAME` LIKE 'TEST%'");
		
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			User u = new User(
					null, 
					"UID" + i,
					"SCREENNAME" + i, 
					"TESTNAME"+ i, 
					18,
					28,
					"location" +i,
					"Description" +i, 
					"url" + i, 
					"profileImageUrl"+ i, 
					"userDomain"+ i,
					"m", 
					38, 
					48,
					58, 
					68,
					new Date(), 
					false,
					false,
					78,
					false,
					false,
					true,
					"avatarLarge"+ i,
					88,
					98, 
					"remark"+ i,
					"CN",
					"verifiedReason"+ i,
					"weibo"+ i, 
					"statusId"+ i,
					null);
			users.add(u);
			// for each user, create some tags.
			// the number of tags varies from 0 to 4
			int tagNum = i % 5;
			List<Tag> tags = new ArrayList<Tag>();			
			for (int j = 0; j < tagNum; j++) {				
				Tag tag = new Tag(null, "tid" + i + "_" + j,"value", "TESTweight", null);
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

	
	@Test
	public void testBatchInsertTag() {
		// batchInsertTag method is covered in testBatchInsertUser
		Dao dao = (Dao) SpringUtil.getBean("dao");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.getBean("jdbcTemplate");
		jdbcTemplate.execute("DELETE FROM `WB_TAG` WHERE `VALUE` LIKE 'TEST%'");
		List<Tag> tags = new ArrayList<Tag>();			
		for (int j = 0; j < 10; j++) {				
			Tag tag = new Tag(null, "tid" + j, "TESTvalue" + j, "weight" +j, null);
			tags.add(tag);
		}
		
		List<Tag> result = dao.batchInsertTag(tags);
		Assert.assertTrue(result.size() == 10);
		// further verification
		List<Tag> loadedTags = dao.loadTagByValueLike("TEST");
		Assert.assertTrue(loadedTags.size() == 10);
		Collections.sort(loadedTags, new TagComparator());
		for(int j=0; j < 10; j++) {
			Assert.assertEquals(tags.get(j), loadedTags.get(j));
		}
	}
	
	
	// @Test
	public void testInsertSource() {
		
		Dao dao = (Dao) SpringUtil.getBean("dao");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.getBean("jdbcTemplate");
		jdbcTemplate.execute("DELETE FROM `WB_TAG` WHERE `VALUE` LIKE 'TEST%'");
		
//		public Source(Long id, String url, String relationship, String name) {
//			this.id = id;
//			this.url = url;
//			this.relationship = relationship;
//			this.name = name;
//		}
		
		Source source = new Source(null, null, "null", "TESTname");
		dao.insertSource(source);
		// List<Source> sourceList = SourceConstant.getSourceId(source);
	}
	
}

class UserComparator implements Comparator<User> {
	@Override
	public int compare(User u1, User u2) {

		return new Long(u1.getId()).compareTo(u2.getId());
	}
}

class TagComparator implements Comparator<Tag> {
	@Override
	public int compare(Tag t1, Tag t2) {

		return new Long(t1.getId()).compareTo(t2.getId());
	}
}