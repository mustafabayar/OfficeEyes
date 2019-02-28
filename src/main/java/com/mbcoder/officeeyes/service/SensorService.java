package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.UltrasonicSensorData;
import com.mbcoder.officeeyes.utils.Utility;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SensorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);

    private AtomicLong lastMovementSeen;

    public SensorService() {
        lastMovementSeen = new AtomicLong();
    }

    public void handleSensorData(UltrasonicSensorData sensorData) {
        long epochTime = Instant.now().toEpochMilli();
        if (sensorData.hasMotion()) {
            LOGGER.debug("Motion detected.");
            LOGGER.debug("Distance: {}", sensorData.getDistance());
            lastMovementSeen.set(epochTime);
        }
    }

    public RichMessage getMovementStatus() {
        long now = Instant.now().toEpochMilli();
        long duration = now - lastMovementSeen.get();
        return Utility.createRichMessage(duration);
    }

}
