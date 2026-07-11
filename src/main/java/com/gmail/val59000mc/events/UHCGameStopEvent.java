package com.gmail.val59000mc.events;

import com.gmail.val59000mc.api.UHCMatchService;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class UHCGameStopEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final UHCMatchService match;
	private final String reason;

	public UHCGameStopEvent(UHCMatchService match, String reason) {
		this.match = Objects.requireNonNull(match, "match");
		this.reason = Objects.requireNonNull(reason, "reason");
	}

	public UHCMatchService getMatch() {
		return match;
	}

	public String getReason() {
		return reason;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
