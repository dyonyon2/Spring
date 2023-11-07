package com.dyonyon.spring.basics.componentscan;

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
public class ComponentDAO {
	@Autowired
	ComponentJdbcConnection jdbcConnection;

	public ComponentJdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(ComponentJdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	} 
	
}
