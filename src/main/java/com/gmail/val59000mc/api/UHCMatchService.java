package com.gmail.val59000mc.api;

import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.players.UhcTeam;

import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

/**
 * Read-only match scope API intended for gameplay addons.
 */
public interface UHCMatchService {

	boolean isRunning();

	boolean isPaused();

	@Nullable
	GameState getState();

	long getElapsedSeconds();

	long getGameStartTimeMillis();

	Collection<World> getGameWorlds();

	boolean isGameWorld(World world);

	Collection<Player> getAlivePlayers();

	boolean isParticipant(Player player);

	boolean isAlive(Player player);

	boolean isSpectator(Player player);

	Optional<UhcTeam> getTeam(Player player);

	Collection<UhcTeam> getAliveTeams();

	Optional<WorldBorder> getWorldBorder(World world);

	UHCWeatherState getWeatherState(World world);

	boolean isWeatherControlled(World world);

}
