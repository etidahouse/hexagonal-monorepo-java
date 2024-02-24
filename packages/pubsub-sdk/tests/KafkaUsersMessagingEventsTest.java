import dev.begon.hexagonal.core.sdk.messaging.ActionKindMessaging;
import dev.begon.hexagonal.core.sdk.messaging.KindMessaging;
import dev.begon.hexagonal.core.sdk.messaging.UserMessaging;
import dev.begon.hexagonal.pubsub.sdk.KafkaClient;
import dev.begon.hexagonal.pubsub.sdk.KafkaUsersMessagingEvents;
import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KafkaUsersMessagingEventsTest {

    private static final String KAFKA_TOPIC = "test-topic";

    @Test
    public void testPublishAndConsumeUserEvent() throws UnknownFailureException, InterruptedException {
        KafkaClient client = TestUtils.client();
        KafkaUsersMessagingEvents kafkaUsersMessagingEvents = new KafkaUsersMessagingEvents(client, KAFKA_TOPIC);

        User user = new User(UUID.randomUUID(), "username", "email");
        UserMessaging event = new UserMessaging(KindMessaging.USER, ActionKindMessaging.STORE, user);

        CountDownLatch latch = new CountDownLatch(1);

        TestUserEventHandler userEventHandler = new TestUserEventHandler(latch);

        Thread listenerThread = new Thread(() -> {
            try {
                kafkaUsersMessagingEvents.listenUserEvents(userEventHandler);
            } catch (UnknownFailureException e) {
                e.printStackTrace();
            }
        });

        listenerThread.start();

        kafkaUsersMessagingEvents.publishUserEvent(event);

        // Wait for the latch to be counted down or timeout after a specified duration
        latch.await(5, TimeUnit.SECONDS);

        kafkaUsersMessagingEvents.stopListening(); // Stop the consumer
        listenerThread.join();

        assertEquals(event.getUser().getId(), userEventHandler.getLastReceivedUserMessaging().getUser().getId());
        assertEquals(event.getUser().getUsername(), userEventHandler.getLastReceivedUserMessaging().getUser().getUsername());
        assertEquals(event.getUser().getEmail(), userEventHandler.getLastReceivedUserMessaging().getUser().getEmail());


        kafkaUsersMessagingEvents.close();
    }

}
