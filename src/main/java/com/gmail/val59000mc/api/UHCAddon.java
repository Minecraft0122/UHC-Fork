package com.gmail.val59000mc.api;

/**
 * Optional addon lifecycle adapter for plugins that want direct callbacks in
 * addition to Bukkit events.
 */
public interface UHCAddon {

	default void onMatchStart(UHCMatchService match) {
	}

	default void onMatchEnd(UHCMatchService match) {
	}

	default void onMatchStop(UHCMatchService match, String reason) {
	}

	default void onMatchPause(UHCMatchService match, String reason) {
	}

	default void onMatchResume(UHCMatchService match) {
	}

}
