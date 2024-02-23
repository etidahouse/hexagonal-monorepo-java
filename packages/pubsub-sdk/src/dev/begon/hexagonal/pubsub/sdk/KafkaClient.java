package dev.begon.hexagonal.pubsub.sdk;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaClient {
    
    public static KafkaProducer<String, String> kafkaClient(String host, int port) {        
        Properties props = new Properties();
        props.put("bootstrap.servers", host + ":" + Integer.toString(port));
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(props);
    }

}
