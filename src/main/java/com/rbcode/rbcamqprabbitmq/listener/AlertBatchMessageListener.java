package com.rbcode.rbcamqprabbitmq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbcode.rbcamqprabbitmq.repository.model.Alert;
import com.rbcode.rbcamqprabbitmq.service.AlertProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BatchMessageListener;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class AlertBatchMessageListener implements BatchMessageListener {

    private final AlertProcessService alertProcessService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AlertBatchMessageListener(AlertProcessService receiver) {
        this.alertProcessService = receiver;
    }

    @Override
    public void onMessage(Message message) {
        if(isNull(message)) {
            log.error("Null messsage received");
            return;
        }

        final Alert alert = convertMessage(message.getBody());

        alertProcessService.saveAlert(alert);
    }

    @Override
    public void onMessageBatch(List<Message> messages) {
        if(isNull(messages) || messages.isEmpty()) {
            log.error("Null or empty messsage list received");
            return;
        }

        final List<Alert> alerts = messages.stream()
                .map(m-> convertMessage(m.getBody()))
                .toList();

        alertProcessService.saveAlerts(alerts);
    }

    private Alert convertMessage(byte[] body) {
        try {
            return objectMapper.readValue(body, Alert.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
