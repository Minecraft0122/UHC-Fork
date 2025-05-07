package com.gmail.val59000mc.versionadapters.adapters;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import com.gmail.val59000mc.versionadapters.VersionAdapter;

public interface SetTeamColorAdapter extends VersionAdapter {

	/**
	 * Sets the scoreboard team color, if possible.
	 *
	 * @param team the scoreboard team
	 * @param color the team color to set
	 */
	void setTeamColor(Team team, ChatColor color);

}
