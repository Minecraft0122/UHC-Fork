package com.gmail.val59000mc.api.internal;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.api.UhcCoreApi;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.exceptions.UhcPlayerDoesNotExistException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.TeamManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioManager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class UhcCoreApiImpl implements UhcCoreApi {

	private final UhcCore plugin;
	private final GameManager gameManager;

	public UhcCoreApiImpl(UhcCore plugin, GameManager gameManager) {
		this.plugin = Objects.requireNonNull(plugin, "plugin");
		this.gameManager = Objects.requireNonNull(gameManager, "gameManager");
	}

	@Override
	public UhcCore getPlugin() {
		return plugin;
	}

	@Override
	public GameManager getGameManager() {
		return gameManager;
	}

	@Override
	public MainConfig getConfig() {
		return gameManager.getConfig();
	}

	@Override
	public PlayerManager getPlayerManager() {
		return gameManager.getPlayerManager();
	}

	@Override
	public TeamManager getTeamManager() {
		return gameManager.getTeamManager();
	}

	@Override
	public ScenarioManager getScenarioManager() {
		return gameManager.getScenarioManager();
	}

	@Override
	public Optional<GameState> getGameState() {
		return Optional.ofNullable(gameManager.getGameState());
	}

	@Override
	public boolean isUhcEnabled() {
		return gameManager.getConfig().get(MainConfig.ENABLE_UHC);
	}

	@Override
	public boolean isPvpEnabled() {
		return gameManager.getPvp();
	}

	@Override
	public boolean isGameEnding() {
		return gameManager.getGameIsEnding();
	}

	@Override
	public long getElapsedTime() {
		return gameManager.getElapsedTime();
	}

	@Override
	public long getRemainingTime() {
		return gameManager.getRemainingTime();
	}

	@Override
	public long getTimeUntilNextEpisode() {
		return gameManager.getTimeUntilNextEpisode();
	}

	@Override
	public int getEpisodeNumber() {
		return gameManager.getEpisodeNumber();
	}

	@Override
	public String getFormattedRemainingTime() {
		return gameManager.getFormattedRemainingTime();
	}

	@Override
	public List<UhcPlayer> getPlayers() {
		return Collections.unmodifiableList(new ArrayList<>(getPlayerManager().getPlayersList()));
	}

	@Override
	public Set<UhcPlayer> getPlayingPlayers() {
		return Collections.unmodifiableSet(new HashSet<>(getPlayerManager().getAllPlayingPlayers()));
	}

	@Override
	public Set<UhcPlayer> getOnlinePlayingPlayers() {
		return Collections.unmodifiableSet(new HashSet<>(getPlayerManager().getOnlinePlayingPlayers()));
	}

	@Override
	public Optional<UhcPlayer> getPlayer(UUID uuid) {
		Objects.requireNonNull(uuid, "uuid");
		try {
			return Optional.of(getPlayerManager().getUhcPlayer(uuid));
		} catch (UhcPlayerDoesNotExistException ignored) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<UhcPlayer> getPlayer(String name) {
		Objects.requireNonNull(name, "name");
		try {
			return Optional.of(getPlayerManager().getUhcPlayer(name));
		} catch (UhcPlayerDoesNotExistException ignored) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<UhcPlayer> getPlayer(Player player) {
		Objects.requireNonNull(player, "player");
		return getPlayer(player.getUniqueId());
	}

	@Override
	public UhcPlayer getOrCreatePlayer(Player player) {
		Objects.requireNonNull(player, "player");
		return getPlayerManager().getOrCreateUhcPlayer(player);
	}

	@Override
	public List<UhcTeam> getTeams() {
		return Collections.unmodifiableList(new ArrayList<>(getTeamManager().getUhcTeams()));
	}

	@Override
	public List<UhcTeam> getPlayingTeams() {
		return Collections.unmodifiableList(new ArrayList<>(getTeamManager().getPlayingUhcTeams()));
	}

	@Override
	public Optional<UhcTeam> getTeam(String name) {
		Objects.requireNonNull(name, "name");
		return Optional.ofNullable(getTeamManager().getTeamByName(name));
	}

	@Override
	public List<Scenario> getRegisteredScenarios() {
		return getScenarioManager().getRegisteredScenarios();
	}

	@Override
	public Set<Scenario> getEnabledScenarios() {
		return Collections.unmodifiableSet(new HashSet<>(getScenarioManager().getEnabledScenarios()));
	}

	@Override
	public Optional<Scenario> getScenario(String key) {
		Objects.requireNonNull(key, "key");
		return getScenarioManager().getScenarioByKey(key);
	}

	@Override
	public boolean isScenarioRegistered(String key) {
		return getScenario(key).isPresent();
	}

	@Override
	public boolean isScenarioEnabled(String key) {
		return getScenario(key).map(this::isScenarioEnabled).orElse(false);
	}

	@Override
	public boolean isScenarioEnabled(Scenario scenario) {
		Objects.requireNonNull(scenario, "scenario");
		return getScenarioManager().isEnabled(scenario);
	}

	@Override
	public void registerScenario(Scenario scenario) {
		Objects.requireNonNull(scenario, "scenario");
		getScenarioManager().registerScenario(scenario);
	}

	@Override
	public boolean unregisterScenario(String key) {
		Scenario scenario = getScenario(key).orElse(null);
		if (scenario == null) {
			return false;
		}

		if (getScenarioManager().isEnabled(scenario)) {
			getScenarioManager().disableScenario(scenario);
		}
		getScenarioManager().unRegisterScenario(scenario.getKey());
		return true;
	}

	@Override
	public void enableScenario(String key) {
		getScenarioManager().enableScenario(requireScenario(key));
	}

	@Override
	public void enableScenario(Scenario scenario) {
		Objects.requireNonNull(scenario, "scenario");
		getScenarioManager().enableScenario(scenario);
	}

	@Override
	public void disableScenario(String key) {
		getScenarioManager().disableScenario(requireScenario(key));
	}

	@Override
	public void disableScenario(Scenario scenario) {
		Objects.requireNonNull(scenario, "scenario");
		getScenarioManager().disableScenario(scenario);
	}

	@Override
	public boolean toggleScenario(String key) {
		return getScenarioManager().toggleScenario(requireScenario(key));
	}

	@Override
	public boolean toggleScenario(Scenario scenario) {
		Objects.requireNonNull(scenario, "scenario");
		return getScenarioManager().toggleScenario(scenario);
	}

	@Override
	public void broadcast(String message) {
		getGameManager().broadcastMessage(Objects.requireNonNull(message, "message"));
	}

	@Override
	public void broadcastInfo(String message) {
		getGameManager().broadcastInfoMessage(Objects.requireNonNull(message, "message"));
	}

	@Override
	public void startGame() {
		getGameManager().startGame();
	}

	@Override
	public void endGame() {
		getGameManager().endGame();
	}

	private Scenario requireScenario(String key) {
		return getScenario(key)
			.orElseThrow(() -> new IllegalArgumentException("No scenario is registered with key: " + key));
	}

}
