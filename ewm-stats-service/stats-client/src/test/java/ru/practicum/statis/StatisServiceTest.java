package ru.practicum.statis;

import junit.framework.TestCase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.AnswerDto;
import ru.practicum.StatDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatisServiceTest extends TestCase {

    @Autowired
    StatisService statisService;
    StatDto statDto;

    @Test
    @Disabled
    public void testsPoints() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        statDto = StatDto.builder().ip("10.255.255.255")
                .app("app")
                .uri("http://uri.ru")
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
        statisService.postStatistics(statDto);
        List<AnswerDto> answerDtoList = statisService.getStatistic(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(5), new String[]{"http://uri.ru"}, "true");
        System.out.println(answerDtoList);
        assertEquals(answerDtoList.size(), 1);
    }

}