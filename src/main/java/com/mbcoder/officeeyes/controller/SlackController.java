package com.mbcoder.officeeyes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbcoder.officeeyes.model.SlackRequest;
import com.mbcoder.officeeyes.service.SlackService;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackController {

    public static final Logger LOGGER = LoggerFactory.getLogger(SlackController.class);

    @Autowired
    SlackService slackService;

    @RequestMapping(value = "/slack/slash",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RichMessage onReceiveSlashCommand(@RequestParam("team_id") String teamId,
                                             @RequestParam("team_domain") String teamDomain,
                                             @RequestParam("channel_id") String channelId,
                                             @RequestParam("channel_name") String channelName,
                                             @RequestParam("user_id") String userId,
                                             @RequestParam("user_name") String userName,
                                             @RequestParam("command") String command,
                                             @RequestParam("text") String text,
                                             @RequestParam("response_url") String responseUrl) {

        SlackRequest slackRequest = new SlackRequest(teamId, teamDomain, channelId, channelName, userId, userName, command, text, responseUrl);

        RichMessage richMessage = slackService.handleSlashCommand(slackRequest);

        // For debugging purpose only
        if (LOGGER.isDebugEnabled()) {
            try {
                LOGGER.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
            } catch (JsonProcessingException e) {
                LOGGER.debug("Error parsing RichMessage: ", e);
            }
        }

        return richMessage.encodedMessage();
    }

}
