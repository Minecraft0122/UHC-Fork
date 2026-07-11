package com.gmail.val59000mc.events;

import com.gmail.val59000mc.api.UHCMatchService;
import com.gmail.val59000mc.players.UhcPlayer;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UHCGameEndEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final UHCMatchService match;
	private final Set<UhcPlayer> winners;

	public UHCGameEndEvent(UHCMatchService match, Set<UhcPlayer> winners) {
		this.match = Objects.requireNonNull(match, "match");
		this.winners = Collections.unmodifiableSet(new HashSet<>(Objects.requireNonNull(winners, "winners")));
	}

	public UHCMatchService getMatch() {
		return match;
	}

	public Set<UhcPlayer> getWinners() {
		return winners;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
