package com.gmail.val59000mc.events;

import com.gmail.val59000mc.api.UHCMatchService;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UHCEntityDropModifyEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private final UHCMatchService match;
	private final LivingEntity entity;
	private final Player killer;
	private final List<ItemStack> drops;
	private int experience;
	private boolean cancelled;

	public UHCEntityDropModifyEvent(UHCMatchService match, LivingEntity entity, @Nullable Player killer, List<ItemStack> drops, int experience) {
		this.match = Objects.requireNonNull(match, "match");
		this.entity = Objects.requireNonNull(entity, "entity");
		this.killer = killer;
		this.drops = new ArrayList<>(Objects.requireNonNull(drops, "drops"));
		this.experience = experience;
	}

	public UHCMatchService getMatch() {
		return match;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	@Nullable
	public Player getKiller() {
		return killer;
	}

	public List<ItemStack> getDrops() {
		return drops;
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
