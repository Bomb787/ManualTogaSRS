package io.github.bomb787.a320;

import io.github.bomb787.a320.init.ItemInit;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class A320Mod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("a320");
	public static final String MOD_ID = "a320";

	private static ItemInit ITEMS;

	@Override
	public void onInitialize() {
		LOGGER.info("A320 initialising.");
		ITEMS = new ItemInit();
	}

}