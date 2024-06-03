package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.Option;
import com.gmail.val59000mc.scenarios.ScenarioListener;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class LuckyLeavesListener extends ScenarioListener{

	@Option(key = "golden-apple-drop-chance")
	private double appleChance = 0.005;

	@EventHandler
	public void onLeaveDecay(LeavesDecayEvent e){
		if (e.isCancelled()) {
			return;
		}

		if (ThreadLocalRandom.current().nextDouble() < appleChance) {
			e.getBlock().getWorld().dropItem(e.getBlock().getLocation().add(.5, 0, .5), new ItemStack(Material.GOLDEN_APPLE));
		}
	}

}
