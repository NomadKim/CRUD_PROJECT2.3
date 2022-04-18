package webres.web.configuration;

import org.hibernate.boot.model.relational.Database;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import webres.web.model.User;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan("webres")
public class HiberConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public EntityManagerFactory getEntityFactoryBean(){

        Properties props=new Properties();
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");


        LocalContainerEntityManagerFactoryBean localEntityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        localEntityManagerFactoryBean.setDataSource(getDataSource());
        localEntityManagerFactoryBean.setJpaVendorAdapter(getVendorAdapter());
        localEntityManagerFactoryBean.setJpaProperties(props);
        localEntityManagerFactoryBean.setPackagesToScan("webres");
        localEntityManagerFactoryBean.setPersistenceUnitName(User.class.getName());
        localEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localEntityManagerFactoryBean.afterPropertiesSet();
        return localEntityManagerFactoryBean.getObject();
    }

    @Bean
    public JpaVendorAdapter getVendorAdapter(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;

    }
}
