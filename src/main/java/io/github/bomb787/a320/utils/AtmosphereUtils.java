package io.github.bomb787.a320.utils;

import net.minecraft.entity.Entity;

/**
 * Some (bad) math to help with calculations that have to do with the atmosphere.
 */
public class AtmosphereUtils {

    private static final float cToFRate = 9/5f;
    private static final float fToCRate = 5/9f;

    /**
     * Approximation of atmospheric pressure changes from altitude.
     * Assumes that pressure at Y=63 is 1013.25hPa/29.92inHg.
     * Also assumes that temperature is uniform across the world as biomes would make for some abrupt changes.
     * @param alt Y coordinate
     * @return Atmospheric pressure in hPa, 1013.25 at Y=63.
     */
    public static float pressureAtAltitude(float alt) {
        return (float) (1013.25 * Math.pow((1-0.001127885*(alt-63)), 5.25588));
    }

    /**
     * Returns the ratio of the pressure at the current/input altitude to the pressure at Y=63.
     * @param alt Y coordinate
     * @return Pressure ratio (curent to sea level/Y=63)
     */
    public static float pressureRatio(float alt) {
        return pressureAtAltitude(alt) / 1013.25f;
    }

    public static float temperature(Entity entity) {
        float modifier = entity.getWorld().getBiome(entity.getBlockPos()).value().getTemperature();
        return 1f;
    }

    /**
     * Air density calculated from atmospheric pressure and temperature
     * Ignores temperature and other biome attributes to avoid abrupt changes.
     * Global relative humidity of 25%.
     * The formula looks horrible but trust :)
     * Formula based off the one from Omni Calculator
     * @param pressure Atmospheric pressure in hPa
     * @param temperature Temperature in Celsius
     * @return Air density in kg/m^3
     */
    public static float airDensity(float pressure, float temperature) {
        double x = 0.99999683
                +temperature*(-0.90826951E-02+temperature*(0.78736169E-04
                +temperature*(-0.61117958E-06+temperature*(0.43884187E-08
                +temperature*(-0.29883885E-10+temperature*(0.21874425E-12
                +temperature*(-0.17892321E-14+temperature*(0.11112018E-16
                +temperature*(-0.30994571E-19)))))))));
        for(int i = 0; i < 8; i++) {
            x = x*x;
        }
        float vapor = (float) (25 * (6.1078 / x));
        float dry = (pressure - vapor) / (287.058f * (temperature + 273.15f));
        vapor = (float) (vapor / (461.495 * (temperature + 273.15)));
        return dry + vapor;
    }

    /**
     * Converts Celsius to Fahrenheit
     * @param celsius ...
     * @return ...
     */
    public static float cToF(float celsius) {
        return (celsius * cToFRate) + 32;
    }

    /**
     * Converts Fahrenheit to Celsius
     * @param fahrenheit ...
     * @return ...
     */
    public static float FtoC(float fahrenheit) {
        return (fahrenheit - 32) * fToCRate;
    }

}