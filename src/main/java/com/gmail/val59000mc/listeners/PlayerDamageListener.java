package com.gmail.val59000mc.listeners;

import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;

public class PlayerDamageListener implements Listener {

	private final GameManager gameManager;
	private final PlayerManager playerManager;

	public PlayerDamageListener(GameManager gameManager) {
		this.gameManager = gameManager;
		this.playerManager = gameManager.getPlayerManager();
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent entityDamageEvent) {
		// Handle this case first, we don't want to send "PvP is not enabled" yet
		// etc. in the lobby, for example.
		if (isPlayerDamage(entityDamageEvent)) {
			Player damaged = (Player) entityDamageEvent.getEntity();
			UhcPlayer uhcDamaged = (UhcPlayer) playerManager.getUhcPlayer(damaged);

			handlePlayerDamaged(entityDamageEvent, uhcDamaged);
		}

		boolean offlineZombiesEnabled = gameManager.getConfig().get(MainConfig.SPAWN_OFFLINE_PLAYERS);

		if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) entityDamageEvent;

			if (isPlayerDamagePlayer(entityDamageByEntityEvent)) {
				Player attacker = (Player) entityDamageByEntityEvent.getDamager();
				Player attacked = (Player) entityDamageByEntityEvent.getEntity();
				UhcPlayer uhcAttacker = playerManager.getUhcPlayer(attacker);
				UhcPlayer uhcAttacked = playerManager.getUhcPlayer(attacked);

				handlePlayerAttackPlayer(entityDamageByEntityEvent, uhcAttacker, uhcAttacked);
			} else if (isPlayerProjectileDamagePlayer(entityDamageByEntityEvent)) {
				Projectile projectile = (Projectile) entityDamageByEntityEvent.getDamager();
				Player attacker = (Player) projectile.getShooter();
				Player attacked = (Player) entityDamageByEntityEvent.getEntity();
				UhcPlayer uhcAttacker = playerManager.getUhcPlayer(attacker);
				UhcPlayer uhcAttacked = playerManager.getUhcPlayer(attacked);

				handlePlayerAttackPlayer(entityDamageByEntityEvent, uhcAttacker, uhcAttacked);
			} else if (offlineZombiesEnabled && isPlayerDamageZombie(entityDamageByEntityEvent)) {
				Player attacker = (Player) entityDamageByEntityEvent.getDamager();
				Zombie attacked = (Zombie) entityDamageByEntityEvent.getEntity();
				UhcPlayer uhcAttacked = findOfflineZombieOwner(attacked);

				if (uhcAttacked != null) {
					UhcPlayer uhcAttacker = playerManager.getUhcPlayer(attacker);

					handlePlayerAttackPlayer(entityDamageByEntityEvent, uhcAttacker, uhcAttacked);
				}
			} else if (offlineZombiesEnabled && isPlayerProjectileDamageZombie(entityDamageByEntityEvent)) {
				Projectile projectile = (Projectile) entityDamageByEntityEvent.getDamager();
				Player attacker = (Player) projectile.getShooter();
				Zombie attacked = (Zombie) entityDamageByEntityEvent.getEntity();
				UhcPlayer uhcAttacked = findOfflineZombieOwner(attacked);

				if (uhcAttacked != null) {
					UhcPlayer uhcAttacker = playerManager.getUhcPlayer(attacker);

					handlePlayerAttackPlayer(entityDamageByEntityEvent, uhcAttacker, uhcAttacked);
				}
			} else if (isLightningDamagePlayer(entityDamageByEntityEvent)) {
				handleLightningStrikePlayer(entityDamageByEntityEvent);
			}
		}
	}

	private boolean isPlayerDamagePlayer(EntityDamageByEntityEvent e) {
		return !e.isCancelled() &&
			e.getDamager() instanceof Player &&
			e.getEntity() instanceof Player;
	}

	private boolean isPlayerProjectileDamagePlayer(EntityDamageByEntityEvent e) {
		return !e.isCancelled() &&
			e.getDamager() instanceof Projectile &&
			((Projectile) e.getDamager()).getShooter() instanceof Player &&
			e.getEntity() instanceof Player;
	}

	private boolean isPlayerDamageZombie(EntityDamageByEntityEvent e) {
		return !e.isCancelled() &&
			e.getDamager() instanceof Player &&
			e.getEntity() instanceof Zombie;
	}

	private boolean isPlayerProjectileDamageZombie(EntityDamageByEntityEvent e) {
		return !e.isCancelled() &&
			e.getDamager() instanceof Projectile &&
			((Projectile) e.getDamager()).getShooter() instanceof Player &&
			e.getEntity() instanceof Zombie;
	}

	private boolean isLightningDamagePlayer(EntityDamageByEntityEvent e) {
		return !e.isCancelled() &&
			e.getDamager() instanceof LightningStrike &&
			e.getEntity() instanceof Player;
	}

	private boolean isPlayerDamage(EntityDamageEvent e) {
		return !e.isCancelled() && e.getEntity() instanceof Player;
	}

	private UhcPlayer findOfflineZombieOwner(Zombie zombie) {
		return playerManager.getAllPlayingPlayers().stream()
			.filter(uhcPlayer -> zombie.getUniqueId().equals(uhcPlayer.getOfflineZombieUuid()))
			.findAny().orElse(null);
	}

	private void handlePlayerAttackPlayer(EntityDamageEvent e, UhcPlayer attacker, UhcPlayer attacked) {
		boolean friendlyFireEnabled = gameManager.getConfig().get(MainConfig.ENABLE_FRIENDLY_FIRE);

		if(!friendlyFireEnabled && attacker.isInTeamWith(attacked)) {
			attacker.sendMessage(Lang.PLAYERS_FF_OFF);
			e.setCancelled(true);
		} else if (!gameManager.getPvp()) {
			attacker.sendMessage(Lang.PLAYERS_PVP_OFF);
			e.setCancelled(true);
		}
	}

	private void handleLightningStrikePlayer(EntityDamageByEntityEvent e) {
		// We spawn lightning strikes at players when they die (as a cosmetic effect),
		// but we don't want it to damage nearby players, so we cancel the damage.
		e.setCancelled(true);
	}

	private void handlePlayerDamaged(EntityDamageEvent e, UhcPlayer damaged) {
		if (!damaged.isPlaying() || damaged.isFrozen()) {
			e.setCancelled(true);
		}
	}

}
