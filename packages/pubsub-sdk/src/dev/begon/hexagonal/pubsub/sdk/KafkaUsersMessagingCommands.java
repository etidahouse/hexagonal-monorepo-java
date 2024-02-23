package dev.begon.hexagonal.pubsub.sdk;

import dev.begon.hexagonal.core.sdk.messaging.UsersMessagingCommands;
import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaUsersMessagingCommands implements UsersMessagingCommands {

    private KafkaProducer<String, String> client;
    private String kafkaTopic;

    public KafkaUsersMessagingCommands(KafkaProducer<String, String> client, String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
        this.client = client;
    }

    @Override
    public void publishUserStoredEvent(User user) throws UnknownFailureException {
        try {
            String userJson = user.toJson();
            client.send(new ProducerRecord<>(kafkaTopic, userJson));
        } catch (Exception e) {
            throw new UnknownFailureException("Erreur lors de la publication de l'événement utilisateur", e);
        }
    }

    @Override
    public void publishUserRemovedEvent(User user) throws UnknownFailureException {
        // Logique similaire pour publier l'événement de suppression d'utilisateur
    }
    
    public void close() {
        client.close();
    }

}
