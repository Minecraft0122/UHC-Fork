package com.gmail.val59000mc.players;

import com.gmail.val59000mc.exceptions.UhcTeamException;
import com.gmail.val59000mc.game.handlers.ScoreboardHandler;
import com.gmail.val59000mc.languages.Lang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TeamManager{

	// This limit is imposed by Minecraft 1.8 - 1.17.
	private static final int TEAM_NAME_MAX_LENGTH = 16;

	// Allow space-separated alphanumeric Unicode words (excluding the Connector Punctuation category).
	// https://unicode.org/reports/tr18/#Compatibility_Properties
	// https://unicode.org/reports/tr18/#General_Category_Property
	private static final Pattern TEAM_NAME_PATTERN = Pattern.compile("[\\p{Alnum}\\p{gc=M}\\p{IsJoin_Control}\\p{gc=Zs}]+", Pattern.UNICODE_CHARACTER_CLASS);

	private static final String[] TEAM_COLORS = new String[]{
		ChatColor.RED.toString(),
		ChatColor.BLUE.toString(),
		ChatColor.DARK_GREEN.toString(),
		ChatColor.DARK_AQUA.toString(),
		ChatColor.DARK_PURPLE.toString(),
		ChatColor.YELLOW.toString(),
		ChatColor.GOLD.toString(),
		ChatColor.GREEN.toString(),
		ChatColor.AQUA.toString(),
		ChatColor.LIGHT_PURPLE.toString()
	};

	private static final String[] TEAM_COLOR_VARIATIONS = new String[]{
		"",
		ChatColor.BOLD.toString(),
		ChatColor.ITALIC.toString(),
		ChatColor.UNDERLINE.toString(),
		ChatColor.BOLD.toString() + ChatColor.ITALIC.toString(),
		ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString(),
		ChatColor.ITALIC.toString() + ChatColor.UNDERLINE.toString(),
		ChatColor.ITALIC.toString() + ChatColor.UNDERLINE.toString() + ChatColor.BOLD.toString()
	};

	private final PlayerManager playerManager;
	private final ScoreboardHandler scoreboardHandler;
	private int lastTeamNumber;
	private List<String> prefixes;

	public TeamManager(PlayerManager playerManager, ScoreboardHandler scoreboardHandler){
		this.playerManager = playerManager;
		this.scoreboardHandler = scoreboardHandler;
		lastTeamNumber = 0;
		loadPrefixes();
	}

	public List<UhcTeam> getPlayingUhcTeams(){
		List<UhcTeam> teams = new ArrayList<>();
		for(UhcTeam team : getUhcTeams()){
			if (team.getPlayingMemberCount() != 0){
				teams.add(team);
			}
		}
		return teams;
	}

	public List<UhcTeam> getUhcTeams(){
		List<UhcTeam> teams = new ArrayList<>();
		for(UhcPlayer player : playerManager.getPlayersList()){

			UhcTeam team = player.getTeam();
			if(!teams.contains(team)) {
				teams.add(team);
			}
		}
		return teams;
	}

	public String sendInvite(Player inviter, String inviteeName) {
		UhcPlayer uhcInviter = playerManager.getUhcPlayer(inviter);
		return sendInvite(uhcInviter, inviteeName);
	}

	public String sendInvite(UhcPlayer inviter, String inviteeName) {
		if (!inviter.isTeamLeader()) {
			return Lang.TEAM_MESSAGE_NOT_LEADER;
		}

		Player bukkitInvitee = Bukkit.getPlayer(inviteeName);
		if (bukkitInvitee == null) {
			return Lang.TEAM_MESSAGE_PLAYER_NOT_ONLINE.replace("%player%", inviteeName);
		}

		UhcTeam team = inviter.getTeam();
		UhcPlayer uhcInvitee = playerManager.getUhcPlayer(bukkitInvitee);
		if (team.contains(uhcInvitee)) {
			return Lang.TEAM_MESSAGE_ALREADY_IN_TEAM.replace("%player%", uhcInvitee.getRealName());
		}
		if (uhcInvitee.getTeamInvites().contains(team)) {
			return Lang.TEAM_MESSAGE_INVITE_ALREADY_SENT.replace("%player%", uhcInvitee.getRealName());
		}

		// Validation passed, perform the invite
		uhcInvitee.inviteToTeam(team);
		return Lang.TEAM_MESSAGE_INVITE_SUCCESS.replace("%player%", uhcInvitee.getRealName());
	}

	public void replyToTeamInvite(UhcPlayer uhcPlayer, UhcTeam team, boolean accepted){
		uhcPlayer.getTeamInvites().remove(team);

		if (!accepted){
			uhcPlayer.sendMessage(Lang.TEAM_MESSAGE_DENY_REQUEST);
			return;
		}

		try{
			team.join(uhcPlayer);

			scoreboardHandler.updateTeamOnTab(team);
		}catch (UhcTeamException ex){
			uhcPlayer.sendMessage(ex.getMessage());
		}
	}

	public String renameTeam(UhcTeam team, String newName) {
		if (newName == null || newName.isEmpty()) {
			return Lang.TEAM_MESSAGE_NAME_EMPTY;
		} else if (newName.length() > TEAM_NAME_MAX_LENGTH) {
			return Lang.TEAM_MESSAGE_NAME_TOO_LONG;
		} else if (!TEAM_NAME_PATTERN.matcher(newName).matches()) {
			return Lang.TEAM_MESSAGE_NAME_ILLEGAL_CHARACTERS;
		}

		team.setTeamName(newName);
		return Lang.TEAM_MESSAGE_NAME_CHANGED;
	}

	@Nullable
	public UhcTeam getTeamByName(String name){
		for (UhcTeam team : getUhcTeams()){
			if (team.getTeamName().equals(name)){
				return team;
			}
		}

		return null;
	}

	public int getNewTeamNumber(){
		lastTeamNumber++;
		return lastTeamNumber;
	}

	private void loadPrefixes(){
		prefixes = new ArrayList<>();

		for (String colorVariation : TEAM_COLOR_VARIATIONS){
			for (String color : TEAM_COLORS){
				prefixes.add(color + colorVariation);
			}
		}
	}

	private List<String> getUsedPrefixes(){
		List<String> used = new ArrayList<>();
		for (UhcTeam team : getUhcTeams()){
			used.add(team.getColor());
		}
		return used;
	}

	public List<String> getFreePrefixes(){
		List<String> used = getUsedPrefixes();
		List<String> free = new ArrayList<>();
		for (String prefix : prefixes){
			if (!used.contains(prefix)){
				free.add(prefix);
			}
		}
		return free;
	}

	public String getTeamPrefix(){
		List<String> free = getFreePrefixes();

		if (free.isEmpty()){
			return ChatColor.DARK_GRAY.toString();
		}

		return free.get(0);
	}

	public String getTeamPrefix(String preferenceColor){
		for (String prefix : getFreePrefixes()){
			if (prefix.contains(preferenceColor)){
				return prefix;
			}
		}

		return null;
	}

}
