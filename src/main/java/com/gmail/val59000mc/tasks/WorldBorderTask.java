package com.gmail.val59000mc.tasks;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.languages.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldBorderTask implements Runnable{

	private long timeBeforeShrink;
	private final long timeToShrink;
	private final int endSideLength;

	public WorldBorderTask(long timeBeforeShrink, int endSideLength, long timeToShrink){
		this.timeBeforeShrink = timeBeforeShrink;
		this.endSideLength = endSideLength;
		this.timeToShrink = timeToShrink;
	}

	@Override
	public void run() {
		if(timeBeforeShrink <= 0){
			startMoving();
		}else{
			timeBeforeShrink--;
			Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), this, 20);
		}
	}

	private void startMoving(){
		GameManager gm = GameManager.getGameManager();
		MainConfig cfg = gm.getConfig();

		GameManager.getGameManager().broadcastInfoMessage(Lang.GAME_BORDER_START_SHRINKING);

		World overworld = GameManager.getGameManager().getMapLoader().getUhcWorld(World.Environment.NORMAL);
		WorldBorder overworldBorder = overworld.getWorldBorder();
		overworldBorder.setSize(endSideLength, timeToShrink);

		World nether = GameManager.getGameManager().getMapLoader().getUhcWorld(World.Environment.NETHER);
		if (nether != null) {
			WorldBorder netherBorder = nether.getWorldBorder();
			netherBorder.setSize(endSideLength / cfg.get(MainConfig.NETHER_SCALE), timeToShrink);
		}
	}

}
