package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.utils.LocationUtils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCommandExecutor implements CommandExecutor{

	private final PlayerManager playerManager;

	public TopCommandExecutor(PlayerManager playerManager){
		this.playerManager = playerManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;
		UhcPlayer uhcPlayer = playerManager.getUhcPlayer(player);

		if (uhcPlayer.getState() != PlayerState.PLAYING){
			player.sendMessage(Lang.COMMAND_TOP_ERROR_PLAYING);
			return true;
		}

		if (player.getWorld().getEnvironment() == World.Environment.NETHER){
			player.sendMessage(Lang.COMMAND_TOP_ERROR_NETHER);
			return true;
		}

		final Location surface = LocationUtils.getSurfaceBlockAt(player.getLocation()).getLocation().add(0.5, 1, 0.5);
		player.teleport(LocationUtils.withSameDirection(surface, player));
		player.sendMessage(Lang.COMMAND_TOP_TELEPORT);
		return true;
	}

}
