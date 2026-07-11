package com.gmail.val59000mc.events;

import com.gmail.val59000mc.api.UHCMatchService;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class UHCGameStartEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final UHCMatchService match;
	private final long gameStartTimeMillis;

	public UHCGameStartEvent(UHCMatchService match, long gameStartTimeMillis) {
		this.match = Objects.requireNonNull(match, "match");
		this.gameStartTimeMillis = gameStartTimeMillis;
	}

	public UHCMatchService getMatch() {
		return match;
	}

	public long getGameStartTimeMillis() {
		return gameStartTimeMillis;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
