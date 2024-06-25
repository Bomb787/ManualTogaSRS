package io.github.bomb787.a320.entities.a320;

import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.NavigableMap;
import java.util.TreeMap;

public class A320FlightModel {

    /*Couldn't think of a better way to do this, just wasn't really happy with what I could do with a polynomial.
    / Numbers are from X-Plane 12 Airfoil Maker using NACA23015 as it's fairly close to the A320's wing. No specific sample interval since that wouldn't get all the important things but it's mostly 5.
    / Of course, a more realistic calculation would have the root, kink, and tip airfoils taken into account but this is Minecraft.
    / I *may* actually use all three some time in the future but I'm just really not sure yet.
    */
    private static final NavigableMap<Integer, Float> CL_VALUES  = new TreeMap<>() {};
    private static void addClValues() {
        CL_VALUES.put(-180, 0.05750f);
        CL_VALUES.put(-175, 0.35938f);
        CL_VALUES.put(-170, 0.65605f);
        CL_VALUES.put(-165, 0.84479f);
        CL_VALUES.put(-163, 0.87963f);
        CL_VALUES.put(-161, 0.89125f);
        CL_VALUES.put(-160, 0.83375f);
        CL_VALUES.put(-155, 0.91757f);
        CL_VALUES.put(-150, 0.99319f);
        CL_VALUES.put(-145, 1.05320f);
        CL_VALUES.put(-140, 1.09172f);
        CL_VALUES.put(-135, 1.10500f);
        CL_VALUES.put(-130, 1.08821f);
        CL_VALUES.put(-125, 1.03836f);
        CL_VALUES.put(-120, 0.95696f);
        CL_VALUES.put(-115, 0.84648f);
        CL_VALUES.put(-110, 0.71028f);
        CL_VALUES.put(-105, 0.55250f);
        CL_VALUES.put(-100, 0.37793f);
        CL_VALUES.put(-95, 0.19188f);
        CL_VALUES.put(-90, 0f);
        CL_VALUES.put(-85, -0.19188f);
        CL_VALUES.put(-80, -0.37793f);
        CL_VALUES.put(-75, -0.55250f);
        CL_VALUES.put(-70, -0.71028f);
        CL_VALUES.put(-65, -0.84648f);
        CL_VALUES.put(-60, -0.95696f);
        CL_VALUES.put(-55, -1.03836f);
        CL_VALUES.put(-50, -1.08821f);
        CL_VALUES.put(-47, -1.10231f);
        CL_VALUES.put(-43, -1.10614f);
        CL_VALUES.put(-40, -1.11210f);
        CL_VALUES.put(-35, -1.13269f);
        CL_VALUES.put(-30, -1.16477f);
        CL_VALUES.put(-25, -1.20519f);
        CL_VALUES.put(-20, -1.25000f);
        CL_VALUES.put(-19, -1.35000f);
        CL_VALUES.put(-18, -1.34495f);
        CL_VALUES.put(-17, -1.32980f);
        CL_VALUES.put(-16, -1.30455f);
        CL_VALUES.put(-15, -1.26920f);
        CL_VALUES.put(-10, -0.94095f);
        CL_VALUES.put(0, 0.10000f);
        CL_VALUES.put(8, 0.94000f);
        CL_VALUES.put(10, 1.14095f);
        CL_VALUES.put(13, 1.36820f);
        CL_VALUES.put(15, 1.46920f);
        CL_VALUES.put(16, 1.50455f);
        CL_VALUES.put(17, 1.52980f);
        CL_VALUES.put(18, 1.54495f);
        CL_VALUES.put(19, 1.55000f);
        CL_VALUES.put(20, 1.45000f);
        CL_VALUES.put(30, 1.24721f);
        CL_VALUES.put(35, 1.17089f);
        CL_VALUES.put(40, 1.12189f);
        CL_VALUES.put(43, 1.10772f);
        CL_VALUES.put(46, 1.10568f);
        CL_VALUES.put(49, 1.09425f);
        CL_VALUES.put(50, 1.08821f);
        CL_VALUES.put(55, 1.03836f);
        CL_VALUES.put(60, 0.95696f);
        CL_VALUES.put(65, 0.84648f);
        CL_VALUES.put(70, 0.71028f);
        CL_VALUES.put(75, 0.55250f);
        CL_VALUES.put(80, 0.37793f);
        CL_VALUES.put(85, 0.19188f);
        CL_VALUES.put(90, 0f);
        CL_VALUES.put(95, -0.19188f);
        CL_VALUES.put(100, -0.37793f);
        CL_VALUES.put(105, -0.55250f);
        CL_VALUES.put(110, -0.71028f);
        CL_VALUES.put(115, -0.84648f);
        CL_VALUES.put(120, -0.95696f);
        CL_VALUES.put(125, -1.03836f);
        CL_VALUES.put(130, -1.08821f);
        CL_VALUES.put(135, -1.10500f);
        CL_VALUES.put(140, -1.08610f);
        CL_VALUES.put(145, -1.03123f);
        CL_VALUES.put(150, -0.94578f);
        CL_VALUES.put(155, -0.83811f);
        CL_VALUES.put(160, -0.71875f);
        CL_VALUES.put(161, -0.77625f);
        CL_VALUES.put(163, -0.76463f);
        CL_VALUES.put(165, -0.72979f);
        CL_VALUES.put(170, -0.54105f);
        CL_VALUES.put(175, -0.24437f);
        CL_VALUES.put(180, 0.05750f);
    }

    private static final float CONSTANT = 65534/360f;

    private static float[] CL_TABLE;

    public A320FlightModel() {
        addClValues();
        CL_TABLE = Util.make(new float[65535], cl_table -> {
            for(int i = 0; i < cl_table.length; i++) {
                if(i == 0 || i == 32767 || i == 65534) {
                    cl_table[i] = CL_VALUES.get((int) (((360 * i) / 65534f) - 180));
                } else {
                    float delta = (Math.abs(CL_VALUES.floorKey((int) (360 * i / 65534f - 180))) - Math.abs(360 * i / 65534f - 180))
                            / (Math.abs(CL_VALUES.floorKey((int) (360 * i / 65534f - 180))) - Math.abs(CL_VALUES.higherKey((int) (360 * i / 65534f - 180))));
                    cl_table[i] = MathHelper.lerp(delta, CL_VALUES.floorEntry((int) (360 * i / 65534f - 180)).getValue(), CL_VALUES.higherEntry((int) (360 * i / 65534f - 180)).getValue());
                }
            }
        });
    }

    public static float getCl(float aoa) {
        return CL_TABLE[(int)((aoa+180) * CONSTANT) & 65535];
    }

}