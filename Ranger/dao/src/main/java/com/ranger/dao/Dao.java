package com.ranger.dao;


import java.sql.Connection;
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
		String sql = "SELECT ID, NAME, GENDER, AGE, EMAIL FROM PEOPLE WHERE NAME LIKE ?";
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
		String sql = "INSERT INTO PEOPLE(NAME, GENDER, AGE, EMAIL) values(?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

		for (User u : users) {
			ps.setString(1, u.getName());
			ps.setString(2, String.valueOf(u.getGender()));
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

	@Override
	public User mapRow(ResultSet rs, int rowIdx) throws SQLException {		
		User u = new User();
		return u;
	}
	
}