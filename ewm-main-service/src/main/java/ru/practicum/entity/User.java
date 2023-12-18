package ru.practicum.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Table(name = "users")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String name;
    @ManyToMany
    @JoinTable(name = "subscriptions", joinColumns = @JoinColumn(name = "follower"),
            inverseJoinColumns = @JoinColumn(name = " event_maker"))
    private List<User> eventMakers;
}
