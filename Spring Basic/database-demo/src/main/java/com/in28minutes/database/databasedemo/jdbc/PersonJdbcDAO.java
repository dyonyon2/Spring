package com.in28minutes.database.databasedemo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.in28minutes.database.databasedemo.entity.Person;

@Repository
public class PersonJdbcDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	class PersonRowMapper implements RowMapper<Person>{
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Person person = new Person();
			person.setId(rs.getInt("id"));
			person.setName(rs.getString("name"));
			person.setLocation(rs.getString("location"));
			person.setBirthDate(rs.getTimestamp("birth_date"));
			return person;
		}
	}
	
	//select * from person
//	public List<Person> findAll(){
//		return jdbcTemplate.query("select * from person", 
//				new BeanPropertyRowMapper<Person>(Person.class));
//	}
	
	public List<Person> findAll(){
		return jdbcTemplate.query("select * from person", 
				new PersonRowMapper());
	}
	
	public Person findById(int id){
		return jdbcTemplate.queryForObject
				("select * from person where id =?", new Object[] {id}, 
				new BeanPropertyRowMapper<Person>(Person.class));
	}
	public int deleteById(int id){
		return jdbcTemplate.update
				("Delete From person where id =?", new Object[] {id});
	}
	public int insert(Person person){
		return jdbcTemplate.update
				("INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) VALUES(?,?,?,?)", new Object[] {person.getId(), person.getName(), person.getLocation(), new Timestamp(person.getBirthDate().getTime())});
	}
	public int update(Person person){
		return jdbcTemplate.update
				("UPDATE PERSON SET NAME=?,LOCATION=?,BIRTH_DATE=?"
						+ "where id = ? ", new Object[] {person.getName(), person.getLocation(), new Timestamp(person.getBirthDate().getTime()),person.getId(),});
	}
	
}
