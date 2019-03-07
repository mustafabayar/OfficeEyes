package com.mbcoder.officeeyes.model;


public class VibrationSensorData {

    private boolean motion;

    public VibrationSensorData() {
    }

    public VibrationSensorData(boolean motion) {
        this.motion = motion;
    }

    public boolean hasMotion() {
        return motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }

}
