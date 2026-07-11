package com.gmail.val59000mc.events;

import com.gmail.val59000mc.api.UHCMatchService;
import com.gmail.val59000mc.players.UhcPlayer;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class UHCPlayerDeathEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final UHCMatchService match;
	private final UhcPlayer victim;
	private final UhcPlayer killer;
	private final Player bukkitKiller;
	private final Location location;
	private final boolean finalDeath;

	public UHCPlayerDeathEvent(
			UHCMatchService match,
			UhcPlayer victim,
			@Nullable UhcPlayer killer,
			@Nullable Player bukkitKiller,
			@Nullable Location location,
			boolean finalDeath
	) {
		this.match = Objects.requireNonNull(match, "match");
		this.victim = Objects.requireNonNull(victim, "victim");
		this.killer = killer;
		this.bukkitKiller = bukkitKiller;
		this.location = location == null ? null : location.clone();
		this.finalDeath = finalDeath;
	}

	public UHCMatchService getMatch() {
		return match;
	}

	public UhcPlayer getVictim() {
		return victim;
	}

	public Optional<UhcPlayer> getKiller() {
		return Optional.ofNullable(killer);
	}

	@Nullable
	public Player getBukkitKiller() {
		return bukkitKiller;
	}

	@Nullable
	public Location getLocation() {
		return location == null ? null : location.clone();
	}

	public boolean isFinalDeath() {
		return finalDeath;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
