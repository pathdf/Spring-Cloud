package com.pankaj.spring.cloud.service.common.db;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("${repository.basepackage}")
public class DatasourceConfig{

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("${db.classname}");
		dataSource.setUrl("${db.url}");
		dataSource.setUsername("${db.username}");
		dataSource.setPassword("${db.password}");
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.current_session_context_class",
				"org.springframework.orm.hibernate5.SpringSessionContext");
		properties.put("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
		properties.put("hibernate.c3p0.min_size", "10");
		properties.put("hibernate.c3p0.max_size", 50);
		properties.put("hibernate.c3p0.acquire_increment", "5");
		properties.put("hibernate.c3p0.idle_test_period", "600");
		properties.put("hibernate.c3p0.timeout", "5000");
		return properties;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource ds)
			throws PropertyVetoException {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(ds);
		entityManagerFactory.setPackagesToScan(new String[] { "${entity.basepackage}" });
		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setJpaProperties(hibernateProperties());
		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}