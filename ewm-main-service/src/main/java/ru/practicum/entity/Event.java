package ru.practicum.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Getter
@Setter
@ToString
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private String annotation;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
    private boolean paid;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "request_moderation")
    private boolean requestModeration;
    private String title;
    @Column(name = "states")
    @Enumerated(EnumType.STRING)
    private State state;
    private int views;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "confirmed_requests")
    private int confirmedRequests;
}
