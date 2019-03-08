package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.slack.Attachment;
import com.mbcoder.officeeyes.model.slack.SlackRequest;
import com.mbcoder.officeeyes.model.slack.SlackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlackService {

    public static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);

    public static final SlackResponse DEFAULT_ERROR_RESPONSE = new SlackResponse("Command is not supported!");

    @Autowired
    SensorService sensorService;

    @Autowired
    ReminderService reminderService;

    public SlackResponse handleSlashCommand(SlackRequest slackRequest) {
        LOGGER.debug("New slash command received: {}", slackRequest.getCommand());

        SlackResponse slackResponse;
        switch (slackRequest.getCommand()) {
            case "/pong":
            case "/pongme":
                slackResponse = handlePongCommand();
                if (slackRequest.getText() != null && !slackRequest.getText().isEmpty()) {
                    Attachment attachment = handleText(slackRequest);
                    slackResponse.addAttachment(attachment);
                }
                break;

            default:
                LOGGER.debug("Command: {} is not supported!", slackRequest.getCommand());
                slackResponse = DEFAULT_ERROR_RESPONSE;
                break;
        }

        slackResponse.setResponseType(slackRequest.getResponseType());
        return slackResponse;
    }

    private SlackResponse handlePongCommand() {
        SlackResponse slackResponse = sensorService.getMovementStatus();
        return slackResponse;
    }

    private Attachment handleText(SlackRequest slackRequest) {
        Attachment attachment;
        switch (slackRequest.getText()) {
            case "remind":
            case "reminder":
                attachment = new Attachment();
                attachment.setTitle("REMINDER");
                if (sensorService.isFree()) {
                    attachment.setText("Reminder is not registered because table is already free.");
                    attachment.setColor("#ff0000"); // Red
                } else {
                    String reminderResponse = reminderService.addReminder(slackRequest);
                    attachment.setText(reminderResponse);
                    attachment.setFooter("Reminders only last for 30 minutes before they destruct themselves");
                    attachment.setColor("#20aa20"); // Green
                }
                break;
            default:
                attachment = null;
                break;
        }
        return attachment;
    }

}
