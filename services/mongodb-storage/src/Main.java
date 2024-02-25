import dev.begon.hexagonal.core.sdk.events.MongoDBEventProcessor;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;

public class Main {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        
        MongoDBEventProcessor processor = new MongoDBEventProcessor(
            config.kafkaUsersMessagingEvents(config.kafkaClient()),
            config.mongoUsersCommands(config.mongoDatabase())
        );

        try {
            processor.replicateMongoDBEvents();
        } catch (UnknownFailureException e) {
            e.printStackTrace();
        }
    }
}
