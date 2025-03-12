package com.gmail.val59000mc.versionadapters.adapters;

import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.val59000mc.versionadapters.VersionAdapter;

public interface SetMaxStackSizeAdapter extends VersionAdapter {

	/**
	 * On Minecraft 1.20.5+, sets the max stack size. Does nothing on older versions,
	 * but those versions have no stack size limit in the API either way.
	 *
	 * @param meta the item meta to modify
	 * @param stackSize the stack size to set
	 */
	void setMaxStackSize(ItemMeta meta, int stackSize);

}
