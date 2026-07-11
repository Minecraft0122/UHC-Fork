package com.gmail.val59000mc.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Optional;

/**
 * Convenience access to the UhcCore API registered in Bukkit's ServicesManager.
 */
public final class UhcCoreProvider {

	private UhcCoreProvider() {
	}

	public static boolean isAvailable() {
		return get().isPresent();
	}

	public static Optional<UhcCoreApi> get() {
		RegisteredServiceProvider<UhcCoreApi> registration = Bukkit.getServicesManager().getRegistration(UhcCoreApi.class);
		if (registration == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(registration.getProvider());
	}

	public static UhcCoreApi require() {
		return get().orElseThrow(() -> new IllegalStateException("UhcCore API is not available"));
	}

}
