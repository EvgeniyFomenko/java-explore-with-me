package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entities.Statistic;
import ru.practicum.entities.StatisticAnswer;

import java.time.LocalDateTime;
import java.util.List;

public interface StatStorage extends JpaRepository<Statistic, Long> {
    @Query("select new ru.practicum.entities.StatisticAnswer (s.app ,s.url as uri , count(ip) as hits) from Statistic s where s.url in (?1) and s.createTime between ?2 and ?3 group by s.ip ,s.url , s.app order by hits desc")
    List<StatisticAnswer> searchByUris(String[] uris, LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.entities.StatisticAnswer (s.app ,s.url as uri , count(ip) as hits) from Statistic s where s.createTime between ?1 and ?2 group by s.ip ,s.url , s.app order by hits desc")
    List<StatisticAnswer> searchByStartEnd(LocalDateTime start, LocalDateTime end);

    @Query("select distinct new ru.practicum.entities.StatisticAnswer (s.app ,s.url as uri , count(distinct ip) as hits) from Statistic s where s.createTime between ?1 and ?2 group by s.ip ,s.url , s.app order by hits desc")
    List<StatisticAnswer> searchDistinctByStartEnd(LocalDateTime start, LocalDateTime end);

    @Query("select distinct new ru.practicum.entities.StatisticAnswer (s.app ,s.url as uri , count(distinct ip) as hits) from Statistic s where s.url in (?1) and s.createTime between ?2 and ?3 group by s.ip ,s.url , s.app order by hits desc ")
    List<StatisticAnswer> searchDistinctByUris(String[] uris, LocalDateTime start, LocalDateTime end);
}
