package fr.jh.tracking.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.jh.tracking.service.TrackingService;
import fr.jh.tracking.model.TrackingVisitedBook;

@RestController
@Slf4j
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @RequestMapping("/visit-book")
    public void track(@RequestParam(value="authorId") Integer authorId, @RequestParam(value="bookId") Integer bookId) {
        this.log.info("tracking with params  : bookId :",authorId," authorId :", bookId);
        this.trackingService.visitBookAction(new TrackingVisitedBook(authorId,bookId));
    }
}