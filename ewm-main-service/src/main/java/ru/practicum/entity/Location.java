package ru.practicum.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Location {
    private float lat;
    private float lon;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
