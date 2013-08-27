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


import com.ranger.common.People;
import com.ranger.common.Tag;

public class Dao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<People> loadPeopleByNameLike(String name) {
		String sql = "SELECT ID, NAME, GENDER, AGE, EMAIL FROM PEOPLE WHERE NAME LIKE ?";
		return jdbcTemplate.query(sql, new String[]{ "%" + name + "%" }, new PeopleRowMapper());
	}
	
	public List<People> batchInsertPeople(List<People> people) {
		// batch insert people
		List<Long> ids = jdbcTemplate.execute(new PeoplePreparedStatementCreator(people), new PeoplePreparedStatementCallback());
		// populate tags with the ids of people
		List<Tag> allTags = new ArrayList<Tag>();
		for(int i=0; i<people.size(); i++ ) {
			People p = people.get(i);
			p.setId(ids.get(i));
			List<Tag> tags = p.getTags();
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
		 return people;
	}
}

class PeoplePreparedStatementCreator implements PreparedStatementCreator {

	private List<People> _people;

	public PeoplePreparedStatementCreator(List<People> people) {
		this._people = people;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String sql = "INSERT INTO PEOPLE(NAME, GENDER, AGE, EMAIL) values(?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

		for (People p : _people) {
			ps.setString(1, p.getName());
			ps.setString(2, String.valueOf(p.getGender()));
			ps.setInt(3, p.getAge());
			ps.setString(4, p.getEmail());
			ps.addBatch();
		}
		return ps;
	}
}

class PeoplePreparedStatementCallback implements PreparedStatementCallback<List<Long>> {

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

class PeopleRowMapper implements RowMapper<People> {

	
	
	@Override
	public People mapRow(ResultSet rs, int rowIdx) throws SQLException {		
		People p = new People(rs.getLong(1), rs.getString(2),rs.getString(3).charAt(0), rs.getInt(4), rs.getString(5));
		return p;
	}
	
}