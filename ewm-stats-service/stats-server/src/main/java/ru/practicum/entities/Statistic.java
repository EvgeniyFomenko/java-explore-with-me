package ru.practicum.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statistic")
@Entity
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String url;
    @Column
    private String ip;
    @Column
    private String app;
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
