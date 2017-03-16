package fr.jh.tracking.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.jh.tracking.model.TrackingVisitedBook;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class VisitedBookSerializer implements Serializer<TrackingVisitedBook> {
 
  @Override
  public void close() {
 
  }
 
  @Override
  public void configure(Map<String, ?> arg0, boolean arg1) {
 
  }
 
  @Override
  public byte[] serialize(String arg0, TrackingVisitedBook arg1) {
    byte[] retVal = null;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      retVal = objectMapper.writeValueAsString(arg1).getBytes();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return retVal;
  }
 
}