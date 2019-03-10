package com.mbcoder.officeeyes.controller;

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

import java.util.concurrent.TimeUnit;

@RestController
public class SlackController {

    public static final Logger LOGGER = LoggerFactory.getLogger(SlackController.class);

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
        long start = System.nanoTime();
        SlackRequest slackRequest = new SlackRequest(teamId, teamDomain, channelId, channelName, userId, userName, command, text, responseUrl);

        LOGGER.debug(slackRequest.toString());

        if (inMaintenance) {
            SlackResponse maintenance = new SlackResponse("I am under maintenance to provide better service");
            maintenance.setResponseType(slackRequest.getResponseType());
            return maintenance;
        }

        SlackResponse slackResponse = slackService.handleSlashCommand(slackRequest);

        LOGGER.debug(slackResponse.toString());

        long finish = System.nanoTime();
        LOGGER.debug("Elapsed Time: {}", TimeUnit.NANOSECONDS.toMillis(finish - start));

        return slackResponse;
    }

}
