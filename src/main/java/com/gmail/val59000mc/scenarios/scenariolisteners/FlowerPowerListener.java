package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.exceptions.ParseException;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.*;

import io.papermc.lib.PaperLib;

import com.gmail.val59000mc.configuration.YamlFile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowerPowerListener extends ScenarioListener{

	private static final Logger LOGGER = Logger.getLogger(FlowerPowerListener.class.getCanonicalName());

	private List<JsonItemStack> flowerDrops;
	private int expPerFlower;

	@Override
	public void onEnable(){
		flowerDrops = new ArrayList<>();

		String source = PaperLib.getMinecraftVersion() < 13 ? "flowerpower-1.8.yml" : "flowerpower-1.13.yml";
		YamlFile cfg;

		try{
			cfg = FileUtils.saveResourceIfNotAvailable(UhcCore.getPlugin(), "flowerpower.yml", source);
		} catch (IOException | InvalidConfigurationException ex) {
			LOGGER.log(Level.WARNING, "Unable to load flowerpower.yml", ex);
			return;
		}

		expPerFlower = cfg.getInt("exp-per-flower", 2);

		for (String drop : cfg.getStringList("drops")){
			try {
				JsonItemStack flowerDrop = JsonItemUtils.getItemFromJson(drop);
				flowerDrops.add(flowerDrop);
			} catch (ParseException ex) {
				LOGGER.log(Level.WARNING, "Failed to parse FlowerPower item: " + drop, ex);
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if (e.isCancelled()) {
			return;
		}

		final Player player = e.getPlayer();
		final Block block = e.getBlock();

		if (getScenarioManager().getActiveBlockDropScenario(player, block) != Scenario.FLOWER_POWER) {
			return;
		}

		// For tall flowers start with the bottom block.
		final Block blockBelow = block.getRelative(BlockFace.DOWN);
		final Block blockToBreak = UniversalMaterial.isFlowerOrDeadBush(blockBelow) ? blockBelow : block;

		if (UniversalMaterial.isFlowerOrDeadBush(blockToBreak) && UniversalMaterial.isFlowerOrDeadBush(block)) {
			final Location breakLoc = blockToBreak.getLocation().add(.5,.5,.5);
			blockToBreak.setType(Material.AIR);
			UhcItems.spawnExtraXp(breakLoc, expPerFlower);

			final int random = RandomUtils.randomInteger(0, flowerDrops.size()-1);
			final ItemStack drop = flowerDrops.get(random).rollStack();
			breakLoc.getWorld().dropItem(breakLoc, drop);
		}
	}

}
