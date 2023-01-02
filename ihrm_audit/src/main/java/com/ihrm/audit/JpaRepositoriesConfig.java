package com.ihrm.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
	basePackages = "com.ihrm.audit.dao",
	entityManagerFactoryRef = "ihrmEntityManager",
	transactionManagerRef = "ihrmTransactionManager"
)
public class JpaRepositoriesConfig {

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("ihrmDataSource")
	private DataSource ihrmDataSource;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean ihrmEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(ihrmDataSource);
		em.setPackagesToScan(new String[] { "com.ihrm.audit.entity" });
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto")); //自动创建|更新|验证数据库表结构
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect")); //指定数据库的sql方言
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Primary
	@Bean
	public PlatformTransactionManager ihrmTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory( ihrmEntityManager().getObject());
		return transactionManager;
	}
}
