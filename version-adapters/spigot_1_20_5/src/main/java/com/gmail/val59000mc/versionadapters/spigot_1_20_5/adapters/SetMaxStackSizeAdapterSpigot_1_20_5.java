package com.gmail.val59000mc.versionadapters.spigot_1_20_5.adapters;

import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.val59000mc.versionadapters.adapters.SetMaxStackSizeAdapter;
import com.google.auto.service.AutoService;

import io.papermc.lib.PaperLib;

@AutoService(SetMaxStackSizeAdapter.class)
public class SetMaxStackSizeAdapterSpigot_1_20_5 implements SetMaxStackSizeAdapter {

	@Override
	public boolean isCompatible() {
		return PaperLib.isVersion(20, 5);
	}

	@Override
	public void setMaxStackSize(ItemMeta meta, int stackSize) {
		meta.setMaxStackSize(stackSize);
	}

}
