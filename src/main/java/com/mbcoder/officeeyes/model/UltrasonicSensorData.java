package com.mbcoder.officeeyes.model;

/**
 * HC-SR04
 */
public class UltrasonicSensorData {

    // I decided to leave the decision of having `motion` to the NodeMCU.
    private boolean motion;
    private int distance;

    public UltrasonicSensorData() {
    }

    public UltrasonicSensorData(boolean motion, int distance) {
        this.motion = motion;
        this.distance = distance;
    }

    public boolean hasMotion() {
        return motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }

    public int getDistance() { return distance; }

    public void setDistance(int distance) { this.distance = distance; }
}
