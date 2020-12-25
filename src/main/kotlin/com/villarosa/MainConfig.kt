package com.villarosa

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
                       transactionManagerRef = "transactionManager",
                       basePackages = ["com.villarosa.repository"])

open class MainConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    open fun dataSource(): DataSource? {
        return DataSourceBuilder.create().build()
    }

    @Bean(name = ["entityManagerFactory"])
    @Primary
    open fun entityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean? {
        return builder.dataSource(dataSource())
                .packages("com.villarosa.repository")
                .persistenceUnit("persistenceUnit")
                .build()
    }

    @Bean(name = ["transactionManager"])
    open fun transactionManager(@Qualifier(value = "entityManagerFactory") emf: EntityManagerFactory?): PlatformTransactionManager? {
        return JpaTransactionManager(emf!!)
    }
}