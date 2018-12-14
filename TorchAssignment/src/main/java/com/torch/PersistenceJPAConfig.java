package com.torch;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig{
	
	@Value(value="${spring.datasource.url}")
	private String connectionUrl;
	@Value(value="${spring.datasource.driver-class-name}")
	private String driverClass;
	@Value(value="${spring.datasource.username}")
	private String username;
	@Value(value="${spring.datasource.password}")
	private String password;
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(connectionUrl);
		dataSource.setUsername( username );
		dataSource.setPassword( password );
		return dataSource;
	}
}
