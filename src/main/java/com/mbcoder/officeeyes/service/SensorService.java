package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.SlackResponse;
import com.mbcoder.officeeyes.model.VibrationSensorData;
import com.mbcoder.officeeyes.utils.Utility;
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

    public void handleSensorData(VibrationSensorData sensorData) {
        long epochTime = Instant.now().toEpochMilli();
        if (sensorData.hasMotion()) {
            LOGGER.debug("Motion detected.");
            lastMovementSeen.set(epochTime);
        }
    }

    public SlackResponse getMovementStatus() {
        long now = Instant.now().toEpochMilli();
        long lastSeen = lastMovementSeen.get();
        if (lastSeen == 0) {
            return Utility.defaultMessage;
        }
        long duration = now - lastSeen;
        return Utility.createSlackResponse(duration);
    }

    public boolean isFree() {
        long now = Instant.now().toEpochMilli();
        long lastSeen = lastMovementSeen.get();
        long duration = now - lastSeen;
        if (lastSeen == 0 || duration > 30000) {
            return  true;
        }
        return false;
    }

}
