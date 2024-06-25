package io.github.bomb787.a320.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public abstract class AbstractHelicopterEntity extends AbstractFlyingVehicleEntity {

    public AbstractHelicopterEntity(EntityType<?> type, World world) {
        super(type, world);
    }

}