package com.mills.quarters;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    private static Class<Application> applicationClass = Application.class;

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:8011");
            }
        };
    }


    public
    @Bean
    Mongo mongo() throws Exception {
        //TODO: configure this based on whether we're on Openshift or Local
        return new Mongo("localhost");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        if (System.getenv("OPENSHIFT_MONGODB_DB_HOST") != null) {
            String openshiftMongoDbHost = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
            int openshiftMongoDbPort = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
            String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
            String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
            Mongo mongo = new MongoClient(openshiftMongoDbHost, openshiftMongoDbPort);
            UserCredentials userCredentials = new UserCredentials(username, password);
            String databaseName = System.getenv("OPENSHIFT_APP_NAME");
            MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName, userCredentials);
            MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
            return mongoTemplate;

        } else {
            String databaseName = "performances";
            return new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), databaseName));
        }
    }
}