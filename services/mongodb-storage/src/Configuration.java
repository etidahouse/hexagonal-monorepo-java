import com.mongodb.client.MongoDatabase;

import dev.begon.hexagonal.mongodb.sdk.MongoStorage;
import dev.begon.hexagonal.mongodb.sdk.MongoUsersCommands;

import dev.begon.hexagonal.pubsub.sdk.KafkaClient;
import dev.begon.hexagonal.pubsub.sdk.KafkaUsersMessagingEvents;

public class Configuration {

    private String mongodbHostname;
    private int mongodbPort;
    private String mongodbDatabaseName;
    private String mongodbUser;
    private String mongodbPassword;
    
    private String kafkaHost;
    private int kafkaPort;
    private String kafkaGroupId;

    private String kafkaTopic;

    public Configuration() {
        this.mongodbHostname = System.getenv("MONGODB_HOSTNAME");
        this.mongodbPort = Integer.parseInt(System.getenv("MONGODB_PORT"));
        this.mongodbDatabaseName = System.getenv("MONGODB_DATABASE_NAME");
        this.mongodbUser = System.getenv("MONGODB_USER");
        this.mongodbPassword = System.getenv("MONGODB_PASSWORD");
        
        this.kafkaHost = System.getenv("KAFKA_HOST");
        this.kafkaPort = Integer.parseInt(System.getenv("KAFKA_PORT"));
        this.kafkaGroupId = System.getenv("KAFKA_GROUP_ID");

        this.kafkaTopic = System.getenv("KAFKA_TOPIC");
    }

    public KafkaClient kafkaClient() {
        return KafkaClient.kafkaClient(kafkaHost, kafkaPort, kafkaGroupId);
    }

    public KafkaUsersMessagingEvents kafkaUsersMessagingEvents(KafkaClient client) {
        return new KafkaUsersMessagingEvents(client, kafkaTopic);
    }

    public MongoDatabase mongoDatabase() {
        return MongoStorage.database(mongodbHostname, mongodbPort, mongodbDatabaseName, mongodbUser, mongodbPassword);
    }

    public MongoUsersCommands mongoUsersCommands(MongoDatabase mongoDatabase) {
        return new MongoUsersCommands(mongoDatabase());
    }
    
}
