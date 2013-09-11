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

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


import com.ranger.common.DataField;
import com.ranger.common.DataObject;
import com.ranger.common.SQLConstant;
import com.ranger.common.Source;
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
		String sql = SQLConstant.USER_SELECT + "WHERE `NAME` LIKE ?";
		return jdbcTemplate.query(sql, new String[]{ "%" + name + "%" }, new UserRowMapper());
	}
	/*
	 * batch insert users return the same user object with PK
	 */
	public List<User> batchInsertUser(List<User> users) {
		// batch insert users
		List<DataObject> dataObjectList = new ArrayList<DataObject>();
		dataObjectList.addAll(users);
		List<Long> ids = jdbcTemplate.execute(new WBPreparedStatementCreator(SQLConstant.USER_INSERT, dataObjectList), new GeneratedKeysPreparedStatementCallback());
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
		List<DataObject> dataObjectList = new ArrayList<DataObject>();
		dataObjectList.addAll(tags);
		List<Long> ids = jdbcTemplate.execute(new WBPreparedStatementCreator(SQLConstant.TAG_INSERT, dataObjectList), new GeneratedKeysPreparedStatementCallback());
		// populate tags with the generaed PK			
		for(int i=0; i<tags.size(); i++ ) {
			Tag t = tags.get(i);
			t.setId(ids.get(i));
		}
		return tags;
	}
	
	public List<Tag> loadTagByValueLike(String value) {
		String sql = SQLConstant.TAG_SELECT + "WHERE `VALUE` LIKE ?";
		return jdbcTemplate.query(sql, new String[]{ "%" + value + "%" }, new TagRowMapper());
	}
	
	public Visible insertVisible(Visible visible) {
		List<DataObject> dataObjectList = new ArrayList<DataObject>();
		dataObjectList.add(visible);
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new WBPreparedStatementCreator(SQLConstant.VISIBLE_INSERT, dataObjectList), holder);
		visible.setId(holder.getKey().longValue());
		return visible;
	}
	
	public List<Visible> loadAllVisible() {
		return jdbcTemplate.query(SQLConstant.VISIBLE_SELECT, new VisibleRowMapper());
	}
	
	
	public Source insertSource(Source source) {
		KeyHolder holder = new GeneratedKeyHolder();
		List<DataObject> dataObjectList = new ArrayList<DataObject>();
		dataObjectList.add(source);
		jdbcTemplate.update(new WBPreparedStatementCreator(SQLConstant.SOURCE_INSERT, dataObjectList), holder);
		source.setId(holder.getKey().longValue());
		return source;
	}
	
	public List<Source> loadAllSource() {
		return jdbcTemplate.query(SQLConstant.SOURCE_SELECT, new SourceRowMapper());
	}
	
	public List<Status> batchInsertStatus(List<Status> statuses) {
		// batch insert status
		List<DataObject> dataObjectList = new ArrayList<DataObject>();
		dataObjectList.addAll(statuses);
		List<Long> ids = jdbcTemplate.execute(new WBPreparedStatementCreator(SQLConstant.STATUS_INSERT, dataObjectList), new GeneratedKeysPreparedStatementCallback());			
		// populate statuses with the generated PK			
		for(int i=0; i<statuses.size(); i++ ) {
			Status s = statuses.get(i);
			s.setId(ids.get(i));
		}
		return statuses;
	}
}

class WBPreparedStatementCreator implements PreparedStatementCreator {

	private String sql;
	private List<DataObject> dataObjectList;
	
	public WBPreparedStatementCreator(String sql, List<DataObject> dataObjectList) {
		this.sql = sql;
		this.dataObjectList = dataObjectList;
	}
	
	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		for (DataObject dataObj : dataObjectList) {
			for(DataField dataField : dataObj.getDataFields()) {
				switch(dataField.getType()) {
				case LONG : setLong(dataField, ps); break;
				case STRING: setString(dataField, ps); break;
				case INTEGER: setInteget(dataField, ps); break;
				case DATE: setDate(dataField, ps); break;
				case BOOLEAN: setBoolean(dataField, ps); break;
				}
			}
			ps.addBatch();
		}
		
		return ps;
	}
	
	
	private void setLong(DataField dataField, PreparedStatement ps) throws SQLException {
		if(dataField.getValue() != null) {
			ps.setLong(dataField.getIndex(), (Long)dataField.getValue());
		} else {
			ps.setNull(dataField.getIndex(), Types.BIGINT);
		}
	}
	
	private void setString(DataField dataField, PreparedStatement ps) throws SQLException {
		ps.setString(dataField.getIndex(), StringUtils.trimToEmpty((String)dataField.getValue()));
	}
	
	private void setInteget(DataField dataField, PreparedStatement ps) throws SQLException {
		if(dataField.getValue() != null) {
			ps.setInt(dataField.getIndex(), (Integer)dataField.getValue());
		} else {
			ps.setInt(dataField.getIndex(), -1);
			// ps.setNull(dataField.getIndex(), Types.INTEGER);
		}
	}
	
	private void setDate(DataField dataField, PreparedStatement ps) throws SQLException {
		if(dataField.getValue() != null) {
			ps.setDate(dataField.getIndex(), new Date(((java.util.Date)dataField.getValue()).getTime()));
		} else {
			ps.setNull(dataField.getIndex(), Types.DATE);
		}
	}
	
	private void setBoolean(DataField dataField, PreparedStatement ps) throws SQLException {
		ps.setBoolean(dataField.getIndex(), (Boolean)dataField.getValue());
	}
}

class UserRowMapper implements RowMapper<User> {

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

class VisibleRowMapper implements RowMapper<Visible> {
	@Override
	public Visible mapRow(ResultSet rs, int rowIdx) throws SQLException {		
		Visible v = new Visible(rs.getLong(1), rs.getInt(2), rs.getInt(3));
		return v;
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

class SourceRowMapper implements RowMapper<Source> {
	@Override
	public Source mapRow(ResultSet rs, int rowIdx) throws SQLException {		
		Source s = new Source(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
		return s;
	}
}















