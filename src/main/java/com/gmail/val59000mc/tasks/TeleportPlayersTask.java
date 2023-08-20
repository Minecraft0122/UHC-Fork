package com.gmail.val59000mc.tasks;

import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;
import com.gmail.val59000mc.utils.LocationUtils;

import java.util.logging.Logger;

import org.bukkit.entity.Player;

public class TeleportPlayersTask implements Runnable{

	private static final Logger LOGGER = Logger.getLogger(TeleportPlayersTask.class.getCanonicalName());

	private final GameManager gameManager;
	private final UhcTeam team;

	public TeleportPlayersTask(GameManager gameManager, UhcTeam team) {
		this.gameManager = gameManager;
		this.team = team;
	}

	@Override
	public void run() {

		for(UhcPlayer uhcPlayer : team.getMembers()){
			Player player;
			try {
				player = uhcPlayer.getPlayer();
			} catch (UhcPlayerNotOnlineException ignored) {
				continue;
			}

			LOGGER.info("Teleporting " + player.getName() + " to " + team.getStartingLocation());

			uhcPlayer.freezePlayer(team.getStartingLocation());

			UhcPlayer.teleport(player, LocationUtils.withSameDirection(team.getStartingLocation(), player));

			gameManager.getPlayerManager().setPlayerStartPlaying(uhcPlayer);

			uhcPlayer.setHasBeenTeleportedToLocation(true);
		}
	}

}
