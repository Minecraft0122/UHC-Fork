package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.events.PlayerStartsPlayingEvent;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.RandomUtils;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuperHeroesListener extends ScenarioListener{

	private static final Logger LOGGER = Logger.getLogger(SuperHeroesListener.class.getCanonicalName());

	@EventHandler
	public void onGameStart(PlayerStartsPlayingEvent e){
		addHeroesEffect(e.getUhcPlayer(), RandomUtils.randomInteger(0, 5));
	}

	private void addHeroesEffect(UhcPlayer uhcPlayer, int effect){

		Player player;

		try {
			player = uhcPlayer.getPlayer();
		} catch (UhcPlayerNotOnlineException ignored) {
			// No effect for offline player
			return;
		}

		switch (effect){
			case 0:
				forceApplyPotionEffect(player, new PotionEffect(PotionEffectType.SPEED, 999999,0));
				break;
			case 1:
				forceApplyPotionEffect(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999,0));
				break;
			case 2:
				forceApplyPotionEffect(player, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999,1));
				break;
			case 3:
				forceApplyPotionEffect(player, new PotionEffect(PotionEffectType.INVISIBILITY, 999999,0));
				break;
			case 4:
				forceApplyPotionEffect(player, new PotionEffect(PotionEffectType.JUMP, 999999,3));
				break;
			case 5:
				forceApplyPotionEffect(player, new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999, 4));
				try {
					uhcPlayer.healFully(); // Fill the newly added hearts
				} catch (UhcPlayerNotOnlineException ignored) {
					// Shouldn't happen
				}
				break;
			default:
				LOGGER.warning("No effect for: " + effect);
				break;
		}
	}

	// Solution for https://gitlab.com/uhccore/uhccore/-/issues/120
	private void forceApplyPotionEffect(Player player, PotionEffect effect) {
		player.removePotionEffect(effect.getType());
		player.addPotionEffect(effect);
	}

}
