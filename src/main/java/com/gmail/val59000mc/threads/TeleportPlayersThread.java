package com.gmail.val59000mc.threads;

import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;
import com.gmail.val59000mc.utils.LocationUtils;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class TeleportPlayersThread implements Runnable{

	private static final Logger LOGGER = Logger.getLogger(TeleportPlayersThread.class.getCanonicalName());

	private final GameManager gameManager;
	private final UhcTeam team;

	public TeleportPlayersThread(GameManager gameManager, UhcTeam team) {
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

			for(PotionEffect effect : gameManager.getConfig().get(MainConfig.POTION_EFFECT_ON_START)){
				player.addPotionEffect(effect);
			}

			uhcPlayer.freezePlayer(team.getStartingLocation());

			player.teleport(LocationUtils.withSameDirection(team.getStartingLocation(), player));

			gameManager.getPlayerManager().setPlayerStartPlaying(uhcPlayer);

			uhcPlayer.setHasBeenTeleportedToLocation(true);
		}
	}

}
