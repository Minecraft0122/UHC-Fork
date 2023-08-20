package com.gmail.val59000mc.tasks;

import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;

public class FinalHealTask implements Runnable{

	private final GameManager gameManager;
	private final PlayerManager playerManager;

	public FinalHealTask(GameManager gameManager, PlayerManager playerManager){
		this.gameManager = gameManager;
		this.playerManager = playerManager;
	}

	@Override
	public void run() {
		for (UhcPlayer player : playerManager.getOnlinePlayingPlayers()){
			try {
				player.healFully();
			} catch (UhcPlayerNotOnlineException ignored) {
				// Should not happen
			}
		}

		gameManager.broadcastInfoMessage(Lang.GAME_FINAL_HEAL);
	}

}
