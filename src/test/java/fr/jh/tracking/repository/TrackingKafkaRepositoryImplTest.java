package fr.jh.tracking.repository;

import fr.jh.tracking.constants.TrackingConstants;
import fr.jh.tracking.model.TrackingVisitedBook;
import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import fr.jh.kafka.test.ConsumerInput;
import fr.jh.kafka.test.KafkaConfSample;
import fr.jh.kafka.test.KafkaServiceTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Created by coucoun on 13/03/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackingKafkaRepositoryImplTest {

    @Autowired
    private KafkaServiceTest kafkaServiceTest;

    @Autowired
    private TrackingKafkaRepositoryImpl trackingRepository;

    @Before
    public void setUp() throws IOException {
        KafkaConfSample kafkaConfSample = new KafkaConfSample(kafkaServiceTest.getZkAdress(),kafkaServiceTest.getBootstrapServers());
        kafkaConfSample.addServer("0");
        AdminUtils.createTopic(kafkaConfSample.getZkUtils(), TrackingConstants.TOPIC_BOOK, 1, 1, new Properties(), RackAwareMode.Disabled$.MODULE$);
    }

    @Test
    public void visitBookAction(){

       KafkaConsumer<Integer,TrackingVisitedBook> consumer = new KafkaConsumer<>(kafkaServiceTest.
        createConsumer(ConsumerInput.builder().
                        consumerId("consumer0").
                        groupId("group0").
                        keyDeserialiser("org.apache.kafka.common.serialization.IntegerDeserializer").
                        valueDeserialiser("fr.jh.tracking.serializer.VisitedBookDeserializer")
                        .build()
        ));

        consumer.subscribe(Arrays.asList(TrackingConstants.TOPIC_BOOK));

        this.trackingRepository.visitBookAction(new TrackingVisitedBook(2,1));

        ConsumerRecords<Integer, TrackingVisitedBook> records = consumer.poll(4000);
        assertEquals(1, records.count());
        Iterator<ConsumerRecord<Integer, TrackingVisitedBook>> recordIterator = records.iterator();
        ConsumerRecord<Integer,TrackingVisitedBook> record = recordIterator.next();
        System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());

        assertEquals(42, (int) record.key());
        assertEquals(new Integer(2), record.value().getAuthorId());
        assertEquals(new Integer(1), record.value().getBookId());

    }
}
