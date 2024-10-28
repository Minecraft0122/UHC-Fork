package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.OreType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodDiamondsListener extends ScenarioListener{

	@EventHandler (priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent e){
		if (e.isCancelled()) {
			return;
		}

		final Player player = e.getPlayer();

		// Vein miner already handles Blood Diamonds
		if (isEnabled(Scenario.VEIN_MINER) && ((VeinMinerListener) getScenarioManager().getScenarioListener(Scenario.VEIN_MINER)).isVeinMiningActive(player)) {
			return;
		}

		if (!OreType.DIAMOND.equals(e.getBlock().getType())){
			return;
		}

		UhcPlayer.damageIrreducible(player, 1);
	}

}
