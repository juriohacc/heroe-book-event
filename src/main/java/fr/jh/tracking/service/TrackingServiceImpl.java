package fr.jh.tracking.service;

import fr.jh.tracking.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.jh.tracking.model.TrackingVisitedBook;

/**
 * Created by coucoun on 13/03/2017.
 */

@Service
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private TrackingRepository trackingRepository;

    @Override
    public void visitBookAction(TrackingVisitedBook trackingVisitedBook) {
        this.trackingRepository.visitBookAction(trackingVisitedBook);
    }

}
