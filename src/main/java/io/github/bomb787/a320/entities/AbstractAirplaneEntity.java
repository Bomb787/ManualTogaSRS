package io.github.bomb787.a320.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public abstract class AbstractAirplaneEntity extends AbstractFlyingVehicleEntity {

    public AbstractAirplaneEntity(EntityType<?> type, World world) {
        super(type, world);
    }

}