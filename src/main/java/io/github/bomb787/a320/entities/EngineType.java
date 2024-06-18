package io.github.bomb787.a320.entities;

public enum EngineType {

    //Mostly Rough calculations and some estimates/guesses/assumptions
    CFM56(1, 1, 1, 1, 1, 1),
    V2500(0.9297f, 1.1074f, 0.95f, 0.75f, 1.25f, 1.125f),
    LEAP(1.142f, 1.0044f, 0.85f, 0.85f, 1.25f, 1.15f),
    PW1000(1.1859f, 1.0026f, 0.84f, 0.80f, 1.25f, 1.15f),
    PW6000(0.8273f, 0.8815f, 0.9f, 0.9f, 0.9f, 1.075f);

    //Multipliers compared to the CFM56
    private final float drag;
    private final float thrust;
    private final float fuelBurn;
    private final float response;
    private final float startTime;
    private final float reverse;

    EngineType(float drag, float thrust, float fuelBurn, float response, float startTime, float reverse) {
        this.drag = drag;
        this.thrust = thrust;
        this.fuelBurn = fuelBurn;
        this.response = response;
        this.startTime = startTime;
        this.reverse = reverse;
    }

    private float getDrag() {
        return drag;
    }

    private float getThrust() {
        return thrust;
    }

    private float getFuelBurn() {
        return fuelBurn;
    }

    private float getResponse() {
        return response;
    }

    private float getStartTime() {
        return startTime;
    }

    private float getReverse() {
        return reverse;
    }

}
