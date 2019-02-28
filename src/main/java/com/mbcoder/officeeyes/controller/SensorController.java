package com.mbcoder.officeeyes.controller;

import com.mbcoder.officeeyes.model.UltrasonicSensorData;
import com.mbcoder.officeeyes.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SensorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    SensorService sensorService;

    @PostMapping(path = "/sensor/event/motion")
    public ResponseEntity<?> handleEvent(@RequestBody UltrasonicSensorData sensorData) {
        LOGGER.debug("New event received from Sensor.");

        sensorService.handleSensorData(sensorData);

        return ResponseEntity.ok().build();
    }
}
