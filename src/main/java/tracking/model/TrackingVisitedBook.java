package tracking.model;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TrackingVisitedBook {

    private Integer authorId;
    private Integer bookId;

}
