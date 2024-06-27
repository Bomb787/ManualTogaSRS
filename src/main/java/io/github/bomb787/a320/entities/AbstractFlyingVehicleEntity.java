package io.github.bomb787.a320.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * It's like a {@link net.minecraft.entity.vehicle.VehicleEntity}, but flying.
 */
public abstract class AbstractFlyingVehicleEntity extends Entity {

    //Total wing/rotor area.
    protected float wingArea;
    //Total horizontal stabilizer area, it was just easier to name is elevatorArea. :)
    protected float elevatorArea;
    //Same as elevatorArea, this is the total vertical stabilizer area.
    protected float rudderArea;

    public AbstractFlyingVehicleEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
    }

    protected abstract float liftCoefficient();

    protected abstract float calculateLift();

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    /**
     * Simple helper method to get the 360 degree heading of an in-game angle.
     * @param angle The in-game angle, {-180 <= x <= 180}
     * @return Compass angle
     */
    protected double realFacingAngle(double angle) {
        return 180 + MathHelper.wrapDegrees(angle);
    }

    /**
     * Gets the angle needed to be added for this to face the movement angle. Negative means left, positive means right.
     * @return Correction angle
     */
    protected double getSlipAngle() {
        double movementAngle = getMovementAngle();
        if(Double.isNaN(movementAngle)) {
            return 0;
        }
        double x = realFacingAngle(movementAngle) - realFacingAngle(this.getYaw());
        if(x >= 180) {
            return -360 + x;
        } else if(x <= -180) {
            return 360 + x;
        }
        return x;
    }

    /**
     * Gets the in-game angle that this is moving at.
     * @return In-game angle of movement
     */
    protected double getMovementAngle() {
        double x = this.getVelocity().x;
        double z = this.getVelocity().z;
        double angle = Math.toDegrees(Math.atan(x / z));
        if(z >= 0) {
            return -angle;
        } else if(x <= 0) {
            return 180f - angle;
        } else {
            return -180f - angle;
        }
    }

    public abstract Item asItem();

}