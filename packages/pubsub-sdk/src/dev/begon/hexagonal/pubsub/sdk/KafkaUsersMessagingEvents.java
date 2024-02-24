package dev.begon.hexagonal.pubsub.sdk;

import dev.begon.hexagonal.core.sdk.messaging.UserMessaging;
import dev.begon.hexagonal.core.sdk.messaging.UsersMessagingEvents;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Consumer;

public class KafkaUsersMessagingEvents implements UsersMessagingEvents {

    private String kafkaTopic;
    private final KafkaClient client;

    private volatile boolean isRunning = true;

    public void stopListening() {
        isRunning = false;
    }

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public KafkaUsersMessagingEvents(KafkaClient client, String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
        this.client = client;
    }

    @Override
    public void publishUserEvent(UserMessaging user) throws UnknownFailureException {
        String userJson = gson.toJson(user);
        client.getProducer().send(new ProducerRecord<>(kafkaTopic, userJson));
    }

    @Override
    public void listenUserEvents(Consumer<UserMessaging> eventHandler) throws UnknownFailureException {
        try {
            client.getConsumer().subscribe(Collections.singletonList(kafkaTopic));
            while (isRunning) {
                ConsumerRecords<String, String> records = client.getConsumer().poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    UserMessaging userMessaging = gson.fromJson(record.value(), UserMessaging.class);
                    eventHandler.accept(userMessaging);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConsumer().close();
        }
    }

    public void close() {
        client.getConsumer().close();
        client.getProducer().close();
    }

}
