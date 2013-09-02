package com.ranger.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import weibo4j.model.Status;


import com.ranger.common.User;
import com.ranger.common.Tag;

public class Dao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<User> loadUserByNameLike(String name) {
		String sql = "SELECT ID, `UID`, `SCREEN_NAME`, `NAME`, `PROVINCE`, `CITY`, `LOCATION`, `DESCRIPTION`, `URL`, `PROFILE_IMAGE_URL`, " +
				"`USER_DOMAIN`, `GENDER`, `FOLLOWERS_COUNT`, `FRIENDS_COUNT`, `STATUSES_COUNT`, `FAVOURITES_COUNT`, `CREATE_AT`, `FOLLOWING`, `VERIFIED`, " +
				"`VERIFIED_TYPE`, `ALLOW_ALL_ACT_MSG`, `ALLOW_ALL_COMMENT`, `FOLLOW_ME`, `AVATAR_LARGE`, `ONLINE_STATUS`, `BI_FOLLOWERS_COUNT`, `REMARK`, " +
				"`LANG`, `VERIFIED_REASON`, `WEIHAO`, `STATUS_ID`) WHERE NAME LIKE ?";
		return jdbcTemplate.query(sql, new String[]{ "%" + name + "%" }, new UserRowMapper());
	}
	
	public List<User> batchInsertUser(List<User> users) {
		// batch insert people
		List<Long> ids = jdbcTemplate.execute(new UserPreparedStatementCreator(users), new UserPreparedStatementCallback());
		// populate tags with the ids of people
		List<Tag> allTags = new ArrayList<Tag>();
		for(int i=0; i<users.size(); i++ ) {
			User u = users.get(i);
			u.setId(ids.get(i));
			
			List<Tag> tags = u.getTags();
			for(Tag tag : tags) {
				tag.setPeopleId(ids.get(i));
			}
			allTags.addAll(tags);
		}
		
		// batch insert tags
		String sql = "INSERT INTO TAG(NAME, PEOPLE_ID) values(?, ?)";
		int[] argTypes = new int[] {Types.VARCHAR, Types.BIGINT};
		List<Object[]> batchTags = new ArrayList<Object[]>();
		for(Tag tag : allTags) {
			Object[] row = new Object[] { tag.getName(), tag.getPeopleId()};
			batchTags.add(row);
		 }
		 jdbcTemplate.batchUpdate(sql, batchTags, argTypes);
		 return users;
	}
}

class UserPreparedStatementCreator implements PreparedStatementCreator {
	private List<User> users;
	public UserPreparedStatementCreator(List<User> users) {
		this.users = users;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String sql = "INSERT INTO WB_USER(`UID`, `SCREEN_NAME`, `NAME`, `PROVINCE`, `CITY`, `LOCATION`, `DESCRIPTION`, `URL`, `PROFILE_IMAGE_URL`, " +
				"`USER_DOMAIN`, `GENDER`, `FOLLOWERS_COUNT`, `FRIENDS_COUNT`, `STATUSES_COUNT`, `FAVOURITES_COUNT`, `CREATE_AT`, `FOLLOWING`, `VERIFIED`, " +
				"`VERIFIED_TYPE`, `ALLOW_ALL_ACT_MSG`, `ALLOW_ALL_COMMENT`, `FOLLOW_ME`, `AVATAR_LARGE`, `ONLINE_STATUS`, `BI_FOLLOWERS_COUNT`, `REMARK`, " +
				"`LANG`, `VERIFIED_REASON`, `WEIHAO`, `STATUS_ID`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

		for (User u : users) {
			ps.setString(1, u.getUid());
			ps.setString(2, u.getScreenName());
			ps.setString(3, u.getName());
			ps.setInt(4, u.getProvince());
			ps.setInt(5, u.getCity());
			ps.setString(6, u.getLocation());
			ps.setString(7, u.getDescription());
			ps.setString(8, u.getUrl());
			ps.setString(9, u.getProfileImageUrl());
			ps.setString(10, u.getUserDomain());
			ps.setString(11, u.getGender());
			ps.setInt(12, u.getFollowersCount());
			ps.setInt(13, u.getFriendsCount());
			ps.setInt(14, u.getStatusesCount());
			ps.setInt(15, u.getFavouritesCount());
			if(u.getCreatedAt() != null) {
				ps.setDate(16, new Date(u.getCreatedAt().getTime()));
			}
			if(u.isFollowing()) {
				ps.setString(17, "Y");
			} else {
				ps.setString(17, "N");
			}
			if(u.isVerified()) {
				ps.setString(18, "Y");
			} else {
				ps.setString(18, "N");
			}
			ps.setInt(19, u.getVerifiedType());
			
			if(u.isAllowAllActMsg()) {
				ps.setString(20, "Y");
			} else {
				ps.setString(20, "N");
			}
			
			if(u.isAllowAllComment()) {
				ps.setString(21, "Y");
			} else {
				ps.setString(21, "N");
			}
			
			if(u.isFollowMe()) {
				ps.setString(22, "Y");
			} else {
				ps.setString(22, "N");
			}
			ps.setString(23, u.getAvatarLarge());
			ps.setInt(24, u.getOnlineStatus());
			ps.setInt(25, u.getBiFollowersCount());
			ps.setString(26, u.getRemark());
			ps.setString(27, u.getLang());
			ps.setString(28, u.getVerifiedReason());
			ps.setString(29, u.getWeihao());
			ps.setString(30, u.getStatusId());
			ps.addBatch();
		}
		return ps;
	}
}

class UserPreparedStatementCallback implements PreparedStatementCallback<List<Long>> {
	@Override
	public List<Long> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
		List<Long> ids = new ArrayList<Long>();
		ps.executeBatch();
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			ids.add(rs.getLong(1));
		}
		return ids;
	}
}

class UserRowMapper implements RowMapper<User> {

	String sql = "SELECT ID, `UID`, `SCREEN_NAME`, `NAME`, `PROVINCE`, `CITY`, `LOCATION`, `DESCRIPTION`, `URL`, `PROFILE_IMAGE_URL`, " +
			"`USER_DOMAIN`, `GENDER`, `FOLLOWERS_COUNT`, `FRIENDS_COUNT`, `STATUSES_COUNT`, `FAVOURITES_COUNT`, `CREATE_AT`, `FOLLOWING`, `VERIFIED`, " +
			"`VERIFIED_TYPE`, `ALLOW_ALL_ACT_MSG`, `ALLOW_ALL_COMMENT`, `FOLLOW_ME`, `AVATAR_LARGE`, `ONLINE_STATUS`, `BI_FOLLOWERS_COUNT`, `REMARK`, " +
			"`LANG`, `VERIFIED_REASON`, `WEIHAO`, `STATUS_ID`) WHERE NAME LIKE ?";
		
	@Override
	public User mapRow(ResultSet rs, int rowIdx) throws SQLException {		
		User u = new User(
				rs.getLong(1), 
				rs.getString(2),
				rs.getString(3), 
				rs.getString(4), 
				rs.getInt(5),
				rs.getInt(6),
				rs.getString(7),
				rs.getString(8), 
				rs.getString(9), 
				rs.getString(10), 
				rs.getString(11),
				rs.getString(12), 
				rs.getInt(13), 
				rs.getInt(14),
				rs.getInt(15), 
				rs.getInt(16),
				rs.getDate(17), 
				rs.getString(18).equalsIgnoreCase("Y"),
				rs.getString(19).equalsIgnoreCase("Y"),
				rs.getInt(20),rs.getString(21).equalsIgnoreCase("Y"),
				rs.getString(22).equalsIgnoreCase("Y"),
				rs.getString(23).equalsIgnoreCase("Y"),
				rs.getString(24),
				rs.getInt(25),
				null, // latest status
				rs.getInt(26), 
				rs.getString(27),
				rs.getString(28),
				rs.getString(29),
				rs.getString(30), 
				rs.getString(31),
				null);
		return u;
	}
}