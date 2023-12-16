package ru.practicum.entity;


public enum State {
    PENDING,
    PUBLISHED,
    CANCELED,
    PUBLISH_EVENT,
    REJECT_EVENT,
    SEND_TO_REVIEW,
    CONFIRMED,
    REJECTED,

    CANCEL_REVIEW;
}
