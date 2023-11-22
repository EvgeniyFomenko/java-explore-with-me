package ru.practicum.statis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.StatDto;
import ru.practicum.client.BaseClient;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class StatisService extends BaseClient {
    private static final String API_PREFIX = "/";

    @Autowired
    public StatisService(@Value("${statis-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> postStatistics(@RequestBody StatDto statDto) {
        return this.post("/hit", statDto);
    }

    public ResponseEntity<Object> getStatistic(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end, @RequestParam(required = false) String[] uris, @RequestParam(defaultValue = "false", required = false) String uniq) {
        Map<String, Object> map = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "uniq", uniq

        );
        return this.get("/stats?start={}&end={}&uris={}&uniq={}", map);
    }


}
