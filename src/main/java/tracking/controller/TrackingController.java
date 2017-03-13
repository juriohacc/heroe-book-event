package tracking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tracking.service.TrackingService;
import tracking.model.TrackingVisitedBook;

@RestController
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @RequestMapping("/greeting")
    public void track(@RequestParam(value="authorId") Integer authorId, @RequestParam(value="bookId") Integer bookId) {

        this.trackingService.visitBookAction(new TrackingVisitedBook(authorId,bookId));
    }
}