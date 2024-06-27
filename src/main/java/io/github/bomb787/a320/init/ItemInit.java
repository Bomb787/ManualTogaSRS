package io.github.bomb787.a320.init;

import io.github.bomb787.a320.A320Mod;
import io.github.bomb787.a320.items.DebugItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemInit {

    public static final DebugItem DEBUG_ITEM = Registry.register(Registries.ITEM, Identifier.of(A320Mod.MOD_ID, "debug"), new DebugItem(new Item.Settings()));

}