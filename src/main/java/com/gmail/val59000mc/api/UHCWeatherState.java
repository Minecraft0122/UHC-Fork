package com.gmail.val59000mc.api;

import org.bukkit.World;

import java.util.Objects;

/**
 * Snapshot of a world's weather state.
 */
public final class UHCWeatherState {

	private final boolean storm;
	private final boolean thundering;
	private final int weatherDuration;
	private final int thunderDuration;
	private final int clearWeatherDuration;

	public UHCWeatherState(boolean storm, boolean thundering, int weatherDuration, int thunderDuration, int clearWeatherDuration) {
		this.storm = storm;
		this.thundering = thundering;
		this.weatherDuration = weatherDuration;
		this.thunderDuration = thunderDuration;
		this.clearWeatherDuration = clearWeatherDuration;
	}

	public static UHCWeatherState of(World world) {
		Objects.requireNonNull(world, "world");
		return new UHCWeatherState(
			world.hasStorm(),
			world.isThundering(),
			world.getWeatherDuration(),
			world.getThunderDuration(),
			world.getClearWeatherDuration()
		);
	}

	public static UHCWeatherState clear(int durationTicks) {
		return new UHCWeatherState(false, false, durationTicks, 0, durationTicks);
	}

	public static UHCWeatherState rain(int durationTicks) {
		return new UHCWeatherState(true, false, durationTicks, 0, 0);
	}

	public static UHCWeatherState thunder(int durationTicks) {
		return new UHCWeatherState(true, true, durationTicks, durationTicks, 0);
	}

	public boolean hasStorm() {
		return storm;
	}

	public boolean isThundering() {
		return thundering;
	}

	public int getWeatherDuration() {
		return weatherDuration;
	}

	public int getThunderDuration() {
		return thunderDuration;
	}

	public int getClearWeatherDuration() {
		return clearWeatherDuration;
	}

	public void applyTo(World world) {
		Objects.requireNonNull(world, "world");
		world.setStorm(storm);
		world.setThundering(thundering);
		world.setWeatherDuration(weatherDuration);
		world.setThunderDuration(thunderDuration);
		world.setClearWeatherDuration(clearWeatherDuration);
	}

}
