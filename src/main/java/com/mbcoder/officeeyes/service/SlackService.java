package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.slack.Attachment;
import com.mbcoder.officeeyes.model.slack.SlackRequest;
import com.mbcoder.officeeyes.model.slack.SlackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlackService {

    public static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);

    public static final SlackResponse DEFAULT_ERROR_RESPONSE = new SlackResponse("Command is not supported!");

    @Autowired
    SensorService sensorService;

    @Autowired
    ReminderService reminderService;

    @Value("${officeeyes.admin.userName}")
    private String admin;

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
        Attachment attachment = null;
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
            case "release":
                if (slackRequest.getUserName().equals(admin)) {
                    attachment = new Attachment();
                    attachment.setTitle(":slack: NEW FEATURE :slack:");
                    attachment.setTitleLink("https://github.com/mustafabayar/OfficeEyes");
                    attachment.setText("Ladies and Gentlemen :knock:\nI am happy to announce my new feature `REMINDER`. You want to play :table_tennis_paddle_and_ball: but the table is occupied? But you also don't want to constantly check if it is empty? No more worries, now I am able to notify you when it gets empty :fidget_spinner:.\nAll you need to type `/pong reminder` or `/pong remind`. If you use `/pong` command the answer will be sent to the channel, if you use `/pongme reminder` only you will see the answer. Reminders are only last for 30 minutes. Which means if you register a reminder but the table did not get free for the next 30 minutes, I will throw the reminder to the garbage. If you register another reminder while you still have an active reminder, I will extend the time of previous reminder. You can have this cool feature only for 0.99 Cent :troll: \nJust kidding, it is available right away! :tada:");
                    attachment.setFooter("MBcoder");
                    attachment.setFooterIcon("https://platform.slack-edge.com/img/default_application_icon.png");
                    attachment.setColor("#0000ff"); // Blue
                    attachment.setTs(Long.toString(System.currentTimeMillis() / 1000L));
                }
                break;
        }
        return attachment;
    }

}
