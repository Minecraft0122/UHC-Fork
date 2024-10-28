package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.scenarios.Option;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.scenarios.ScenarioManager;
import com.gmail.val59000mc.utils.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class VeinMinerListener extends ScenarioListener{

	private static final BlockFace[] BLOCK_FACES = new BlockFace[]{
			BlockFace.DOWN,
			BlockFace.UP,
			BlockFace.SOUTH,
			BlockFace.NORTH,
			BlockFace.EAST,
			BlockFace.WEST
	};

	@Option(key = "calculate-tool-damage")
	private boolean calculateToolDamage = true;

	@Option(key = "require-sneaking")
	private boolean requireSneaking = true;

	/**
	 * @see ScenarioManager#getActiveBlockDropScenario(Player, Block)
	 */
	public boolean isVeinMiningActive(Player player) {
		return !requireSneaking || player.isSneaking();
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if (e.isCancelled()) {
			return;
		}

		final Player player = e.getPlayer();
		final Block block = e.getBlock();

		if (getScenarioManager().getActiveBlockDropScenario(player, block) != Scenario.VEIN_MINER) {
			return;
		}

		ItemStack tool = player.getItemInHand();

		final Material blockType = block.getType();
		if (blockType == UniversalMaterial.GLOWING_REDSTONE_ORE.getType()){
			block.setType(Material.REDSTONE_ORE);
		}

		final Optional<OreType> oreType = OreType.valueOf(blockType);
		if (!oreType.isPresent() || !oreType.get().isCorrectTool(tool.getType())) {
			return;
		}

		// Find and "mine" all surrounding blocks
		Vein vein = new Vein(block);
		vein.process();

		int amount = vein.getSize() * getVeinMultiplier(blockType);
		ItemStack drops = oreType.get().getDrop().getStack(amount);
		Location loc = player.getLocation().getBlock().getLocation().add(.5,.5,.5);
		loc.getWorld().dropItem(loc, drops);

		int xp = oreType.get().getXpPerBlock() * vein.getSize();
		if (xp != 0) {
			UhcItems.spawnExtraXp(player.getLocation(), xp);
		}

		// Process blood diamonds.
		if (isEnabled(Scenario.BLOOD_DIAMONDS) && oreType.get() == OreType.DIAMOND) {
			UhcPlayer.damageIrreducible(player, vein.getSize());
		}

		if (calculateToolDamage) {
			UhcPlayer.damageMiningTool(player, vein.getSize());
		}
	}

	private int getVeinMultiplier(Material oreType) {
		int multiplier;
		if (getScenarioManager().isEnabled(Scenario.TRIPLE_ORES)) {
			multiplier = 3;
		} else if (getScenarioManager().isEnabled(Scenario.DOUBLE_ORES)) {
			multiplier = 2;
		} else {
			multiplier = 1;
		}
		if (OreType.isGold(oreType) && isEnabled(Scenario.DOUBLE_GOLD)) {
			multiplier *= 2;
		}
		return multiplier;
	}

	private static class Vein {
		private final Block startBlock;
		private final Material type;
		private int size;

		public Vein(Block startBlock) {
			this.startBlock = startBlock;
			this.type = startBlock.getType();
			size = 0;
		}

		public void process() {
			getVeinBlocks(startBlock, type, 2, 10);
		}

		public int getSize() {
			return size;
		}

		// Finds "connected" vein blocks, excluding the start block, and "mine" them
		private void getVeinBlocks(Block block, Material type, int i, int maxBlocks) {
			if (maxBlocks == 0) return;

			if (block.getType() == UniversalMaterial.GLOWING_REDSTONE_ORE.getType()) {
				block.setType(Material.REDSTONE_ORE);
			}

			if (block.getType() == type) {
				block.setType(Material.AIR);
				size++;
				i = 2;
			}else {
				i--;
			}

			// Max ores per vein is 20 to avoid server lag when mining sand / gravel.
			if (i > 0 && size < 20) {
				for (BlockFace face : BLOCK_FACES) {
					getVeinBlocks(block.getRelative(face), type, i, maxBlocks-1);
				}
			}
		}
	}

}
