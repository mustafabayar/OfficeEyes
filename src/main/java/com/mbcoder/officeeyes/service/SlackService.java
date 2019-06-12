package com.mbcoder.officeeyes.service;

import com.mbcoder.officeeyes.model.slack.*;
import com.mbcoder.officeeyes.utils.PingPongUtility;
import com.mbcoder.officeeyes.utils.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SlackService {

    public static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);

    public static final SlackResponse DEFAULT_ERROR_RESPONSE = new SlackResponse("Command is not supported!");

    private RestTemplate restTemplate = new RestTemplate();

    private Map<String, SlackResponse> callbacks = new HashMap<>();

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
                    List<Attachment> attachments = handleText(slackRequest);
                    attachments.forEach(slackResponse::addAttachment);
                    if (slackRequest.getText().equals("help") || slackRequest.getText().equals("man") || slackRequest.getText().equals("manual")) {
                        slackResponse.setText(":slack: OfficeEyes Manual :slack:");
                    }
                }
                break;

            case "/ping":
                slackResponse = handlePingCommand(slackRequest);
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

    private SlackResponse handlePingCommand(SlackRequest request) {
        boolean isFree = sensorService.isFree();
        if (!isFree) {
            return new SlackResponse("The table is currently occupied, try later!");
        }

        SlackResponse slackResponse = new SlackResponse();
        slackResponse.setTs(Long.toString(System.currentTimeMillis() / 1000L));

        slackResponse.setText(String.format("<@%s> wants to play Ping-Pong!", request.getUserId()));

        Attachment attachment = new Attachment();
        attachment.setText(String.format("Please press the button if you want to play against <@%s>", request.getUserId()));
        attachment.setFallback("You are unable to answer this request");
        attachment.setCallbackId(request.getUserId());

        Action action = new Action("button-join", "Join", "primary", "button", "To join a match");

        attachment.addAction(action);
        slackResponse.addAttachment(attachment);
        callbacks.put(slackResponse.getTs(), slackResponse);

        return slackResponse;
    }

    private List<Attachment> handleText(SlackRequest slackRequest) {
        List<Attachment> attachments = new ArrayList<>();
        switch (slackRequest.getText()) {
            case "remind":
            case "reminder":
                Attachment attachment = new Attachment();
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
                attachments.add(attachment);
                break;
            case "help":
            case "man":
            case "manual":
                Attachment attachment0 = new Attachment();
                attachment0.setTitle("Please go to the Github page if you want to contribute to the project.");
                attachment0.setTitleLink("https://github.com/mustafabayar/OfficeEyes");
                attachment0.setText("If you have any suggestion for improvement please ask in the <#C12SL4810> channel or reach to <@UEKALNKB8>");
                attachment0.setFooter("All of the below commands can be executed anywhere in the Slack. You don't have to use them in pingpong channel.");
                attachment0.setColor("#00cc00");
                attachments.add(attachment0);

                Attachment attachment1 = new Attachment();
                attachment1.setTitle("1) /pong & /pongme");
                attachment1.setText("Usage: `/pong` or `/pongme`\nThese are basic commands for checking the status of ping-pong table. Former sends the reply as public to the channel in which the command is initiated while the latter sends the reply as private to the initiator. All of the below features can be used both with `pong` and `pongme` commands.");
                attachment1.setColor("#0000ff");
                attachments.add(attachment1);

                Attachment attachment2 = new Attachment();
                attachment2.setTitle("2) remind & reminder");
                attachment2.setText("Usage: `/pong reminder` or `/pongme reminder`\nReminder is a service that informs you when the table gets free. If you try to set a reminder while the table is already free, it will warn you and reminder will not be set. If you successfully set a reminder, application will reply to the channel or chat in which the command is initiated when the table gets free. If you set a reminder but the table did not get empty for the next 30 minutes, reminder will be deleted and you will not be informed anymore. If you try to create a new reminder while you still have an active reminder, it will extend the duration of the existing reminder.");
                attachment2.setColor("#0000ff");
                attachments.add(attachment2);

                Attachment attachment3 = new Attachment();
                attachment3.setTitle("3) /ping");
                attachment3.setText("Usage: `/ping`\nIt creates a interactive message for someone to accept your challenge. When the challenge is accepted, it tags both users in a reply to inform them. The benefit of using `/ping` command over a good old pinging is that it also checks if the table is free or not, and if the table is occupied it won't create the challenge. It also checks the status of the table second time when a user accepts the challenge so that they can be sure that table is free or not.");
                attachment3.setColor("#0000ff");
                attachments.add(attachment3);

                Attachment attachment4 = new Attachment();
                attachment4.setTitle("4) help & manual & man");
                attachment4.setText("Usage: `/pong help` or `/pongme help`\nPrints this document.");
                attachment4.setColor("#ff0000");
                attachments.add(attachment4);
                break;
        }
        return attachments;
    }

    public SlackResponse handleInteractiveRequest(InteractiveRequest interactiveRequest) {
        if (interactiveRequest.getUser().getId().equals(interactiveRequest.getCallbackId())) {
            SlackResponse failResponse = new SlackResponse(PingPongUtility.getStatusText(Status.SAME_PERSON));
            failResponse.setDeleteOriginal(false);
            failResponse.setReplaceOriginal(false);
            failResponse.setResponseType("ephemeral");
            return failResponse;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String key = interactiveRequest.getMessageTs().substring(0, interactiveRequest.getMessageTs().indexOf("."));
        SlackResponse response = interactiveRequest.getOriginalMessage();
        if (response == null) {
            response = callbacks.get(key);
            interactiveRequest.setOriginalMessage(response);
            callbacks.remove(key);
        }
        response.getAttachments().clear();
        Attachment attachment = new Attachment();
        attachment.setColor("#20aa20"); // Green
        attachment.setText(String.format("This challenge is accepted by <@%s>", interactiveRequest.getUser().getId()));
        response.addAttachment(attachment);
        HttpEntity<SlackResponse> entity = new HttpEntity<>(response, headers);
        LOGGER.debug("Sending action response to Slack at url: {}", interactiveRequest.getResponseUrl());
        ResponseEntity<String> answer = restTemplate.exchange(interactiveRequest.getResponseUrl(), HttpMethod.POST, entity, String.class);
        LOGGER.debug("Slack response to the action response: {}", answer.getStatusCodeValue());

        return sendResponseToThread(interactiveRequest);
    }

    private SlackResponse sendResponseToThread(InteractiveRequest interactiveRequest) {
        SlackResponse response = new SlackResponse();
        boolean isFree = sensorService.isFree();
        if (!isFree) {
            response.setText(String.format("<@%s> and <@%s> I am sorry but table got busy, try later!", interactiveRequest.getUser().getId(), interactiveRequest.getCallbackId()));
            response.setReplaceOriginal(false);
        } else {
            response.setText(String.format("<@%s> and <@%s> GO GO GO!", interactiveRequest.getUser().getId(), interactiveRequest.getCallbackId()));
            response.setReplaceOriginal(false);
        }
        response.setThreadTs(interactiveRequest.getMessageTs());
        response.setResponseType("in_channel");

        return response;
    }

}
