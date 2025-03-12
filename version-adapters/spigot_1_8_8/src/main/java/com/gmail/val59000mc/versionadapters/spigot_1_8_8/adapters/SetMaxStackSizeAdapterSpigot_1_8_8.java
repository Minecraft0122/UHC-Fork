package com.gmail.val59000mc.versionadapters.spigot_1_8_8.adapters;

import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.val59000mc.versionadapters.adapters.SetMaxStackSizeAdapter;
import com.google.auto.service.AutoService;

import io.papermc.lib.PaperLib;

@AutoService(SetMaxStackSizeAdapter.class)
public class SetMaxStackSizeAdapterSpigot_1_8_8 implements SetMaxStackSizeAdapter {

	@Override
	public boolean isCompatible() {
		return PaperLib.isVersion(8, 8) && !PaperLib.isVersion(20, 5);
	}

	@Override
	public void setMaxStackSize(ItemMeta meta, int stackSize) {
		// Cannot set max stack size before Minecraft 1.20.5/6, and unstackable
		// items are stackable via API either way on Minecraft versions below.
	}

}
