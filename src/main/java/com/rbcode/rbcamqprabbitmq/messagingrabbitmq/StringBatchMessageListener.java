package com.rbcode.rbcamqprabbitmq.messagingrabbitmq;

import org.springframework.amqp.core.BatchMessageListener;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringBatchMessageListener implements BatchMessageListener {

    private final Receiver receiver;

    public StringBatchMessageListener(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onMessage(Message message) {
        receiver.receiveMessage(Arrays.toString(message.getBody()));
    }

    @Override
    public void onMessageBatch(List<Message> messages) {
        receiver.receiveMessages(messages.stream()
                .map(m->new String(m.getBody(), StandardCharsets.UTF_8))
                .collect(Collectors.toList()));
    }
}
