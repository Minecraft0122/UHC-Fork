package com.gmail.val59000mc.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;

public class ReviveCommandExecutor implements CommandExecutor{

	private final GameManager gameManager;
	private final PlayerManager playerManager;

	public ReviveCommandExecutor(GameManager gameManager){
		this.gameManager = gameManager;
		this.playerManager = gameManager.getPlayerManager();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (args.length != 1 && args.length != 2){
			sender.sendMessage(ChatColor.RED + "Correct usage: '/revive <player>' or use '/revive <player> clear' to respawn the player without giving their items back.");
			return true;
		}

		if (gameManager.getGameState() != GameState.PLAYING && gameManager.getGameState() != GameState.DEATHMATCH){
			sender.sendMessage(ChatColor.RED + "You can only use this command while playing!");
			return true;
		}

		String name = args[0];
		boolean spawnWithItems = args.length != 2 || !args[1].equalsIgnoreCase("clear");

		OfflinePlayer player = findOnlineOrOfflinePlayer(name);
		if (player == null) {
			sender.sendMessage(ChatColor.RED + "Player not found!");
			return true;
		}

		boolean playerIsDead = playerManager.getPlayersList().stream()
			.filter(UhcPlayer::isDead)
			.anyMatch(p -> p.getUuid().equals(player.getUniqueId()));

		if (!playerIsDead) {
			sender.sendMessage(ChatColor.RED + "Player is not dead!");
			return true;
		}

		playerManager.revivePlayer(player.getUniqueId(), player.getName(), spawnWithItems);
		if (player.isOnline()) {
			sender.sendMessage(ChatColor.GREEN + name + " has been revived!");
		} else {
			sender.sendMessage(ChatColor.GREEN + name + " can now join the game!");
		}
		return true;
	}

	private OfflinePlayer findOnlineOrOfflinePlayer(String name) {
		Player onlinePlayer = Bukkit.getPlayer(name);
		if (onlinePlayer != null) {
			return onlinePlayer; // Found online player with matching name
		}

		// Note: We don't use Bukkit.getOfflinePlayers(), it can be very expensive
		// since it needs to load all player.dat files from disk.
		// Instead, we use our own in-memory player list (which also tracks
		// offline players, as long as they are a UHC player).
		for (UhcPlayer uhcPlayer : playerManager.getPlayersList()) {
			if (!uhcPlayer.getRealName().equals(name)) {
				continue; // Skip reading player.dat file
			}

			// Found likely match, load the player.dat file.
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uhcPlayer.getUuid());
			if (offlinePlayer.hasPlayedBefore()) {
				return offlinePlayer; // Found offline player with matching name
			}
		}

		return null; // No player found by that name
	}

}
