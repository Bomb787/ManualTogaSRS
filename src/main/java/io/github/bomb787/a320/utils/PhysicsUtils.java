package io.github.bomb787.a320.utils;

import net.minecraft.util.Util;

public class PhysicsUtils {

    //Generic coefficient of lift based off of NACA23012
    public static final float[] DEFAULT_CL = Util.make(new float[65536], default_cl -> {
        for(int i = 0; i < default_cl.length; i++) {
            default_cl[i] = i;
        }
    });

}