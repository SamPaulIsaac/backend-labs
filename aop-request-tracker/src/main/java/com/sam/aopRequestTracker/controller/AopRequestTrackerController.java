package com.sam.aopRequestTracker.controller;

import com.sam.aopRequestTracker.config.TrackUserAction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
public class AopRequestTrackerController {

    @TrackUserAction(actionName = "Trigger Audit", description = "Audit trigger endpoint")
    @GetMapping("/trigger")
    public String trigger() {
        return "Trigger Action completed!";
    }


}
