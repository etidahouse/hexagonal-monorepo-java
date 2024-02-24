import dev.begon.hexagonal.pubsub.sdk.KafkaClient;

public class TestUtils {
    
    public static KafkaClient client() {
        return KafkaClient.kafkaClient("localhost", 9092, "group-id");
    }

}
