package io.github.bomb787.a320.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public abstract class AbstractAirplaneEntity extends AbstractFlyingVehicleEntity {

    /**
     * <a href="https://eaglepubs.erau.edu/introductiontoaerospaceflightvehicles/chapter/finite-wing-characteristics/">Useful ERAU site</a>
     * <a href="https://www.grc.nasa.gov/www/k-12/VirtualAero/BottleRocket/airplane/induced.html">Useful NASA site</a>
     */
    public AbstractAirplaneEntity(EntityType<?> type, World world) {
        super(type, world);
    }

}