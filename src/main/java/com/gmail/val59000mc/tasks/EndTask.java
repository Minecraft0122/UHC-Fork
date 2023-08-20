package com.gmail.val59000mc.tasks;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.languages.Lang;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

public class EndTask implements Runnable{

	private static final Logger LOGGER = Logger.getLogger(EndTask.class.getCanonicalName());

	private static final EndTask instance;

	private int timeBeforeEnd;
	private boolean run;

	private EndTask(){
		timeBeforeEnd = 61;
		run = false;
	}

	static{
		instance = new EndTask();
	}

	@Override
	public void run() {
		if (!run){
			return; // Stop task
		}

		GameManager gm = GameManager.getGameManager();

		if(timeBeforeEnd <= 0){
			gm.endGame();
		}else{
			if(timeBeforeEnd%10 == 0 || timeBeforeEnd <= 5){
				LOGGER.info(Lang.PLAYERS_ALL_HAVE_LEFT+" "+timeBeforeEnd);
				gm.broadcastInfoMessage(Lang.PLAYERS_ALL_HAVE_LEFT+" "+timeBeforeEnd);
			}
			timeBeforeEnd--;
			Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), EndTask.this,20);
		}
	}

	public static void start(){
		if(instance.run){
			return; // Already running
		}

		instance.run = true;
		instance.timeBeforeEnd = 61;
		Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), instance, 20);
	}

	public static void stop(){
		if(instance.run){
			instance.run = false;
			GameManager.getGameManager().broadcastInfoMessage(Lang.GAME_END_STOPPED);
			LOGGER.info(Lang.GAME_END_STOPPED);
		}
	}

}
