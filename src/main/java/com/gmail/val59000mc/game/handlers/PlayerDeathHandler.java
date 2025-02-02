package com.gmail.val59000mc.game.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.events.UhcPlayerKillEvent;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioManager;
import com.gmail.val59000mc.scenarios.scenariolisteners.SilentNightListener;
import com.gmail.val59000mc.scenarios.scenariolisteners.TeamInventoryListener;
import com.gmail.val59000mc.tasks.TimeBeforeSendBungeeTask;
import com.gmail.val59000mc.utils.UniversalMaterial;
import com.gmail.val59000mc.utils.UniversalSound;
import com.gmail.val59000mc.utils.UniversalSound.SoundParseException;
import com.gmail.val59000mc.utils.snapshot.ItemStackSnapshot;
import com.gmail.val59000mc.utils.snapshot.Snapshot;
import com.gmail.val59000mc.utils.VersionUtils;

public class PlayerDeathHandler {

	private static final Logger LOGGER = Logger.getLogger(PlayerDeathHandler.class.getCanonicalName());

	private final GameManager gameManager;
	private final ScenarioManager scenarioManager;
	private final PlayerManager playerManager;
	private final MainConfig config;
	private final CustomEventHandler customEventHandler;

	public PlayerDeathHandler(GameManager gameManager, ScenarioManager scenarioManager, PlayerManager playerManager, MainConfig config, CustomEventHandler customEventHandler) {
		this.gameManager = gameManager;
		this.scenarioManager = scenarioManager;
		this.playerManager = playerManager;
		this.config = config;
		this.customEventHandler = customEventHandler;
	}

	public void handleOnlinePlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		UhcPlayer uhcPlayer = playerManager.getUhcPlayer(player);

		List<ItemStack> modifiedDrops = handlePlayerDeath(uhcPlayer, player.getLocation(), new ArrayList<>(event.getDrops()), player.getKiller());

		// Modify event drops
		event.getDrops().clear();
		event.getDrops().addAll(modifiedDrops);

		// handle player leaving the server
		boolean canContinueToSpectate = player.hasPermission("uhc-core.spectate.override")
				|| config.get(MainConfig.CAN_SPECTATE_AFTER_DEATH);

		if (!canContinueToSpectate) {
			if (config.get(MainConfig.ENABLE_BUNGEE_SUPPORT)) {
				Bukkit.getScheduler().runTaskAsynchronously(UhcCore.getPlugin(), new TimeBeforeSendBungeeTask(playerManager, uhcPlayer, config.get(MainConfig.TIME_BEFORE_SEND_BUNGEE_AFTER_DEATH)));
			} else {
				player.kickPlayer(Lang.DISPLAY_MESSAGE_PREFIX + " " + Lang.KICK_DEAD);
			}
		}

		setDeathMessage(event);
	}

	public void handleOfflinePlayerDeath(UhcPlayer uhcPlayer, @Nullable Location location, @Nullable Player killer) {
		List<ItemStack> modifiedDrops = handlePlayerDeath(uhcPlayer, location, uhcPlayer.getStoredItems().stream().map(Snapshot::makeCopy).collect(Collectors.toList()), killer);

		// Drop player items
		if (location != null) {
			modifiedDrops.forEach(item -> location.getWorld().dropItem(location, item));
		}
	}

	private List<ItemStack> handlePlayerDeath(UhcPlayer uhcPlayer, @Nullable Location location, List<ItemStack> playerDrops, @Nullable Player killer) {
		if (uhcPlayer.getState() != PlayerState.PLAYING){
			LOGGER.warning(uhcPlayer.getName() + " died while already in 'DEAD' mode!");
			return playerDrops;
		}

		playerManager.setLastDeathTime();

		// kill event
		if(killer != null){
			UhcPlayer uhcKiller = playerManager.getUhcPlayer(killer);

			uhcKiller.addKill();

			// Call Bukkit event
			UhcPlayerKillEvent killEvent = new UhcPlayerKillEvent(uhcKiller, uhcPlayer);
			Bukkit.getServer().getPluginManager().callEvent(killEvent);

			customEventHandler.handleKillEvent(killer, uhcKiller);
		}

		// Drop the team inventory if the last player on a team was killed
		if (scenarioManager.isEnabled(Scenario.TEAM_INVENTORY))
		{
			UhcTeam team = uhcPlayer.getTeam();
			if (team.getPlayingMemberCount() == 1)
			{
				((TeamInventoryListener) scenarioManager.getScenarioListener(Scenario.TEAM_INVENTORY)).dropTeamInventory(team, location);
			}
		}

		// Store drops in case player gets re-spawned.
		uhcPlayer.getStoredItems().clear();
		uhcPlayer.getStoredItems().addAll(playerDrops.stream().map(ItemStackSnapshot::of).collect(Collectors.toList()));

		if(config.get(MainConfig.REGEN_HEAD_DROP_ON_PLAYER_DEATH)){
			playerDrops.add(UhcItems.createRegenHead(uhcPlayer));
		}

		if(location != null && config.get(MainConfig.ENABLE_GOLDEN_HEADS)){
			if (config.get(MainConfig.PLACE_HEAD_ON_FENCE) && !scenarioManager.isEnabled(Scenario.TIMEBOMB)){
				// place head on fence
				Location loc = location.clone().add(1,0,0);
				loc.getBlock().setType(UniversalMaterial.OAK_FENCE.getType());
				loc.add(0, 1, 0);
				loc.getBlock().setType(UniversalMaterial.PLAYER_HEAD_BLOCK.getType());

				Skull skull = (Skull) loc.getBlock().getState();
				VersionUtils.getVersionUtils().setSkullOwner(skull, uhcPlayer);
				skull.setRotation(BlockFace.NORTH);
				skull.update();
			}else{
				playerDrops.add(UhcItems.createGoldenHeadPlayerSkull(uhcPlayer.getName(), uhcPlayer.getUuid()));
			}
		}

		if(location != null && config.get(MainConfig.ENABLE_EXP_DROP_ON_DEATH)){
			UhcItems.spawnExtraXp(location, config.get(MainConfig.EXP_DROP_ON_DEATH));
		}

		uhcPlayer.setState(PlayerState.DEAD);

		if (config.get(MainConfig.STRIKE_LIGHTNING_ON_DEATH)) {
			playerManager.strikeLightning(uhcPlayer);
		}

		try {
			UniversalSound.parse(config.get(MainConfig.PLAYER_DEATH_SOUND), UniversalSound.WITHER_SPAWN)
				.ifPresent(playerManager::playSoundToAll);
		} catch (SoundParseException e) {
			LOGGER.log(Level.WARNING, "Unable to parse player death sound", e);
		}

		if (shouldAnnounceEliminations()) {
			gameManager.broadcastInfoMessage(Lang.PLAYERS_ELIMINATED.replace("%player%", uhcPlayer.getName()));
		}

		playerManager.checkIfRemainingPlayers();

		return playerDrops;
	}

	private void setDeathMessage(PlayerDeathEvent event) {
		if (Lang.PLAYERS_DEATH_MESSAGE.isEmpty()) {
			event.setDeathMessage(null);
		} else if (Lang.PLAYERS_DEATH_MESSAGE.equals("%original%")) {
			// If the intent is to keep the original, unmodified death message, we should avoid setting the
			// death message, because even if we call event.setDeathMessage(event.getDeathMessage()), the death
			// message may change, depending on the server platform/version. This is because the message is in string
			// form, which means that any translatable chat components will get translated using the server's locale,
			// which means that the message will no longer be translatable by the client.
			// However, on CraftBukkit/Spigot for Minecraft 1.6.1+, this is accounted for by the server, such that the
			// death message will not be changed as long as the final message of the event is equal to the initial one.
			// But this logic was removed in a Paper patch when the Kyori Adventure API got integrated, and even
			// though it initially still worked the same way because Paper was now using actual chat components instead
			// of string messages, that part was later changed so that the server translates chat components to strings:
			// https://github.com/PaperMC/Paper/commit/c1635eabb41dd5cfdaf012531481079c8d174b20
			// So in conclusion, we need to avoid setting the death message in order to avoid pre-translating it
			// server-side on Paper servers for Minecraft 1.16.5+.
			return; // Avoid setting death message
		} else {
			event.setDeathMessage(Lang.PLAYERS_DEATH_MESSAGE.replace("%original%", event.getDeathMessage()));
		}
	}

	private boolean shouldAnnounceEliminations() {
		return !scenarioManager.isEnabled(Scenario.SILENT_NIGHT) ||
			!((SilentNightListener) scenarioManager.getScenarioListener(Scenario.SILENT_NIGHT)).isNightMode();
	}

}
