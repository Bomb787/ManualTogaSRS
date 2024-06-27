package io.github.bomb787.a320.entities.a320;

import io.github.bomb787.a320.entities.AbstractAirplaneEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class A320Entity extends AbstractAirplaneEntity {

    public A320Entity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public float liftCoefficient() {
        return A320FlightModel.CL_TABLE[(int)((aoa+180) * A320FlightModel.CONSTANT) & 65535];
    }

    @Override
    public Item asItem() {
        return null;
    }

}