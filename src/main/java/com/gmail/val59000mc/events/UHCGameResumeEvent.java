package com.gmail.val59000mc.events;

import com.gmail.val59000mc.api.UHCMatchService;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class UHCGameResumeEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final UHCMatchService match;

	public UHCGameResumeEvent(UHCMatchService match) {
		this.match = Objects.requireNonNull(match, "match");
	}

	public UHCMatchService getMatch() {
		return match;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
