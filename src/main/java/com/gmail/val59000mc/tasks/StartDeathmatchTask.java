package com.gmail.val59000mc.tasks;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.utils.UniversalSound;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class StartDeathmatchTask implements Runnable{

	private final GameManager gameManager;
	private int timeBeforePVP;
	private final boolean shrinkBorder;

	public StartDeathmatchTask(GameManager gameManager, boolean shrinkBorder){
		this.gameManager = gameManager;
		this.timeBeforePVP = gameManager.getConfig().get(MainConfig.DEATHMATCH_PVP_DELAY);
		this.shrinkBorder = shrinkBorder;
	}

	@Override
	public void run() {
		if (timeBeforePVP == 0) {
			gameManager.setPvp(true);
			gameManager.broadcastInfoMessage(Lang.PVP_ENABLED);
			gameManager.getPlayerManager().playSoundToAll(UniversalSound.WITHER_SPAWN.getSound());
			gameManager.getPlayerManager().setLastDeathTime();

			for (UhcPlayer uhcPlayer : gameManager.getPlayerManager().getPlayersList()){
				uhcPlayer.releasePlayer();
			}

			// If center deathmatch move border.
			if (shrinkBorder){
				final int borderEndApothem = gameManager.getConfig().get(MainConfig.DEATHMATCH_END_SIZE);
				final int borderEndSideLength = 2 * borderEndApothem;
				final int borderTimeToShrink = gameManager.getConfig().get(MainConfig.DEATHMATCH_TIME_TO_SHRINK);
				gameManager.getMapLoader().getUhcWorld(World.Environment.NORMAL).getWorldBorder().setSize(borderEndSideLength, borderTimeToShrink);
				gameManager.getMapLoader().getUhcWorld(World.Environment.NORMAL).getWorldBorder().setDamageBuffer(1);
			}
		} else if (timeBeforePVP <= 5 || (timeBeforePVP % 5 == 0)) {
			gameManager.broadcastInfoMessage(Lang.PVP_START_IN+" "+timeBeforePVP+"s");
			gameManager.getPlayerManager().playSoundToAll(UniversalSound.CLICK.getSound());
		}

		timeBeforePVP--;

		if (timeBeforePVP >= 0) {
			Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), this, 20);
		}
	}

}
