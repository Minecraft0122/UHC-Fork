package com.gmail.val59000mc.events;

import com.gmail.val59000mc.api.UHCMatchService;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class UHCExperienceModifyEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private final UHCMatchService match;
	private final Location location;
	private int experience;
	private boolean cancelled;

	public UHCExperienceModifyEvent(UHCMatchService match, Location location, int experience) {
		this.match = Objects.requireNonNull(match, "match");
		this.location = Objects.requireNonNull(location, "location").clone();
		this.experience = experience;
	}

	public UHCMatchService getMatch() {
		return match;
	}

	public Location getLocation() {
		return location.clone();
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = Math.max(0, experience);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
