package com.gmail.val59000mc.tasks;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.exceptions.UhcPlayerDoesNotExistException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.game.handlers.PlayerDeathHandler;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KillDisconnectedPlayerTask implements Runnable{

	private static final Logger LOGGER = Logger.getLogger(KillDisconnectedPlayerTask.class.getCanonicalName());

	private final PlayerDeathHandler playerDeathHandler;
	private final UUID uuid;
	private int timeLeft;
	private final Location location;

	public KillDisconnectedPlayerTask(PlayerDeathHandler playerDeathHandler, UUID playerUuid, int maxDisconnectPlayersTime, Location location){
		this.playerDeathHandler = playerDeathHandler;
		uuid = playerUuid;
		timeLeft = maxDisconnectPlayersTime;
		this.location = location;
	}

	@Override
	public void run() {
		GameManager gm = GameManager.getGameManager();

		if(!gm.getGameState().equals(GameState.PLAYING)) {
			return;
		}

		Player player = Bukkit.getPlayer(uuid);

		if (player != null){
			return; // Player is back online
		}

		if(timeLeft <= 0){
			UhcPlayer uhcPlayer;
			PlayerManager pm = gm.getPlayerManager();
			try {
				uhcPlayer = pm.getUhcPlayer(uuid);
			} catch (UhcPlayerDoesNotExistException e) {
				LOGGER.log(Level.WARNING, "Not a UHC player", e);
				return;
			}

			// If using offline zombies kill that zombie.
			if (uhcPlayer.getOfflineZombieUuid() != null){
				Optional<Entity> zombie = Arrays.stream(location.getChunk().getEntities())
						.filter(e -> e.getUniqueId().equals(uhcPlayer.getOfflineZombieUuid()))
						.findFirst();

				// Remove zombie
				if (zombie.isPresent()) {
					playerDeathHandler.handleOfflinePlayerDeath(uhcPlayer, zombie.get().getLocation(), null);
					zombie.get().remove();
					uhcPlayer.setOfflineZombieUuid(null);
				}
				// No zombie found, kill player without removing zombie.
				else {
					playerDeathHandler.handleOfflinePlayerDeath(uhcPlayer, null, null);
				}
			}else{
				playerDeathHandler.handleOfflinePlayerDeath(uhcPlayer, null, null);
			}
		}else{
			timeLeft-=5;
			Bukkit.getScheduler().scheduleSyncDelayedTask(UhcCore.getPlugin(), this, 100);
		}
	}

}
