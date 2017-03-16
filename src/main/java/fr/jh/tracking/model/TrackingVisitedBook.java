package fr.jh.tracking.model;

import lombok.Getter;
import lombok.ToString;

@ToString
public class TrackingVisitedBook {

     @Getter
     private Integer authorId;

    @Getter
    private Integer bookId;

    public TrackingVisitedBook() {
    }

    public TrackingVisitedBook(Integer authorId,Integer bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }
}