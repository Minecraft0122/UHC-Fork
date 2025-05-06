package com.gmail.val59000mc.versionadapters.adapters;

import org.bukkit.WorldCreator;

import com.gmail.val59000mc.versionadapters.VersionAdapter;

public interface SetBiomeProviderAdapter extends VersionAdapter {

	/**
	 * Sets the BiomeProvider used by the specified WorldCreator, if possible.
	 *
	 * @param worldCreator the WorldCreator
	 * @param biomeProvider the name of the BiomeProvider to set, or {@code null}
	 */
	void setBiomeProvider(WorldCreator worldCreator, String biomeProvider);

}
