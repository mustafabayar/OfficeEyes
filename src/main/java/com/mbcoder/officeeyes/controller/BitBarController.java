package com.mbcoder.officeeyes.controller;

import com.mbcoder.officeeyes.model.BitBarResponse;
import com.mbcoder.officeeyes.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BitBarController {

    public static final Logger LOGGER = LoggerFactory.getLogger(BitBarController.class);

    @Autowired
    SensorService sensorService;

    @GetMapping(path = "/bitbar")
    public BitBarResponse handleEvent() {
        LOGGER.debug("New request received from Bitbar.");
        return new BitBarResponse(sensorService.isFree());
    }
}
