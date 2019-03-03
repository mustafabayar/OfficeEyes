package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.Reminder;
import com.mbcoder.officeeyes.model.SlackRequest;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    SensorService sensorService;

    private HttpHeaders headers = new HttpHeaders();

    private RestTemplate restTemplate = new RestTemplate();

    private List<Reminder> reminders;

    public ReminderService() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        reminders = new ArrayList<>();
    }

    public ReminderService(List<Reminder> reminders) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.reminders = reminders;
    }

    @Scheduled(fixedRateString = "10000")
    public void checkReminders() {
        if (reminders.size() > 0) {
            Instant now = Instant.now();
            // Remove the reminder if it is added more than 30 minutes ago.
            // This is because responseUrl is only valid for 30 minutes.
            reminders.removeIf(r -> Duration.between(r.getDate(), now).toMinutes() > 30);
            if (sensorService.isFree()) {
                reminders.forEach(this::sendReminder);
                reminders.clear();
            }
        }
    }

    public String addReminder(SlackRequest slackRequest) {
        if (isDuplicate(slackRequest)) {
            return "This reminder is already registered!";
        }
        Reminder reminder = new Reminder(Instant.now(), slackRequest);
        this.reminders.add(reminder);
        return "New reminder registered. I will inform you when the table is free.";
    }

    public void sendReminder(Reminder reminder) {
        RichMessage richMessage = new RichMessage();
        richMessage.setText("Table is free now, hurry!");
        richMessage.setResponseType(reminder.getRequest().getResponseType());
        HttpEntity<RichMessage> entity = new HttpEntity<>(richMessage, headers);
        ResponseEntity<String> response = restTemplate.postForObject(reminder.getRequest().getResponseUrl(), entity, ResponseEntity.class);
    }

    public boolean isDuplicate(SlackRequest slackRequest) {
        for (Reminder reminder : reminders) {
            SlackRequest request = reminder.getRequest();
            if (request.getCommand().equals(slackRequest.getCommand())) {
                if (request.getUserId().equals(slackRequest.getUserId())) {
                    return true;
                } else {
                    if (request.getCommand().equals("/pong") && request.getChannelId().equals(slackRequest.getChannelId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
