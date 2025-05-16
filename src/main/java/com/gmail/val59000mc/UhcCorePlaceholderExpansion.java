package com.gmail.val59000mc;

import org.bukkit.entity.Player;

import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.UhcPlayer;

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
	public String onPlaceholderRequest(Player player, String params) {
		if (player == null) {
			return null;
		}

		final GameManager gm = GameManager.getGameManager();

		String placeholder = "%" + params + "%";
		UhcPlayer uhcPlayer = gm.getPlayerManager().getUhcPlayer(player);
		String translatedPlaceholder = gm.getScoreboardManager().translatePlaceholders(placeholder, uhcPlayer, player);
		if (!translatedPlaceholder.equals(placeholder)) {
			return translatedPlaceholder;
		} else {
			return null; // Requested placeholder does not exist
		}

	}
}
