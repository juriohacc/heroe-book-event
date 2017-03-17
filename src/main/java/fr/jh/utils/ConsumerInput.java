package fr.jh.utils;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ConsumerInput {
    private final String groupId;
    private final String consumerId;
    private final String keyDeserialiser;
    private final String valueDeserialiser;

    public ConsumerInput(String groupId, String consumerId, String keyDeserialiser, String valueDeserialiser) {
        this.groupId = groupId;
        this.consumerId = consumerId;
        this.keyDeserialiser = keyDeserialiser;
        this.valueDeserialiser = valueDeserialiser;
    }


    public String getGroupId() {
        return groupId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public String getKeyDeserialiser() {
        return keyDeserialiser;
    }

    public String getValueDeserialiser() {
        return valueDeserialiser;
    }
}
