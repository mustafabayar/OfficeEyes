package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.SlackRequest;
import com.mbcoder.officeeyes.model.SlackResponse;
import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
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

    public RichMessage handleSlashCommand(SlackRequest slackRequest) {
        LOGGER.debug("New slash command received: {}", slackRequest.getCommand());

        SlackResponse slackResponse;
        switch (slackRequest.getCommand()) {
            case "/pong":
            case "/pongme":
                slackResponse = handlePongCommand();
                if (checkReminder(slackRequest)) {
                    if (sensorService.isFree()) {
                        Attachment attachment = new Attachment();
                        attachment.setTitle("REMINDER");
                        attachment.setText("Reminder is not registered because table is already free.");
                        attachment.setColor("#ff0000");
                        slackResponse.getAttachments().add(attachment);
                    } else {
                        String reminderResponse = reminderService.addReminder(slackRequest);
                        Attachment attachment = new Attachment();
                        attachment.setTitle("REMINDER");
                        attachment.setText(reminderResponse);
                        attachment.setColor("#2eb886");
                        slackResponse.getAttachments().add(attachment);
                    }
                }
                break;

            default:
                LOGGER.debug("Command: {} is not supported!", slackRequest.getCommand());
                slackResponse = DEFAULT_ERROR_RESPONSE;
                break;
        }
        RichMessage richMessage = createRichMessage(slackResponse);
        richMessage.setResponseType(slackRequest.getResponseType());
        return richMessage;
    }

    private SlackResponse handlePongCommand() {
        SlackResponse slackResponse = sensorService.getMovementStatus();
        return slackResponse;
    }

    private boolean checkReminder(SlackRequest slackRequest) {
        if (slackRequest.getText() != null && slackRequest.getText().equalsIgnoreCase("reminder")) {
            return true;
        }
        return false;
    }

    private RichMessage createRichMessage(SlackResponse slackResponse) {
        RichMessage richMessage = new RichMessage();
        richMessage.setText(slackResponse.getText());
        richMessage.setAttachments(slackResponse.getAttachments().stream().toArray(Attachment[]::new));
        return richMessage;
    }

}
