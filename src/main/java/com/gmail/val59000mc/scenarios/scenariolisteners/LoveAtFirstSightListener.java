package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.events.UhcStartingEvent;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;
import com.gmail.val59000mc.scenarios.Option;
import com.gmail.val59000mc.scenarios.ScenarioListener;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LoveAtFirstSightListener extends ScenarioListener{

	private static final Logger LOGGER = Logger.getLogger(LoveAtFirstSightListener.class.getCanonicalName());

	@Option(key = "disable-broadcasts")
	private boolean disableBroadcasts = false;

	@Option(key = "max-players-per-team")
	private int maxPlayersPerTeam = -1;

	@Override
	public void onEnable() {
		if (maxPlayersPerTeam <= 0 && maxPlayersPerTeam != -1) {
			LOGGER.warning("Invalid value for love_at_first_sight.max-players-per-team");
		} else if (getActualTeamSize() == 1) {
			LOGGER.warning("max-players-per-team is set to 1, this will make Love at First Sight pointless");
		}
	}

	// Priority above DoubleDatesListener#onGameStateChanged
	@EventHandler(priority = EventPriority.HIGH)
	public void onGameStarting(UhcStartingEvent e) {
		for (final UhcPlayer player : getPlayerManager().getPlayersList()) {
			if (!player.getTeam().isSolo() && !player.isTeamLeader()) {
				// Teams are updated on the tab list by UpdateScoreboardTask once the game starts
				player.setTeam(new UhcTeam());
			}
		}
	}

	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerDamage(EntityDamageByEntityEvent e){
		// NOTE: We don't check e.isCancelled() here, because we are interested
		// in the action of left-clicking itself. If the event has been canceled
		// (as in, the damage is canceled), via the time-before-pvp setting for
		// example, we still consider this action a valid left click,
		// for the purposes of joining someone's team.

		if (e.getEntityType() != EntityType.PLAYER || !(e.getDamager() instanceof Player)){
			return;
		}

		PlayerManager pm = getPlayerManager();
		UhcPlayer uhcDamaged = pm.getUhcPlayer((Player) e.getEntity());
		UhcPlayer uhcDamager = pm.getUhcPlayer((Player) e.getDamager());

		if (getGameManager().getGameState() != GameState.PLAYING){
			return;
		}

		// Spectator admins (who may be in creative mode) should be ignored, for example
		if (!uhcDamager.isPlaying() || !uhcDamaged.isPlaying()) {
			return;
		}

		if (isTeamFull(uhcDamaged.getTeam()) || isTeamFull(uhcDamager.getTeam())){
			return; // One of the teams is full so no team can be made
		}

		if (!uhcDamaged.getTeam().isSolo() && !uhcDamager.getTeam().isSolo()){
			return; // Neither of the players are solo so a team can't be created
		}

		if (getTeamManager().getPlayingUhcTeams().size() <= 2){
			return; // Only 2 teams left, don't team them up but let them first.
		}

		boolean result;
		if (uhcDamaged.getTeam().isSolo()){
			// add to damager team
			result = addPlayerToTeam(uhcDamaged, uhcDamager.getTeam());
		}else{
			// add damager to damaged
			result = addPlayerToTeam(uhcDamager, uhcDamaged.getTeam());
		}

		if (result){
			e.setCancelled(true);
		}
	}

	private int getActualTeamSize() {
		if (maxPlayersPerTeam <= 0) {
			return getConfiguration().get(MainConfig.MAX_PLAYERS_PER_TEAM);
		}
		return maxPlayersPerTeam;
	}

	private boolean isTeamFull(UhcTeam team) {
		return getActualTeamSize() == team.getMembers().size();
	}

	private boolean addPlayerToTeam(UhcPlayer player, UhcTeam team){
		if (isTeamFull(team)) return false;
		Inventory teamInventory = team.getTeamInventory();

		for (ItemStack item : player.getTeam().getTeamInventory().getContents()){
			if (item == null || item.getType() == Material.AIR){
				continue;
			}

			if (teamInventory.getContents().length < teamInventory.getSize()){
				teamInventory.addItem(item);
			}else {
				try {
					Player bukkitPlayer = player.getPlayer();
					bukkitPlayer.getWorld().dropItem(bukkitPlayer.getLocation(), item);
				} catch (UhcPlayerNotOnlineException ignored) {
					// Shouldn't happen
				}
			}
		}

		player.setTeam(team);

		team.sendMessage(Lang.TEAM_MESSAGE_PLAYER_JOINS.replace("%player%", player.getName()));
		GameManager gm = GameManager.getGameManager();
		gm.getScoreboardManager().updateTeamOnTab(team);
		if (!disableBroadcasts){
			gm.broadcastMessage(Lang.SCENARIO_LOVEATFIRSTSIGHT_JOIN_BROADCAST.replace("%player%", player.getName()).replace("%leader%", team.getLeader().getName()));
		}
		return true;
	}

}
