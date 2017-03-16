package fr.jh.tracking.repository;

import fr.jh.tracking.constants.TrackingConstants;
import fr.jh.tracking.model.TrackingVisitedBook;
import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.TestUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import kafka.zk.EmbeddedZookeeper;
import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.utils.SystemTime;
import org.apache.kafka.common.utils.Time;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
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
    private TrackingKafkaRepositoryImpl trackingRepository;

    private static final String ZKHOST = "127.0.0.1";
    private static final String BROKERHOST = "127.0.0.1";
    private static final String BROKERPORT = "9092";

    @Before
    public void setUp() throws IOException {
        EmbeddedZookeeper zkServer = new EmbeddedZookeeper();
        String zkConnect = ZKHOST + ":" + zkServer.port();
        ZkClient zkClient = new ZkClient(zkConnect, 30000, 30000, ZKStringSerializer$.MODULE$);
        ZkUtils zkUtils = ZkUtils.apply(zkClient, false);


        // setup Broker
        Properties brokerProps = new Properties();
        brokerProps.setProperty("zookeeper.connect", zkConnect);
        brokerProps.setProperty("broker.id", "0");
        brokerProps.setProperty("log.dirs", Files.createTempDirectory("kafka-").toAbsolutePath().toString());
        brokerProps.setProperty("listeners", "PLAINTEXT://" + BROKERHOST +":" + BROKERPORT);
        KafkaConfig config = new KafkaConfig(brokerProps);
        Time mock = new SystemTime();
        KafkaServer kafkaServer = TestUtils.createServer(config, mock);

        // create topic
        AdminUtils.createTopic(zkUtils, TrackingConstants.TOPIC_BOOK, 1, 1, new Properties(), RackAwareMode.Disabled$.MODULE$);
    }

    @Test
    public void visitBookAction(){
        Properties consumerProps = new Properties();
        consumerProps.setProperty("bootstrap.servers", BROKERHOST + ":" + BROKERPORT);
        consumerProps.setProperty("group.id", "group0");
        consumerProps.setProperty("client.id", "consumer0");
        consumerProps.setProperty("key.deserializer","org.apache.kafka.common.serialization.IntegerDeserializer");
        consumerProps.setProperty("value.deserializer", "fr.jh.tracking.serializer.VisitedBookDeserializer");
        consumerProps.put("auto.offset.reset", "earliest");  // to make sure the consumer starts from the beginning of the topic
        KafkaConsumer<Integer,TrackingVisitedBook> consumer = new KafkaConsumer<>(consumerProps);
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
