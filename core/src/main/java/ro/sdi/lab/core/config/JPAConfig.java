package ro.sdi.lab.core.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import ro.sdi.lab.core.repository.custom.CustomRepository;
import ro.sdi.lab.core.repository.custom.CustomRepositoryNative;
import ro.sdi.lab.core.repository.custom.CustomRepositoryJPQL;
import ro.sdi.lab.core.repository.custom.CustomRepositoryCriteria;
import ro.sdi.lab.core.service.Service;

@Configuration
@EnableJpaRepositories({"ro.sdi.lab.core.repository"})
@EnableTransactionManagement
//@EnableCaching
public class JPAConfig
{
    public static final Logger log = LoggerFactory.getLogger(JPAConfig.class);

    @Value("${db.jdbcUrl}")
    private String jdbcUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.generateDDL}")
    private Boolean generateDDL;

    @Value("${db.configuration}")
    private String configuration;

    public Database database;

    @Profile("prod")
    @Bean
    public DataSource dataSource()
    {
        log.trace("Using database from properties file");
        database = Database.POSTGRESQL;
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        try
        {
            DriverManager.registerDriver(new org.postgresql.Driver());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Profile("dev")
    @Bean
    public DataSource mySQLDataSource()
    {
        log.trace("Using MySQL database for testing");
        database = Database.MYSQL;
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/MovieRentalDB?useSSL=false");
        config.setUsername("root");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        try
        {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new HikariDataSource(config);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource)
    {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(database);
        vendorAdapter.setGenerateDdl(generateDDL);
        vendorAdapter.setShowSql(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ro.sdi.lab.core.model");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public EntityManager entityManager(DataSource dataSource)
    {
        return entityManagerFactory(dataSource).createEntityManager();
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory)
    {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory);
        return manager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator()
    {
        return new HibernateExceptionTranslator();
    }

    @Bean
    Validator validator()
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    CustomRepository customRepository()
    {
        log.trace("Custom repository configuration: " + configuration);
        switch(configuration)
        {
            case "JPQL":
            return new CustomRepositoryJPQL();
            case "Criteria":
                return new CustomRepositoryCriteria();
            case "Native":
                return new CustomRepositoryNative();
        }
        throw new RuntimeException("Repository configuration invalid!");
    }
}
