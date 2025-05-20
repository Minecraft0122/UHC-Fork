package com.gmail.val59000mc.versionadapters.adapters;

import org.bukkit.World;

import com.gmail.val59000mc.versionadapters.VersionAdapter;

public interface GetWorldMinHeightAdapter extends VersionAdapter {

	/**
	 * Gets the (inclusive) minimum height of the world.
	 *
	 * @param world the world
	 * @return the minimum height of the world
	 */
	int getWorldMinHeight(World world);

}
