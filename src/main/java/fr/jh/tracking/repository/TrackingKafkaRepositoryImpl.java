package fr.jh.tracking.repository;

import fr.jh.configuration.queue.kafka.Configuration;
import fr.jh.tracking.constants.TrackingConstants;
import fr.jh.tracking.model.TrackingVisitedBook;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.Properties;

/**
 * Created by coucoun on 13/03/2017.
 */

@Repository
@Slf4j
public class TrackingKafkaRepositoryImpl implements TrackingRepository{


    @Autowired
    private Configuration configuration;

    @Override
    public void visitBookAction(TrackingVisitedBook trackingVisitedBook) {

        this.log.info("produce message for visitBookAction for input :  {} ",trackingVisitedBook);
        KafkaProducer<Integer, TrackingVisitedBook> producer = new KafkaProducer<Integer, TrackingVisitedBook>(getTrackingVisitBookBrokerProperties());
        ProducerRecord<Integer,TrackingVisitedBook> data = new ProducerRecord<>(TrackingConstants.TOPIC_BOOK, 42, trackingVisitedBook);
        producer.send(data);
        this.log.info("message produced for visitBookAction");
        producer.close();
    }

    @Bean
    public Properties getTrackingVisitBookBrokerProperties(){
        Properties producerProps = new Properties();
        producerProps.setProperty("bootstrap.servers",this.configuration.getBootstrapServers());
        producerProps.setProperty("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        producerProps.setProperty("value.serializer", "fr.jh.tracking.serializer.VisitedBookSerializer");
        return producerProps;
    }
}
