package com.gmail.val59000mc.utils;

import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.maploader.MapLoader;
import com.gmail.val59000mc.configuration.MainConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.player.PlayerPortalEvent;

public class VersionUtils_1_14 extends VersionUtils_1_13{

	@Override
	public void handleNetherPortalEvent(PlayerPortalEvent event){
		GameManager gm = GameManager.getGameManager();
		MainConfig cfg = gm.getConfig();

		Location loc = event.getFrom();
		MapLoader mapLoader = GameManager.getGameManager().getMapLoader();
		double netherScale = cfg.get(MainConfig.NETHER_SCALE);

		if (event.getFrom().getWorld().getEnvironment() == World.Environment.NETHER){
			loc.setWorld(mapLoader.getUhcWorld(World.Environment.NORMAL));
			loc.setX(loc.getX() * netherScale);
			loc.setZ(loc.getZ() * netherScale);
			event.setTo(loc);
		}else{
			loc.setWorld(mapLoader.getUhcWorld(World.Environment.NETHER));
			loc.setX(loc.getX() / netherScale);
			loc.setZ(loc.getZ() / netherScale);
			event.setTo(loc);
		}
	}

	@Override
	public boolean isAir(Material material) {
		return material.isAir();
	}

}
