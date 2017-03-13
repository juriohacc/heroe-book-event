package tracking.repository;

import org.springframework.stereotype.Repository;
import tracking.model.TrackingVisitedBook;

/**
 * Created by coucoun on 13/03/2017.
 */

@Repository
public class TrackingKafkaRepositoryImpl implements TrackingRepository{

    @Override
    public void visitBookAction(TrackingVisitedBook trackingVisitedBook) {


    }
}
