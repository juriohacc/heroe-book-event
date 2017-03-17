package fr.jh.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by coucoun on 17/03/2017.
 */

@Service
public class KafkaServiceTest {

    @Value("${zookeeper.address}")
    private String zkAdress;

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;


    public String getZkAdress() {
        return zkAdress;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public Properties createConsumer(ConsumerInput consumerInput){
        Properties consumerProps = new Properties();
        consumerProps.setProperty("bootstrap.servers", bootstrapServers);
        consumerProps.setProperty("group.id", consumerInput.getGroupId());
        consumerProps.setProperty("client.id", consumerInput.getConsumerId());
        consumerProps.setProperty("key.deserializer", consumerInput.getKeyDeserialiser());
        consumerProps.setProperty("value.deserializer", consumerInput.getValueDeserialiser());
        consumerProps.put("auto.offset.reset", "earliest");  // to make sure the consumer starts from the beginning of the topic

        return consumerProps;
    }

}
