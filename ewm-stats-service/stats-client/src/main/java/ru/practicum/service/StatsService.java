package ru.practicum.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.AnswerDto;
import ru.practicum.StatDto;
import ru.practicum.client.BaseClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatsService extends BaseClient {
    private static final String API_PREFIX = "/";

    @Autowired
    public StatsService(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public StatDto postStatistics(@RequestBody StatDto statDto) {
        ResponseEntity<StatDto> responseEntity = this.post("/hit", statDto, StatDto.builder().build());

        return responseEntity.getBody();
    }

    public List<AnswerDto> getStatistic(LocalDateTime start, LocalDateTime end, String[] uris, String uniq) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = Map.of(
                "start", start.format(formatter),
                "end", end.format(formatter),
                "uris", uris,
                "uniq", uniq

        );
        ResponseEntity<ArrayList<AnswerDto>> responseEntity = this.get("/stats?start={start}&end={end}&uris={uris}&uniq={uniq}", map, new ArrayList<>());

        return responseEntity.getBody();
    }


}
