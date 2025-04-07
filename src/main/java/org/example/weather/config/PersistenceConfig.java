package org.example.weather.config;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.Properties;

@Configuration
public class PersistenceConfig {

    @Bean
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/weather_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("MyPassword2025"); // заменишь на своё

        // Прогон миграций Flyway при запуске
        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load()
                .migrate();

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        var sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("org.example.weather.model");

        var hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.put("hibernate.show_sql", "true");
        hibernateProperties.put("hibernate.format_sql", "true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "validate"); // мы применяем миграции, а не автогенерацию

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        var txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}

