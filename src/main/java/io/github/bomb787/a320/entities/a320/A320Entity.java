package io.github.bomb787.a320.entities.a320;

import io.github.bomb787.a320.entities.AbstractAirplaneEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class A320Entity extends AbstractAirplaneEntity {

    private static final float CONSTANT = 65535/360f;
    /**
     * The lift coefficient of a finite wing can be approximated by scaling it by AR / (AR+2).
     * This is of course simplified here as the shape of the wing is not taken into account.
     * Source: <a href="https://aviation.stackexchange.com/questions/14508/what-is-the-method-to-calculate-a-finite-wings-lift-from-its-sectional-airfoil">Stack Overflow</a>
     */
    private static final float SCALE = 9.4f / 11.4f;
    private static final float[] CL_TABLE = Util.make(new float[65535], cl_table -> {
        for (int i = 0; i < cl_table.length; i++) {
            cl_table[i] = SCALE * (float)stupid(((360*i)/65534d) - 180);
        }
    });


    public A320Entity(EntityType<?> type, World world, float wingArea, float elevatorArea, float rudderArea, double maxSpeed, float maxG, float dihedral) {
        super(type, world, wingArea, elevatorArea, rudderArea, maxSpeed, maxG, dihedral);
    }

    public static float liftCoefficient(float aoa1) {
        return CL_TABLE[(int)((aoa1+180) * CONSTANT) & 65534];
    }

    @Override
    protected float liftCoefficient() {
        return CL_TABLE[(int)((this.aoa+180) * CONSTANT) & 65534];
    }

    @Override
    public Item asItem() {
        return null;
    }

    /**
     * Used for generating a lookup table of lift coefficients. Values generated by X-Plane 12 Airfoil Maker.
     * @param aoa Angle of attack
     * @return Lift coefficient at given aoa.
     */
    private static double stupid(double aoa) {
        if(Math.abs(aoa) == 180) {
            return 0.0575;
        } else if(-180 < aoa && aoa < -170) {
            return 0.059855 * (aoa + 180) + 0.0575;
        } else if(-170 <= aoa && aoa < -161) {
            return -3.347427750470008e-8 * Math.pow(aoa+170, 3) - 0.002903216795800 * Math.pow(aoa+170, 2) + 0.052264995912013 * (aoa+170) + 0.65605;
        } else if(-161 <= aoa && aoa < -160) {
            return (-0.0575 * aoa) - 8.36625;
        } else if(-160 <= aoa && aoa < -135) {
            return -6.353044821702220e-6 * Math.pow(aoa+160, 3) - 1.163477589148892e-4 * Math.pow(aoa+160, 2) + 0.017729346986436 * (aoa+160) + 0.83375;
        } else if(-135 <= aoa && aoa < -90) {
            return 4.437528682453325e-6 * Math.pow(aoa+135, 3) -7.453678030560787e-4 * Math.pow(aoa+135, 2) + 1.105;
        } else if(-90 <= aoa && aoa < -45) {
            return 4.437558423202500e-6 * Math.pow(aoa+90, 3) + 1.462987542574540e-4 * Math.pow(aoa+90, 2) - 0.040125055304126 * (aoa+90);
        } else if(-45 <= aoa && aoa < -20) {
            return 3.395744922474933e-6 * Math.pow(aoa+45, 3) - 3.168936230618734e-4 * Math.pow(aoa+45, 2) - 1.105;
        } else if(-20 <= aoa && aoa < -19) {
            return (-0.1 * aoa) - 3.25;
        } else if(-19 <= aoa && aoa < -9) {
            return 2.220446049250313e-18 * Math.pow(aoa+19, 3) + 0.005050062560419 * Math.pow(aoa+19, 2) - 6.256041942251415e-7 * (aoa+19) - 1.35;
        } else if(-9 <= aoa && aoa < 9) {
            return (0.105 * aoa) + 0.1;
        } else if(9 <= aoa && aoa < 19) {
            return 1.347291664693874e-18 * Math.pow(aoa-9, 3) - 0.005050062560419 * Math.pow(aoa-9, 2) + 0.101000625604195 * (aoa-9) + 1.045;
        } else if(19 <= aoa && aoa < 20) {
            return (-0.1 * aoa) + 3.45;
        } else if(20 <= aoa && aoa < 45) {
            return 8.079950867327650e-6 * Math.pow(aoa-20, 3) + 1.480024566336180e-4 * Math.pow(aoa-20, 2) - 0.022550030707920 * (aoa-20) + 1.45;
        } else if(45 <= aoa && aoa < 90) {
            return 4.437558423202506e-6 * Math.pow(aoa-45, 3) - 7.453691413897919e-4 * Math.pow(aoa-45, 2) + 1.105;
        } else if(90 <= aoa && aoa < 135) {
            return 4.437528682453318e-6 * Math.pow(aoa-90, 3) + 1.463014309248803e-4 * Math.pow(aoa-90, 2) - 0.040125115529143*(aoa-90);
        } else if(135 <= aoa && aoa < 160) {
            return -9.046082968454799e-6 * Math.pow(aoa-135, 3) + 8.441520742113699e-4 * Math.pow(aoa-135, 2) - 1.105;
        } else if(160 <= aoa && aoa < 161) {
            return -0.0575 * (aoa-160) - 0.71875;
        } else if(161 <= aoa && aoa < 170) {
            return 1.409443263360743e-8 * Math.pow(aoa-161, 3) + 0.002903569887121 * Math.pow(aoa-161, 2) + 6.270020467520916e-8 * (aoa-161 ) - 0.77625;
        } else {
            return 0.059855 * (aoa-170) - 0.54105;
        }
    }

}