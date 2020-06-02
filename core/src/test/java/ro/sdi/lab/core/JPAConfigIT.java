package ro.sdi.lab.core;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import ro.sdi.lab.core.repository.custom.CustomRepository;
import ro.sdi.lab.core.repository.custom.CustomRepositoryCriteria;
import ro.sdi.lab.core.repository.custom.CustomRepositoryJPQL;
import ro.sdi.lab.core.repository.custom.CustomRepositoryNative;
import ro.sdi.lab.core.validation.ClientValidator;
import ro.sdi.lab.core.validation.MovieValidator;

@Configuration
@EnableJpaRepositories("ro.sdi.lab.core.repository")
@EnableTransactionManagement
public class JPAConfigIT {

    @Value("${db.jdbcURL}")
    private String jdbcURL;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;


    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcURL);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.H2);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ro.sdi.lab.core.model");
        factory.setDataSource(dataSource());
        factory.getJpaPropertyMap().put("hibernate.generate_statistics", true);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    CustomRepository customRepository()
    {
        return new CustomRepositoryJPQL();
    }

    @Bean
    Validator validator()
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    MovieValidator movieValidator()
    {
        return new MovieValidator();
    }

    @Bean
    ClientValidator clientValidator()
    {
        return new ClientValidator();
    }

    @Bean
    DateTimeFormatter dateFormatter()
    {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    }
}