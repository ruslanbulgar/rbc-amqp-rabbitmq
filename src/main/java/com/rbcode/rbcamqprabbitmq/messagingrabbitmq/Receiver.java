package com.rbcode.rbcamqprabbitmq.messagingrabbitmq;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Receiver {

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }

    public void receiveMessages(List<String> messages) {
        System.out.println("Received batch <" + messages.toString() + ">");
    }
}
