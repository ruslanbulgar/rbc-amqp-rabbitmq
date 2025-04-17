package com.rbcode.rbcamqprabbitmq.repository;

import com.rbcode.rbcamqprabbitmq.repository.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
