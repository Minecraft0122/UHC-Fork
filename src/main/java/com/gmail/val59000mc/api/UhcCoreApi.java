package com.gmail.val59000mc.api;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.TeamManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioManager;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Public entry point for other plugins that integrate with UhcCore.
 *
 * <p>Prefer obtaining this API through {@link UhcCoreProvider} or Bukkit's
 * ServicesManager instead of depending on UhcCore internals directly.</p>
 */
public interface UhcCoreApi {

	UhcCore getPlugin();

	GameManager getGameManager();

	MainConfig getConfig();

	PlayerManager getPlayerManager();

	TeamManager getTeamManager();

	ScenarioManager getScenarioManager();

	Optional<GameState> getGameState();

	boolean isUhcEnabled();

	boolean isPvpEnabled();

	boolean isGameEnding();

	long getElapsedTime();

	long getRemainingTime();

	long getTimeUntilNextEpisode();

	int getEpisodeNumber();

	String getFormattedRemainingTime();

	List<UhcPlayer> getPlayers();

	Set<UhcPlayer> getPlayingPlayers();

	Set<UhcPlayer> getOnlinePlayingPlayers();

	Optional<UhcPlayer> getPlayer(UUID uuid);

	Optional<UhcPlayer> getPlayer(String name);

	Optional<UhcPlayer> getPlayer(Player player);

	UhcPlayer getOrCreatePlayer(Player player);

	List<UhcTeam> getTeams();

	List<UhcTeam> getPlayingTeams();

	Optional<UhcTeam> getTeam(String name);

	List<Scenario> getRegisteredScenarios();

	Set<Scenario> getEnabledScenarios();

	Optional<Scenario> getScenario(String key);

	boolean isScenarioRegistered(String key);

	boolean isScenarioEnabled(String key);

	boolean isScenarioEnabled(Scenario scenario);

	void registerScenario(Scenario scenario);

	boolean unregisterScenario(String key);

	void enableScenario(String key);

	void enableScenario(Scenario scenario);

	void disableScenario(String key);

	void disableScenario(Scenario scenario);

	boolean toggleScenario(String key);

	boolean toggleScenario(Scenario scenario);

	void broadcast(String message);

	void broadcastInfo(String message);

	void startGame();

	void endGame();

}
