package com.ranger.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ranger.common.Status;
import com.ranger.common.User;
import com.ranger.common.Tag;
import com.ranger.util.Bool;
import com.ranger.common.Visible;

public class Dao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<User> loadUserByNameLike(String name) {
		String sql = "SELECT `ID`, `UID`, `SCREEN_NAME`, `NAME`, `PROVINCE`, `CITY`, `LOCATION`, `DESCRIPTION`, `URL`, `PROFILE_IMAGE_URL`, " +
				"`USER_DOMAIN`, `GENDER`, `FOLLOWERS_COUNT`, `FRIENDS_COUNT`, `STATUSES_COUNT`, `FAVOURITES_COUNT`, `CREATE_AT`, `FOLLOWING`, `VERIFIED`, " +
				"`VERIFIED_TYPE`, `ALLOW_ALL_ACT_MSG`, `ALLOW_ALL_COMMENT`, `FOLLOW_ME`, `AVATAR_LARGE`, `ONLINE_STATUS`, `BI_FOLLOWERS_COUNT`, `REMARK`, " +
				"`LANG`, `VERIFIED_REASON`, `WEIHAO`, `STATUS_ID` FROM WB_USER WHERE NAME LIKE ?";
		return jdbcTemplate.query(sql, new String[]{ "%" + name + "%" }, new UserRowMapper());
	}
	/*
	 * batch insert users return the same user object with PK
	 */
	public List<User> batchInsertUser(List<User> users) {
		// batch insert users
		List<Long> ids = jdbcTemplate.execute(new UserPreparedStatementCreator(users), new GeneratedKeysPreparedStatementCallback());
		// populate tags with the ids of user
		List<Tag> allTags = new ArrayList<Tag>();
		for(int i=0; i<users.size(); i++ ) {
			User u = users.get(i);
			u.setId(ids.get(i));
			
			List<Tag> tags = u.getTags();
			for(Tag tag : tags) {
				tag.setUserId(ids.get(i));
			}
			allTags.addAll(tags);
		}
		
		batchInsertTag(allTags);
		 return users;
	}

	/*
	 * batch insert users return the same user object with PK
	 */
	public List<Tag> batchInsertTag(List<Tag> tags) {
		// batch insert tags
		List<Long> ids = jdbcTemplate.execute(new TagPreparedStatementCreator(tags), new GeneratedKeysPreparedStatementCallback());			
		// populate tags with the generaed PK			
		for(int i=0; i<tags.size(); i++ ) {
			Tag t = tags.get(i);
			t.setId(ids.get(i));
		}
		return tags;
	}
	
	public Visible insertVisible(Visible visible) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new VisiblePreparedStatementCreator(visible), holder);
		visible.setId(holder.getKey().longValue());
		return visible;
	}
	
	public List<Visible> loadAllVisible() {
		String sql = "SELECT `ID`, `TYPE`, `LIST_ID`) FROM WB_VISIBLE";
		return jdbcTemplate.query(sql, new VisibleRowMapper());
	}
	
	public List<Status> batchInsertStatus(List<Status> statuses) {
		// batch insert status
		List<Long> ids = jdbcTemplate.execute(new StatusPreparedStatementCreator(statuses), new GeneratedKeysPreparedStatementCallback());			
		// populate statuses with the generaed PK			
		for(int i=0; i<statuses.size(); i++ ) {
			Status s = statuses.get(i);
			s.setId(ids.get(i));
		}
		return statuses;
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
			ps.setString(17, Bool.getBool(u.isFollowing()).getValue());
			ps.setString(18, Bool.getBool(u.isVerified()).getValue());
			ps.setInt(19, u.getVerifiedType());			
			ps.setString(20, Bool.getBool(u.isAllowAllActMsg()).getValue());
			ps.setString(21, Bool.getBool(u.isAllowAllComment()).getValue());
			ps.setString(22, Bool.getBool(u.isFollowMe()).getValue());
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
				Bool.getBoolean(rs.getString(18)),
				Bool.getBoolean(rs.getString(19)),
				rs.getInt(20),
				Bool.getBoolean(rs.getString(21)),
				Bool.getBoolean(rs.getString(22)),
				Bool.getBoolean(rs.getString(23)),
				rs.getString(24),
				rs.getInt(25),
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

class TagPreparedStatementCreator implements PreparedStatementCreator {
		private List<Tag> tags;
			public TagPreparedStatementCreator(List<Tag> tags) {
			this.tags = tags;
		}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String sql = "INSERT INTO WB_TAG(`TID`, `VALUE`, `WEIGHT`, `USER_ID`) VALUES(?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

		for (Tag t : tags) {
			ps.setString(1, t.getTid());
			ps.setString(2, t.getValue());
			ps.setString(3, t.getWeight());
			ps.setLong(4, t.getUserId());
			ps.addBatch();
		}
		return ps;
	}
}


class TagRowMapper implements RowMapper<Tag> {
	@Override
	public Tag mapRow(ResultSet rs, int rowIdx) throws SQLException {		
		Tag t = new Tag(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5));
		return t;
	}
}


class GeneratedKeysPreparedStatementCallback implements PreparedStatementCallback<List<Long>> {
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

class VisiblePreparedStatementCreator implements PreparedStatementCreator {
	private Visible visible;
		public VisiblePreparedStatementCreator(Visible visible) {
		this.visible = visible;
	}
		
	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String sql = "INSERT INTO WB_VISIBLE(`TYPE`, `LIST_ID`) VALUES(?,?)";
		PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, visible.getType());
		ps.setInt(2, visible.getListId());
		return ps;
	}
}

class VisibleRowMapper implements RowMapper<Visible> {
	@Override
	public Visible mapRow(ResultSet rs, int rowIdx) throws SQLException {		
		Visible v = new Visible(rs.getLong(1), rs.getInt(2), rs.getInt(3));
		return v;
	}
}


class StatusPreparedStatementCreator implements PreparedStatementCreator {
	private List<Status> satuses;
		public StatusPreparedStatementCreator(List<Status> satuses) {
		this.satuses = satuses;
	}
		
@Override
public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
	String sql = "INSERT INTO WB_STATUS(`STID`,`USER_ID`,`CREATE_AT`,`MID`,`ID_STR`,`TEXT`,`SR_ID`,`FAVORRITED`,`TRUNCATED`,`IN_REPLY_TO_STATUS_ID`," +
			"`IN_REPLY_TO_USER_ID`,`IN_REPLY_TO_SCREEN_NAME`,`THUNMB_NAIL_PIC`,`B_MIDDLE_PIC`,`RETWEETED_STATUS_ID`,`GEO`," + 
			"`LATITUDE`,`LONGITUDE`,`REPORTS_COUNT`,`COMMENTS_COUNT`,`ANNOTATIONS`,`M_LEVEL`,`V_ID`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

		for (Status s : satuses) {
			ps.setString(1, s.getStid());
			ps.setLong(2, s.getUserId());
			if(s.getCreatedAt() != null) {
				ps.setDate(3, new Date(s.getCreatedAt().getTime()));
			}
			ps.setString(4, s.getMid());
			ps.setLong(5, s.getIdstr());		
			ps.setString(6, s.getText());
			ps.setLong(7, s.getSourceId());
			ps.setString(8, Bool.getBool(s.isFavorited()).getValue());
			ps.setString(9, Bool.getBool(s.isTruncated()).getValue());
			ps.setLong(10, s.getInReplyToStatusId());
			ps.setLong(11, s.getInReplyToUserId());
			ps.setString(12, s.getInReplyToScreenName());
			ps.setString(13, s.getThumbnailPic());
			ps.setString(14, s.getBmiddlePic());
			ps.setString(15, s.getOriginalPic());
			ps.setLong(16, s.getRetweetedStatusId());		
			ps.setString(17, s.getGeo());
			ps.setDouble(18, s.getLatitude());
			ps.setDouble(19, s.getLongitude());
			ps.setInt(20, s.getRepostsCount());
			ps.setInt(21, s.getCommentsCount());
			ps.setString(22, s.getAnnotations());
			ps.setInt(23, s.getMlevel());
			ps.setLong(24, s.getVisibleId());
			ps.addBatch();
		}
		return ps;
	}
}

class StatusRowMapper implements RowMapper<Status> {
	
	@Override
	public Status mapRow(ResultSet rs, int rowIdx) throws SQLException {		
		Status s = new Status(  
				rs.getLong(1), 
				rs.getString(2),
				rs.getLong(3), 
				rs.getDate(4), 
				rs.getString(5),
				rs.getLong(6),
				rs.getString(7),
				rs.getLong(8), 
				Bool.getBoolean(rs.getString(9)),
				Bool.getBoolean(rs.getString(10)),
				rs.getLong(11),
				rs.getLong(12), 
				rs.getString(13), 
				rs.getString(14),
				rs.getString(15), 
				rs.getString(16),
				rs.getLong(17), 
				rs.getString(18),
				rs.getDouble(19),
				rs.getDouble(20),
				rs.getInt(21),
				rs.getInt(22),
				rs.getString(23),
				rs.getInt(24),
				rs.getLong(25));
		return s;
	}
}



















