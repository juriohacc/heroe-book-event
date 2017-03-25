package fr.jh.tracking.model;

import lombok.Getter;
import lombok.ToString;

@ToString
public class TrackingVisitedBook {

    @Getter
    private String authorId;

    @Getter
    private String bookId;

    public TrackingVisitedBook() {
    }

    public TrackingVisitedBook(String authorId,String bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }
}