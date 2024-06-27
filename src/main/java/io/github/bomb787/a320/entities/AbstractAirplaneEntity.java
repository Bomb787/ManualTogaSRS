package io.github.bomb787.a320.entities;

import io.github.bomb787.a320.utils.AtmosphereUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class AbstractAirplaneEntity extends AbstractFlyingVehicleEntity {

    protected float aoa;

    /**
     * <a href="https://eaglepubs.erau.edu/introductiontoaerospaceflightvehicles/chapter/finite-wing-characteristics/">Useful ERAU site</a>
     * <a href="https://www.grc.nasa.gov/www/k-12/VirtualAero/BottleRocket/airplane/induced.html">Useful NASA site</a>
     */
    public AbstractAirplaneEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

    }

    /**
     * Lift = Coefficient of lift * ((density * velocity^2) / 2) * area.
     * @return lift in Newtons :D
     */
    @Override
    protected float calculateLift() {
        //Percentage of the current velocity that is flowing over the chord of the wing (this is very unrealistic).
        //Multiplied by 20 to get velocity per second
        double chordVelocity = 20 * this.getVelocity().length() * (MathHelper.cos((MathHelper.PI * Math.abs((float)this.getSlipAngle())) / 90f) / 2f + 0.5);
        return (float) (this.liftCoefficient() * ((AtmosphereUtils.densityAtAltitude(this) * chordVelocity * chordVelocity) / 2f) * this.wingArea);
    }

    //TODO NACA23012, 2412 might also be a good option
    @Override
    protected float liftCoefficient() {
        return 69f;
    }

    @Override
    protected float calculateDrag() {
        return 1f;
    }

    /**
     * D = induced drag + parasitic drag
     * @return Total drag coefficient
     */
    @Override
    protected float dragCoefficient() {
        return 69;
    }

}