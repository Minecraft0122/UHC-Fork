package com.gmail.val59000mc.api.internal;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.api.UHCAddon;
import com.gmail.val59000mc.api.UhcCoreApi;
import com.gmail.val59000mc.api.UHCWeatherState;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.events.UHCGameEndEvent;
import com.gmail.val59000mc.events.UHCGamePauseEvent;
import com.gmail.val59000mc.events.UHCGameResumeEvent;
import com.gmail.val59000mc.events.UHCGameStartEvent;
import com.gmail.val59000mc.events.UHCGameStopEvent;
import com.gmail.val59000mc.exceptions.UhcPlayerDoesNotExistException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.TeamManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class UhcCoreApiImpl implements UhcCoreApi, Listener {

	private static final Logger LOGGER = Logger.getLogger(UhcCoreApiImpl.class.getCanonicalName());

	private final UhcCore plugin;
	private final GameManager gameManager;
	private final Set<UHCAddon> addons;
	private final Map<UUID, UHCWeatherState> weatherRestoreStates;
	private final Map<UUID, Integer> weatherRestoreTasks;

	public UhcCoreApiImpl(UhcCore plugin, GameManager gameManager) {
		this.plugin = Objects.requireNonNull(plugin, "plugin");
		this.gameManager = Objects.requireNonNull(gameManager, "gameManager");
		this.addons = ConcurrentHashMap.newKeySet();
		this.weatherRestoreStates = new HashMap<>();
		this.weatherRestoreTasks = new HashMap<>();
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
	public GameState getState() {
		return gameManager.getGameState();
	}

	@Override
	public boolean isUhcEnabled() {
		return gameManager.getConfig().get(MainConfig.ENABLE_UHC);
	}

	@Override
	public boolean isRunning() {
		GameState state = gameManager.getGameState();
		return state == GameState.PLAYING || state == GameState.DEATHMATCH;
	}

	@Override
	public boolean isPaused() {
		return gameManager.isGamePaused();
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
	public long getElapsedSeconds() {
		return gameManager.getElapsedTime();
	}

	@Override
	public long getGameStartTimeMillis() {
		return gameManager.getGameStartTimeMillis();
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
	public Collection<World> getGameWorlds() {
		List<World> worlds = new ArrayList<>();
		addWorldIfPresent(worlds, World.Environment.NORMAL);
		addWorldIfPresent(worlds, World.Environment.NETHER);
		addWorldIfPresent(worlds, World.Environment.THE_END);
		return Collections.unmodifiableList(worlds);
	}

	@Override
	public boolean isGameWorld(World world) {
		Objects.requireNonNull(world, "world");
		UUID worldUid = world.getUID();
		return getGameWorlds().stream().anyMatch(gameWorld -> gameWorld.getUID().equals(worldUid));
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
	public Collection<Player> getAlivePlayers() {
		List<Player> players = getPlayerManager().getOnlinePlayingPlayers().stream()
			.map(uhcPlayer -> Bukkit.getPlayer(uhcPlayer.getUuid()))
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
		return Collections.unmodifiableList(players);
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
	public boolean isParticipant(Player player) {
		return isRunning() && getPlayer(player)
			.map(uhcPlayer -> uhcPlayer.isPlaying() || uhcPlayer.isDead())
			.orElse(false);
	}

	@Override
	public boolean isAlive(Player player) {
		return isRunning() && getPlayer(player).map(UhcPlayer::isPlaying).orElse(false);
	}

	@Override
	public boolean isSpectator(Player player) {
		return getPlayer(player).map(UhcPlayer::isDead).orElse(false);
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
	public Collection<UhcTeam> getAliveTeams() {
		return getPlayingTeams();
	}

	@Override
	public Optional<UhcTeam> getTeam(String name) {
		Objects.requireNonNull(name, "name");
		return Optional.ofNullable(getTeamManager().getTeamByName(name));
	}

	@Override
	public Optional<UhcTeam> getTeam(Player player) {
		return getPlayer(player).map(UhcPlayer::getTeam);
	}

	@Override
	public Optional<WorldBorder> getWorldBorder(World world) {
		Objects.requireNonNull(world, "world");
		if (!isGameWorld(world)) {
			return Optional.empty();
		}
		return Optional.of(world.getWorldBorder());
	}

	@Override
	public UHCWeatherState getWeatherState(World world) {
		return UHCWeatherState.of(world);
	}

	@Override
	public boolean isWeatherControlled(World world) {
		Objects.requireNonNull(world, "world");
		return weatherRestoreStates.containsKey(world.getUID());
	}

	@Override
	public void setTemporaryWeather(World world, UHCWeatherState weatherState, long durationTicks) {
		Objects.requireNonNull(world, "world");
		Objects.requireNonNull(weatherState, "weatherState");
		claimWeatherControl(world);
		weatherState.applyTo(world);

		UUID worldId = world.getUID();
		cancelWeatherRestoreTask(worldId);
		if (durationTicks > 0) {
			int taskId = Bukkit.getScheduler().runTaskLater(plugin, () -> releaseWeatherControl(world, true), durationTicks).getTaskId();
			weatherRestoreTasks.put(worldId, taskId);
		}
	}

	@Override
	public void claimWeatherControl(World world) {
		Objects.requireNonNull(world, "world");
		weatherRestoreStates.putIfAbsent(world.getUID(), UHCWeatherState.of(world));
	}

	@Override
	public void releaseWeatherControl(World world, boolean restore) {
		Objects.requireNonNull(world, "world");
		UUID worldId = world.getUID();
		cancelWeatherRestoreTask(worldId);
		UHCWeatherState restoreState = weatherRestoreStates.remove(worldId);
		if (restore && restoreState != null) {
			restoreState.applyTo(world);
		}
	}

	public void releaseAllWeatherControls(boolean restore) {
		for (World world : new ArrayList<>(getGameWorlds())) {
			if (isWeatherControlled(world)) {
				releaseWeatherControl(world, restore);
			}
		}
		weatherRestoreStates.clear();
		weatherRestoreTasks.clear();
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

	@Override
	public void pauseGame(String reason) {
		getGameManager().pauseGame(Objects.requireNonNull(reason, "reason"));
	}

	@Override
	public void resumeGame() {
		getGameManager().resumeGame();
	}

	@Override
	public boolean registerAddon(UHCAddon addon) {
		return addons.add(Objects.requireNonNull(addon, "addon"));
	}

	@Override
	public boolean unregisterAddon(UHCAddon addon) {
		return addons.remove(Objects.requireNonNull(addon, "addon"));
	}

	@Override
	public Set<UHCAddon> getAddons() {
		return Collections.unmodifiableSet(new HashSet<>(addons));
	}

	@EventHandler
	public void onMatchStart(UHCGameStartEvent event) {
		notifyAddons(addon -> addon.onMatchStart(event.getMatch()));
	}

	@EventHandler
	public void onMatchEnd(UHCGameEndEvent event) {
		notifyAddons(addon -> addon.onMatchEnd(event.getMatch()));
	}

	@EventHandler
	public void onMatchStop(UHCGameStopEvent event) {
		notifyAddons(addon -> addon.onMatchStop(event.getMatch(), event.getReason()));
	}

	@EventHandler
	public void onMatchPause(UHCGamePauseEvent event) {
		notifyAddons(addon -> addon.onMatchPause(event.getMatch(), event.getReason()));
	}

	@EventHandler
	public void onMatchResume(UHCGameResumeEvent event) {
		notifyAddons(addon -> addon.onMatchResume(event.getMatch()));
	}

	private Scenario requireScenario(String key) {
		return getScenario(key)
			.orElseThrow(() -> new IllegalArgumentException("No scenario is registered with key: " + key));
	}

	private void addWorldIfPresent(List<World> worlds, World.Environment environment) {
		World world = gameManager.getMapLoader().getUhcWorld(environment);
		if (world != null) {
			worlds.add(world);
		}
	}

	private void cancelWeatherRestoreTask(UUID worldId) {
		Integer taskId = weatherRestoreTasks.remove(worldId);
		if (taskId != null) {
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}

	private void notifyAddons(Consumer<UHCAddon> action) {
		for (UHCAddon addon : addons) {
			try {
				action.accept(addon);
			} catch (RuntimeException ex) {
				LOGGER.log(Level.WARNING, "UHC addon callback failed", ex);
			}
		}
	}

}
