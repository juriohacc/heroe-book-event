package fr.jh.configuration.queue.kafka;

import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration
public class Configuration {


    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;


    public String getBootstrapServers() {
        return bootstrapServers;
    }
}
