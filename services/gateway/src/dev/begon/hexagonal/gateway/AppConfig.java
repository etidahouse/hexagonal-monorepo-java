package dev.begon.hexagonal.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.mongodb.client.MongoDatabase;

import dev.begon.mongodb.sdk.MongoStorage;
import dev.begon.mongodb.sdk.MongoUsersQueries;

@Configuration
public class AppConfig {

    @Value("${MONGODB_USER}")
    private String mongodbUser;

    @Value("${MONGODB_PASSWORD}")
    private String mongodbPassword;

    @Value("${MONGODB_HOSTNAME}")
    private String mongodbHostname;

    @Value("${MONGODB_DATABASE_NAME}")
    private String mongodbDatabaseName;

    @Value("${MONGODB_PORT}")
    private int mongodbPort;

    @Bean
    public MongoDatabase mongoDatabase() {
        return MongoStorage.database(mongodbHostname, mongodbPort, mongodbDatabaseName, mongodbUser, mongodbPassword);
    }

    @Bean
    public MongoUsersQueries mongoUsersQueries(MongoDatabase mongoDatabase) {
        return new MongoUsersQueries(mongoDatabase());
    }

}
