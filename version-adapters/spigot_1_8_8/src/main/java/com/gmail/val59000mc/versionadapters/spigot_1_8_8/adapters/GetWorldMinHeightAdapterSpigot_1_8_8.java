package com.gmail.val59000mc.versionadapters.spigot_1_8_8.adapters;

import org.bukkit.World;

import com.gmail.val59000mc.versionadapters.adapters.GetWorldMinHeightAdapter;
import com.google.auto.service.AutoService;

import io.papermc.lib.PaperLib;

@AutoService(GetWorldMinHeightAdapter.class)
public class GetWorldMinHeightAdapterSpigot_1_8_8 implements GetWorldMinHeightAdapter {

	@Override
	public boolean isCompatible() {
		return PaperLib.isVersion(8, 8) && !PaperLib.isVersion(17);
	}

	@Override
	public int getWorldMinHeight(World world) {
		return 0;
	}

}
