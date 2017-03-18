package fr.jh.kafka.test;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.TestUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import kafka.zk.EmbeddedZookeeper;
import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.common.utils.SystemTime;
import org.apache.kafka.common.utils.Time;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Created by coucoun on 17/03/2017.
 */
public class KafkaConfSample {

    private EmbeddedZookeeper embeddedZookeeper;
    private ZkClient zkClient;
    private String zkConnect;
    private ZkUtils zkUtils;
    private String bootstrapServers;

    public KafkaConfSample(String zkAdress, String bootstrapServers){
        this.bootstrapServers = bootstrapServers;
        this.embeddedZookeeper = new EmbeddedZookeeper();
        this.zkConnect = zkAdress + ":" + embeddedZookeeper.port();
        this.zkClient = new ZkClient(zkConnect, 30000, 30000, ZKStringSerializer$.MODULE$);
        this.zkUtils = createZkUtils();
    }


    public ZkUtils createZkUtils(){
        return ZkUtils.apply(zkClient, false);
    }


    public KafkaServer addServer(String brokerId) throws IOException {
        Properties brokerProps = new Properties();
        brokerProps.setProperty("zookeeper.connect", zkConnect);
        brokerProps.setProperty("broker.id", brokerId);
        brokerProps.setProperty("log.dirs", Files.createTempDirectory("kafka-").toAbsolutePath().toString());
        brokerProps.setProperty("listeners", "PLAINTEXT://" + bootstrapServers);
        KafkaConfig config = new KafkaConfig(brokerProps);
        Time mock = new SystemTime();
        return TestUtils.createServer(config, mock);
    }

    public ZkUtils getZkUtils() {
        return zkUtils;
    }

}
