package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.VibrationSensorData;
import com.mbcoder.officeeyes.model.entity.ActivityTime;
import com.mbcoder.officeeyes.model.slack.SlackResponse;
import com.mbcoder.officeeyes.repository.ActivityRepository;
import com.mbcoder.officeeyes.utils.PingPongUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SensorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);

    private AtomicLong lastMovementSeen;

    @Autowired
    ActivityRepository activityRepository;

    public SensorService() {
        lastMovementSeen = new AtomicLong();
    }

    @PostConstruct
    public void init() {
        ActivityTime activityTime = activityRepository.findById(1000L).orElse(null);
        if (activityTime != null) {
            lastMovementSeen.set(activityTime.getLastActivityTime().toInstant().toEpochMilli());
        }
    }

    public void handleSensorData(VibrationSensorData sensorData) {
        long epochTime = Instant.now().toEpochMilli();
        if (sensorData.hasMotion()) {
            LOGGER.debug("Motion detected.");
            lastMovementSeen.set(epochTime);

            ActivityTime activityTime = activityRepository.findById(1000L).orElse(new ActivityTime());
            activityTime.setLastActivityTime(new Date());
            activityRepository.save(activityTime);
        }
    }

    public SlackResponse getMovementStatus() {
        long now = Instant.now().toEpochMilli();
        long lastSeen = lastMovementSeen.get();
        if (lastSeen == 0) {
            return new SlackResponse("FREE! But I didn't had time to check the last activity time.");
        }
        long duration = now - lastSeen;
        return PingPongUtility.createSlackResponse(duration);
    }

    public boolean isFree() {
        long now = Instant.now().toEpochMilli();
        long lastSeen = lastMovementSeen.get();
        long duration = now - lastSeen;
        if (lastSeen == 0 || duration > 45000) {
            return  true;
        }
        return false;
    }

}
