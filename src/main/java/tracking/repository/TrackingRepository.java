package tracking.repository;

import tracking.model.TrackingVisitedBook;

/**
 * Created by coucoun on 13/03/2017.
 */
public interface TrackingRepository {

    void visitBookAction(TrackingVisitedBook trackingVisitedBook);

}
