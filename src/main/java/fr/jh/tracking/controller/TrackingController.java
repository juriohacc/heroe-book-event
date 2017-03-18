package fr.jh.tracking.controller;

import fr.jh.tracking.model.TrackingVisitedBook;
import fr.jh.tracking.service.TrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @RequestMapping(value = "/visit-book", method = RequestMethod.POST)
    public void trackVisitBook(@RequestBody @Valid TrackingRequest trackingRequest) {
        this.log.info("tracking with params  : bookId : ", trackingRequest);
        this.trackingService.visitBookAction(new TrackingVisitedBook(trackingRequest.getAuthorId(),trackingRequest.getBookId()));
    }
}