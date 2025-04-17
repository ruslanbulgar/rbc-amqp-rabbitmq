package com.rbcode.rbcamqprabbitmq.service;

import com.rbcode.rbcamqprabbitmq.repository.model.Alert;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class AlertValidationService {

    public boolean valid(final Alert alert) {
        if (isNull(alert)) {
            return false;
        }
        return !nonNull(alert.getId());
    }
}
