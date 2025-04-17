package com.rbcode.rbcamqprabbitmq.service;

import com.rbcode.rbcamqprabbitmq.repository.AlertRepository;
import com.rbcode.rbcamqprabbitmq.repository.model.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AlertProcessService {

    private final AlertRepository alertRepository;
    private final AlertValidationService alertValidationService;

    public void saveAlert(Alert alert) {
        System.out.println("Received an alert");

        if (alertValidationService.valid(alert)) {
            alertRepository.save(alert);
        }
    }

    public void saveAlerts(List<Alert> alerts) {
        if (alerts.size() == 1) {
            saveAlert(alerts.get(0));
        } else {
            System.out.printf("Received batch of %s alerts %n", alerts.size());

            alertRepository.saveAll(alerts
                    .stream()
                    .filter(alertValidationService::valid)
                    .toList()
            );
        }
    }

    public Long count() {
        return alertRepository.count();
    }
}
