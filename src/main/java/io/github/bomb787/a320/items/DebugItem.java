package io.github.bomb787.a320.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DebugItem extends Item {

    public DebugItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!world.isClient) {
        }
        if(world.isClient) {
            user.sendMessage(Text.literal(String.valueOf(getSlipAngle(user))), true);
        }
        return TypedActionResult.pass(itemStack);
    }

    /**
     * Simple helper method to get the 360 degree heading of an in-game angle.
     * @param angle The in-game angle, {-180 <= x <= 180}
     * @return Compass angle
     */
    private static double realFacingAngle(double angle) {
        return 180 + MathHelper.wrapDegrees(angle);
    }

    /**
     * Gets the angle needed to be added for an entity to face its movement angle. Negative means left, positive means right.
     * @param entity Input entity
     * @return Correction angle
     */
    private static double getSlipAngle(Entity entity) {
        double movementAngle = getMovementAngle(entity);
        if(Double.isNaN(movementAngle)) {
            return 0;
        }
        double x = realFacingAngle(movementAngle) - realFacingAngle(entity.getHeadYaw());
        if(x >= 180) {
            return -360 + x;
        } else if(x <= -180) {
            return 360 + x;
        }
        return x;
    }

    /**
     * Gets the in-game angle that an entity is moving at.
     * @param entity Input entity
     * @return In-game angle of movement
     */
    private static double getMovementAngle(Entity entity) {
        double x = entity.getVelocity().x;
        double z = entity.getVelocity().z;
        if(z >= 0) {
            return -Math.toDegrees(Math.atan(entity.getVelocity().x/ entity.getVelocity().z));
        } else if(x <= 0) {
            return 180f - Math.toDegrees(Math.atan(entity.getVelocity().x/ entity.getVelocity().z));
        } else {
            return -180f - Math.toDegrees(Math.atan(entity.getVelocity().x/ entity.getVelocity().z));
        }
    }

}