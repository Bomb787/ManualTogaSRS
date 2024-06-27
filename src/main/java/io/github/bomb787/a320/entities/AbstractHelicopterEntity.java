package io.github.bomb787.a320.entities;

import io.github.bomb787.a320.utils.AtmosphereUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static java.lang.Math.log;

public abstract class AbstractHelicopterEntity extends AbstractFlyingVehicleEntity {

    //Radius in meters/blocks
    protected float rotorRadius;
    protected float rpm;
    protected int bladeNumber;
    protected float point7Radius;

    public AbstractHelicopterEntity(EntityType<?> type, World world) {
        super(type, world);
        this.point7Radius = this.rotorRadius * 2 * MathHelper.PI * 0.7f;
    }

    /**
     * Lift = Lift coefficient * (area / 2) * density * velocity^2, where velocity is the rotational velocity at 70% of the rotor radius. Translational lift will be simplified because idk how to calculate that.
     * From <a href="https://drones.stackexchange.com/questions/2180/how-to-calculate-how-much-lift-to-be-generated-for-my-helicopter-to-takeoff">Stack Exchange</a>
     * @return lift in Newtons :D
     */
    @Override
    protected float calculateLift() {
        float velocity = point7Radius * this.rpm;
        //Arctan version (float) (1.5 * (ONE_OVER_PI * Math.atan((this.getVelocity().length() - 25) / 4f) + 1.5))
        float translationalLift = (float) (0.5 * Math.log(this.getVelocity().length() + 1)) + 1;
        return liftCoefficient() * (this.wingArea / 2f) * AtmosphereUtils.densityAtAltitude(this) * velocity * velocity * translationalLift;
    }

    //TODO NACA0009 seems like a good default airfoil for helicopters
    @Override
    protected float liftCoefficient() {
        return 69f;
    }

}