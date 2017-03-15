package fr.jh.tracking.repository;

import fr.jh.tracking.model.TrackingVisitedBook;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Created by coucoun on 13/03/2017.
 */

@Repository
@Slf4j
public class TrackingKafkaRepositoryImpl implements TrackingRepository{

    private static final String BROKERHOST = "127.0.0.1";
    private static final String BROKERPORT = "9092";
    private static final String TOPIC = "test";

    @Override
    public void visitBookAction(TrackingVisitedBook trackingVisitedBook) {

        this.log.info("produce message for visitBookAction for input : ",trackingVisitedBook);
        // setup producer
        Properties producerProps = new Properties();
        producerProps.setProperty("bootstrap.servers", BROKERHOST + ":" + BROKERPORT);
        producerProps.setProperty("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        producerProps.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        KafkaProducer<Integer, byte[]> producer = new KafkaProducer<Integer, byte[]>(producerProps);

        ProducerRecord<Integer, byte[]> data = new ProducerRecord<>(TOPIC, 42, "test-message".getBytes(StandardCharsets.UTF_8));
        producer.send(data);
        this.log.info("message produced for visitBookAction");
        producer.close();
    }
}
