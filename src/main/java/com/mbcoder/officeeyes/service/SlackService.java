package com.mbcoder.officeeyes.service;

import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlackService {

    public static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);

    @Autowired
    SensorService sensorService;

    public RichMessage handleSlashCommand(String command) {
        LOGGER.debug("New slash command received: {}", command);

        RichMessage richMessage;

        switch (command) {
            case "/pong":
                richMessage = handlePongCommand();
                richMessage.setResponseType("in_channel");
                break;

            case "/pongme":
                richMessage = handlePongCommand();
                richMessage.setResponseType("ephemeral");
                break;

            default:
                LOGGER.debug("Command: {} is not supported!", command);
                richMessage = new RichMessage("Command is not supported!");
                break;
        }

        return richMessage;
    }

    private RichMessage handlePongCommand() {
        RichMessage richMessage = sensorService.getMovementStatus();
        return richMessage;
    }

}
