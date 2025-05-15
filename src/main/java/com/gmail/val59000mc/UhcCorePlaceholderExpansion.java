package com.gmail.val59000mc;

import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.scoreboard.ScoreboardManager;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class UhcCorePlaceholderExpansion extends PlaceholderExpansion {
	private final UhcCore plugin;

	public UhcCorePlaceholderExpansion(UhcCore plugin){
		this.plugin = plugin;
	}

	@Override
	public String getAuthor() {
		return plugin.getDescription().getAuthors().toString();
	}

	@Override
	public String getIdentifier() {
		return "uhccore";
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String onRequest(OfflinePlayer player, String params) {

		GameManager gm = GameManager.getGameManager();

		PlayerManager manager = gm.getPlayerManager();
		Player bukkitPlayer = player.getPlayer();
		UhcPlayer uhcPlayer = manager.getUhcPlayer(bukkitPlayer);
		ScoreboardManager scoreboardManager = gm.getScoreboardManager();

		String placeholder = "%" + params + "%";

		String translatedPlaceholder = scoreboardManager.translatePlaceholders(placeholder, uhcPlayer, bukkitPlayer);
		if (!translatedPlaceholder.equals(placeholder)) {
			return translatedPlaceholder;
		} else {
			return null; // Requested placeholder does not exist
		}

	}
}
