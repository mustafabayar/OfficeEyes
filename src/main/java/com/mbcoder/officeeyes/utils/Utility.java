package com.mbcoder.officeeyes.utils;

import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utility {

    private static List<String> occupiedStatusTexts;
    private static List<String> probablyOccupiedStatusTexts;
    private static List<String> freeStatusTexts;

    public static RichMessage defaultMessage;

    static {
        occupiedStatusTexts = new ArrayList<>();
        occupiedStatusTexts.add("Try your chance with the kicker :)");
        occupiedStatusTexts.add("Maybe next time.");
        occupiedStatusTexts.add("I am sure you had some works to do anyway.");
        occupiedStatusTexts.add("Odds are not in your favor.");
        occupiedStatusTexts.add("Or maybe someone is just trolling my sensors?");

        probablyOccupiedStatusTexts = new ArrayList<>();
        probablyOccupiedStatusTexts.add("Go and check it yourself.");
        probablyOccupiedStatusTexts.add("Try it again few seconds later.");
        probablyOccupiedStatusTexts.add("Schrödinger's Ping Pong :)");

        freeStatusTexts = new ArrayList<>();
        freeStatusTexts.add("Unless invisible ninjas are playing it.");
        freeStatusTexts.add("Would you like me to reserve it for you?");
        freeStatusTexts.add("But it would be better if you keep working.");
        freeStatusTexts.add("Hurry up before it's too late!");

        defaultMessage = new RichMessage();
        defaultMessage.setText("FREE! But I didn't had time to check the last activity time.");
    }

    public static RichMessage createRichMessage(long milliseconds) {
        if (milliseconds < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        RichMessage richMessage = new RichMessage();

        makeDecision(richMessage, milliseconds);

        return richMessage;
    }

    public static void makeDecision(RichMessage richMessage, long milliseconds) {
        Attachment[] attachments = null;
        boolean isFunnyText = Math.random() < 0.25;

        if (isFunnyText) {
            attachments = new Attachment[1];
            attachments[0] = new Attachment();
        }

        String decision = "Status Unknown!";
        if (milliseconds <= 15000) {
            decision = "OCCUPIED!";
            if (isFunnyText) {
                attachments[0].setText(getOccupiedStatusText());
            }
        } else if (15000 < milliseconds && milliseconds < 30000) {
            decision = "PROBABLY OCCUPIED!";
            if (isFunnyText) {
                attachments[0].setText(getProbablyOccupiedStatusText());
            }
        } else if (milliseconds >= 30000) {
            decision = "FREE!";
            if (isFunnyText) {
                attachments[0].setText(getFreeStatusText());
            }
        }

        String durationBreakdown = getDurationBreakdown(milliseconds);

        String response = String.format("%s Last seen activity was %s ago.", decision, durationBreakdown);
        richMessage.setText(response);

        if (attachments != null) {
            richMessage.setAttachments(attachments);
        }
    }

    public static String getDurationBreakdown(long milliseconds) {

        if (milliseconds < 1000) {
            return String.format("%04d milliseconds", milliseconds);
        }

        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;

        StringBuilder sb = new StringBuilder();

        if (days != 0) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(String.format("%02d days", days));
        }

        if (hours != 0) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(String.format("%02d hours", hours));
        }

        if (minutes != 0) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(String.format("%02d minutes", minutes));
        }

        if (seconds != 0) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(String.format("%02d seconds", seconds));
        }

        return sb.toString();
    }

    private static String getOccupiedStatusText() {
        int index = getRandomIndex(5);
        return occupiedStatusTexts.get(index);
    }

    private static String getProbablyOccupiedStatusText() {
        int index = getRandomIndex(3);
        return probablyOccupiedStatusTexts.get(index);
    }

    private static String getFreeStatusText() {
        int index = getRandomIndex(4);
        return freeStatusTexts.get(index);
    }

    private static int getRandomIndex(int range) {
        Random rand = new Random();
        return rand.nextInt(range);
    }
}
