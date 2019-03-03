package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.SlackRequest;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    SensorService sensorService;

    private HttpHeaders headers = new HttpHeaders();

    private RestTemplate restTemplate = new RestTemplate();

    private List<SlackRequest> reminders;

    public ReminderService() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        reminders = new ArrayList<>();
    }

    public ReminderService(List<SlackRequest> reminders) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.reminders = reminders;
    }

    @Scheduled(fixedRateString = "10000")
    public void checkReminders() {
        if (reminders.size() > 0) {
            boolean free = sensorService.isFree();
            if (free) {
                reminders.forEach(this::sendReminder);
                reminders.clear();
            }
        }
    }

    public String addReminder(SlackRequest slackRequest) {
        if (isDuplicate(slackRequest)) {
            return "This reminder is already registered!";
        }
        this.reminders.add(slackRequest);
        return "New reminder registered. I will inform you when the table is free.";
    }

    public void sendReminder(SlackRequest slackRequest) {
        RichMessage richMessage = new RichMessage();
        richMessage.setText("Table is free now, hurry!");
        richMessage.setResponseType(slackRequest.getResponseType());
        HttpEntity<RichMessage> entity = new HttpEntity<>(richMessage, headers);
        ResponseEntity<String> response = restTemplate.postForObject(slackRequest.getResponseUrl(), entity, ResponseEntity.class);
    }

    public boolean isDuplicate(SlackRequest slackRequest) {
        for (SlackRequest reminder : reminders) {
            if (reminder.getCommand().equals(slackRequest.getCommand())) {
                if (reminder.getUserId().equals(slackRequest.getUserId())) {
                    return true;
                } else {
                    if (reminder.getCommand().equals("/pong") && reminder.getChannelId().equals(slackRequest.getChannelId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
