package io.github.bomb787.a320.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebugItem extends Item {

    public DebugItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!world.isClient){
            user.sendMessage(Text.literal(String.valueOf(world.getBiome(user.getBlockPos()).value().getTemperature())));
            user.sendMessage(Text.literal(String.valueOf(world.getBiome(user.getBlockPos()).value().isCold(user.getBlockPos()))));
        }

        return TypedActionResult.pass(itemStack);
    }

}
