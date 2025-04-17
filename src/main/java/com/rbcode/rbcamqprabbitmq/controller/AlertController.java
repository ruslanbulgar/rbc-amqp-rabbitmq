package com.rbcode.rbcamqprabbitmq.controller;

import com.rbcode.rbcamqprabbitmq.service.AlertProcessService;
import com.rbcode.rbcamqprabbitmq.service.AlertQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class AlertController {
    private final AlertQueueService alertService;
    private final AlertProcessService alertProcessService;

    @GetMapping({"", "/"})
    public String listAlerts(Model model){
        model.addAttribute("count", alertProcessService.count());
        return "list";
    }

    @PostMapping("/alert/send")
    public String processAlert(@RequestParam(name = "alertNum") final String alerts) {
        alertService.addAlertsToQueue(Integer.parseInt(alerts));

        return "redirect:/";
    }


}
