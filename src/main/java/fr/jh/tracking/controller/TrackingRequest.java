package fr.jh.tracking.controller;

import lombok.Value;

import javax.validation.constraints.NotNull;


@Value
public class TrackingRequest {

    @NotNull
    private String authorId;

    @NotNull
    private String bookId;
}
