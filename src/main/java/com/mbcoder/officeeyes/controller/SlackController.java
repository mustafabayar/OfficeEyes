package com.mbcoder.officeeyes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbcoder.officeeyes.model.slack.InteractiveRequest;
import com.mbcoder.officeeyes.model.slack.SlackRequest;
import com.mbcoder.officeeyes.model.slack.SlackResponse;
import com.mbcoder.officeeyes.service.SlackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class SlackController {

    public static final Logger LOGGER = LoggerFactory.getLogger(SlackController.class);

    private static final List<String> ALLOWED_CHANNELS = Stream.of("directmessage", "pingpong", "kicker", "privategroup").collect(Collectors.toList());

    @Autowired
    ObjectMapper mapper;

    @Autowired
    SlackService slackService;

    @Value("${officeeyes.inMaintenance}")
    private boolean inMaintenance;

    @RequestMapping(value = "/slack/slash",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public SlackResponse onReceiveSlashCommand(@RequestParam("team_id") String teamId,
                                               @RequestParam("team_domain") String teamDomain,
                                               @RequestParam("channel_id") String channelId,
                                               @RequestParam("channel_name") String channelName,
                                               @RequestParam("user_id") String userId,
                                               @RequestParam("user_name") String userName,
                                               @RequestParam("command") String command,
                                               @RequestParam("text") String text,
                                               @RequestParam("response_url") String responseUrl) {
        SlackRequest slackRequest = new SlackRequest(teamId, teamDomain, channelId, channelName, userId, userName, command, text, responseUrl);
        LOGGER.info(slackRequest.toString());

        if (!ALLOWED_CHANNELS.contains(channelName)) {
            SlackResponse warning = new SlackResponse("This request is only available on certain places. Such as: Direct Messages, <#C12SL4810> Channel, <#C0AEHJ8D7> Channel and Private Channels.");
            warning.setResponseType("ephemeral");
            return warning;
        }

        if (inMaintenance) {
            SlackResponse maintenance = new SlackResponse("I am under maintenance to provide better service");
            maintenance.setResponseType(slackRequest.getResponseType());
            return maintenance;
        }

        SlackResponse slackResponse = slackService.handleSlashCommand(slackRequest);

        LOGGER.info(slackResponse.toString());

        return slackResponse;
    }

    @RequestMapping(value = "/slack/interactive",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public SlackResponse onReceiveInteractiveCommand(@RequestParam("payload") String payload) {
        InteractiveRequest interactiveRequest;
        try {
            interactiveRequest = mapper.readValue(payload, InteractiveRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new SlackResponse("Something went wrong");
        }

        LOGGER.info(interactiveRequest.toString());

        if (inMaintenance) {
            SlackResponse maintenance = new SlackResponse("I am under maintenance to provide better service");
            maintenance.setResponseType("in_channel");
            return maintenance;
        }

        SlackResponse slackResponse = slackService.handleInteractiveRequest(interactiveRequest);

        LOGGER.info(slackResponse.toString());

        return slackResponse;
    }

}
