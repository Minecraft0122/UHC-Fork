package com.gmail.val59000mc.scenarios.scenariolisteners;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.util.Vector;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.scenarios.Option;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.UniversalMaterial;
import com.gmail.val59000mc.utils.UniversalSound;
import com.gmail.val59000mc.utils.VersionUtils;

import io.papermc.lib.PaperLib;

public class FastLeavesDecayListener extends ScenarioListener{

	// Leaf decay uses 6-connectivity
	private final static BlockFace[] NEIGHBOURS = new BlockFace[] {
		BlockFace.UP,
		BlockFace.DOWN,
		BlockFace.NORTH,
		BlockFace.EAST,
		BlockFace.SOUTH,
		BlockFace.WEST
	};

	@Option(key = "time-decay")
	private int timeDecay = 5;

	// Low priority to override custom block drop scenarios
	@EventHandler(priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}

		final Block block = e.getBlock();
		if (UniversalMaterial.isLog(block.getType()) || UniversalMaterial.isLeaves(block.getType())) {
			// Delaying as right now the block may still be a log and therefore leaves won't decay
			scheduleDecayNeighbors(block);
		}
	}

	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e) {
		if (e.isCancelled()) {
			return;
		}

		scheduleDecayNeighbors(e.getBlock());
	}

	private void scheduleDecayNeighbors(Block block) {
		Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> decayNeighbors(block), timeDecay);
	}

	private void decayNeighbors(Block block) {
		// See https://minecraft.wiki/w/History_of_leaf_decay
		final int decayRange = PaperLib.isVersion(13) ? 6 : 4;

		for (BlockFace face : NEIGHBOURS) {
			final Block relative = block.getRelative(face);

			final boolean canDecay =
				UniversalMaterial.isLeaves(relative.getType()) &&
				!VersionUtils.getVersionUtils().leavesIsPersistent(relative) &&
				!isConnectedToLog(relative, decayRange);
			if (!canDecay) {
				continue;
			}

			// Must be called in order to notify LuckyLeavesListener and others
			LeavesDecayEvent event = new LeavesDecayEvent(relative);
			Bukkit.getPluginManager().callEvent(event);
			if (!event.isCancelled()) {
				relative.breakNaturally();
				relative.getWorld().playSound(relative.getLocation(), UniversalSound.BLOCK_GRASS_BREAK.getSound(), 1, 1);
			}
		}
	}

	private boolean isConnectedToLog(Block leaf, int maxDistance) {
		// Search for any leaf-connected log block within maxDistance blocks of this leaf block.
		// We need to avoid re-visiting the same block twice in different paths,
		// since this leads to performance issues, especially on Minecraft 1.8,
		// where block access seems to be quite slow. BFS works well in this case.
		final Set<Vector> visited = new HashSet<>();
		final Queue<Block> queue = new ArrayDeque<>();
		queue.add(leaf);
		visited.add(new Vector(leaf.getX(), leaf.getY(), leaf.getZ()));

		int bfsIterationRemainder = queue.size();
		while (!queue.isEmpty() && maxDistance > 0) {
			final Block current = queue.remove();

			for (BlockFace dir : NEIGHBOURS) {
				final Block neighbor = current.getRelative(dir);

				if (UniversalMaterial.isLog(neighbor.getType())) {
					return true; // Found a connected log
				}

				// If maxDistance == 1, we are only looking for immediately neighboring logs,
				// so let's avoid filling up the queue with "dead end" leaves in that case.
				if (maxDistance > 1) {
					final Vector neighborPos = new Vector(neighbor.getX(), neighbor.getY(), neighbor.getZ());
					// If we find non-visited neighboring leaves, add them to the queue.
					if (UniversalMaterial.isLeaves(neighbor.getType()) && visited.add(neighborPos)) {
						queue.add(neighbor);
					}
				}
			}

			// Count the number of remaining blocks in this BFS iteration.
			// When it reaches 0, it's time for the next iteration, with a lower maxDistance.
			if (--bfsIterationRemainder == 0) {
				bfsIterationRemainder = queue.size();
				maxDistance--;
			}
		}
		return false; // No connected logs found within maxDistance
	}

}
