package dev.begon.mongodb.sdk;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ServerAddress;

import org.bson.UuidRepresentation;

import java.util.Arrays;

public class MongoStorage {

    public static MongoDatabase database(String hostname, int port, String databasename, String user, String password) {
        MongoCredential credential = MongoCredential.createScramSha256Credential(user, "admin", password.toCharArray());

        MongoClient mongoClient = MongoClients.create(
            MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress(hostname, port))))
                .credential(credential)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build());

        return mongoClient.getDatabase(databasename);
    }

}
