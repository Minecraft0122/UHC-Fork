package com.gmail.val59000mc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.val59000mc.tasks.PreStartTask;

public class StartCommandExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		sender.sendMessage(ChatColor.GREEN + "[UhcCore] Force starting has toggled!");
		PreStartTask.toggleForce();
		return true;
	}

}
