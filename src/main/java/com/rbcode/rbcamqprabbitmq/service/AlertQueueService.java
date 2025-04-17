package com.rbcode.rbcamqprabbitmq.service;

import com.rbcode.rbcamqprabbitmq.controller.model.AlertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertQueueService {

    @Value("${rbc.queue.name}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;

    public void addAlertsToQueue(final Integer count) {
        new Thread(() -> {
            IntStream.range(0, count).forEach(i -> {
                final String ldt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

                AlertDto alert = new AlertDto(null, "sender" + i, ldt, "message sent at " + ldt + " for id " + i);

                log.info("Sending new alert to queue");

                rabbitTemplate.convertAndSend(queueName, alert);
            });
        }).start();
    }
}
