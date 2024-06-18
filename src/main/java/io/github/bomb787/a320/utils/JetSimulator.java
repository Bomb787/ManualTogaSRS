package io.github.bomb787.a320.utils;

/**
 * Grossly oversimplified simulation of jet engine physics.
 */
public class JetSimulator {

    /**
     * "Simulates" the response delay of jet engines at lower RPMs.
     * @param n1 The N1% of the engine being simulated.
     * @param modifier Momentum modifier, high = faster response at lower RPMs, values below 0.4 may cause weirdness, 0 and negatives will definitely cause weirdness.
     * @return How quickly the engine should respond to throttle changes from 0-1.
     */
    public static float reactionMultiplier(float n1, float modifier) {
        if(n1 <= 0)
            return 0.25f;
        if(n1 >= 100)
            return 1f;
        return (float) (1 - Math.pow(1 - n1/100f, 3*modifier)) * 0.75f + 0.25f;
    }

    public static float getThrust(float n1, float maxThrust, boolean reversing) {
        float thrust = (n1 / 100) * maxThrust;
        return reversing ? -thrust * 0.5f : thrust;
    }

}