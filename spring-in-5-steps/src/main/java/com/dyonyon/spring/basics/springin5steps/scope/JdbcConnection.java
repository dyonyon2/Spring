package com.dyonyon.spring.basics.springin5steps.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
/*
// 1-3. JDBC의 Scope가 prototype일 때, DAO는 같은 Bean이고, @Autowired된 JDBCConnection도 같은 Bean이다.
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
*/
// 1-4. JDBC의 Scope가 prototype이고 proxy를 사용일 때, 사용되는 모든 곳에서 프록시를 통해 새로운 JDBC를 만든다.
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JdbcConnection {
	public JdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
