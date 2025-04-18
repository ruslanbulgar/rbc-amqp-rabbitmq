package com.rbcode.rbcamqprabbitmq.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String sender;
    private String time;
    private String message;
}
