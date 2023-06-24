package com.dyonyon.spring.basics.springin5steps.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component
/*
// 1-2. DAO의 Scope가 prototype일 때, DAO는 다른 Bean이지만, @Autowired된 JDBCConnection은 같은 Bean이다. 
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
*/
@Repository // **Data Layer라서 Repository! Component도 사용 가능
public class PersonDAO {
	@Autowired
	JdbcConnection jdbcConnection;

	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(JdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	} 
	
}
