package com.rbcode.rbcamqprabbitmq.repository;

import com.rbcode.rbcamqprabbitmq.repository.model.Alert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlertRepositoryTest {

    @Autowired
    private AlertRepository repository;

    @Test
    void saveAlert() {

        final Alert alert = createAlert();

        Alert savedAlert = repository.save(alert);

        assertNotNull(savedAlert);
        assertNotNull(savedAlert.getId());
        assertNotNull(savedAlert.getTime());
        assertEquals("Sender", savedAlert.getSender());
        assertEquals("Message", savedAlert.getMessage());
    }

    private Alert createAlert() {
        return new Alert("Sender", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), "Message");
    }
}