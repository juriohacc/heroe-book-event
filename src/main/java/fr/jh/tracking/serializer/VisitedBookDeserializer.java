package fr.jh.tracking.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.jh.tracking.model.TrackingVisitedBook;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;
 
public class VisitedBookDeserializer implements Deserializer<TrackingVisitedBook> {
 
  @Override
  public void close() {
 
  }
 
  @Override
  public void configure(Map<String, ?> arg0, boolean arg1) {
 
  }
 
  @Override
  public TrackingVisitedBook deserialize(String arg0, byte[] arg1) {
    ObjectMapper mapper = new ObjectMapper();
    TrackingVisitedBook user = null;
    try {
      user = mapper.readValue(arg1, TrackingVisitedBook.class);
    } catch (Exception e) {
 
      e.printStackTrace();
    }
    return user;
  }
 
}