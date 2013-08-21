package com.ranger.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import com.ranger.common.People;
public class Dao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int[] batchInsert(List<People> people) {
		String sql = "INSERT INTO PEOPLE(NAME, GENDER, AGE, EMAIL) values(?, ?, ?, ?)";
		int[] argTypes = new int[] {Types.VARCHAR, Types.CHAR, Types.INTEGER, Types.VARCHAR};
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for(People p : people) {
			Object[] row = new Object[] {p.getName(), p.getGender(), p.getAge(), p.getEmail() };
			batchArgs.add(row);
		}
		return jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
	}
	
}
