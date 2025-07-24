package com.gmail.val59000mc.maploader;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.configuration.VeinConfiguration;
import com.gmail.val59000mc.utils.RandomUtils;
import com.gmail.val59000mc.utils.UniversalMaterial;
import com.gmail.val59000mc.versionadapters.adapters.GetWorldMinHeightAdapter;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class VeinGeneratorPopulator extends BlockPopulator {

	private final Map<Material, VeinConfiguration> generateVeins;

	public VeinGeneratorPopulator(Map<Material, VeinConfiguration> generateVeins){
		this.generateVeins = generateVeins;
	}

	@Override
	public void populate(World world, Random random, Chunk source) {
		for(Entry<Material, VeinConfiguration> entry : generateVeins.entrySet()){
			VeinConfiguration veinCfg = entry.getValue();
			Material material = entry.getKey();

			int randNbrVeins = RandomUtils.randomInteger(veinCfg.getMinVeinsPerChunk(), veinCfg.getMaxVeinsPerChunk());

			for(int i=0 ; i<randNbrVeins ; i++){
				int randNbrBlocks = RandomUtils.randomInteger(veinCfg.getMinBlocksPerVein(), veinCfg.getMaxBlocksPerVein());
				if(randNbrBlocks > 0){
					int randX = RandomUtils.randomInteger(0, 15);
					int randY = RandomUtils.randomInteger(veinCfg.getMinY(),veinCfg.getMaxY());
					int randZ = RandomUtils.randomInteger(0, 15);
					Block randBlock = tryAdjustingToProperBlock(source.getBlock(randX, randY, randZ));
					if(randBlock != null){
						generateVein(material,randBlock,randNbrBlocks);
					}
				}
			}
		}
	}

	private Block tryAdjustingToProperBlock(Block randBlock) {
		if (UniversalMaterial.isOreReplaceableBlock(randBlock)) {
			return randBlock;
		}

		// Descend to go beneath the water in the sea
		if(randBlock.getType().equals(UniversalMaterial.STATIONARY_WATER.getType())){
			while(randBlock.getType().equals(UniversalMaterial.STATIONARY_WATER.getType()) && randBlock.getY() > 10){
				randBlock = randBlock.getRelative(0, -10, 0);
			}
			if (UniversalMaterial.isOreReplaceableBlock(randBlock)) {
				return randBlock;
			}
		}
		return null;
	}

	/**
	 * Generate a vein starting from a block
	 * @param material : the material of the vein
	 * @param startBlock : the block where to start the vein
	 * @param nbrBlocks : the number of blocks in the vein
	 */
	private void generateVein(Material material, Block startBlock, int nbrBlocks){
		List<Block> blocks = getAdjacentsBlocks(startBlock,nbrBlocks);
		for(Block block : blocks){
			// Don't send block updates to neighboring chunk, to avoid infinite recursion
			block.setType(material, false);
		}
	}

	/**
	 * Get a set of adjacent blocks starting from a block
	 * @param startBlock : the block where to start the search
	 * @param nbrBlocks : number of adjacent blocks
	 * @return
	 */
	private List<Block> getAdjacentsBlocks(Block startBlock, int nbrBlocks){
		int failedAttempts = 0;
		List<Block> adjacentBlocks = new ArrayList<>();
		adjacentBlocks.add(startBlock);
		while(adjacentBlocks.size() < nbrBlocks && failedAttempts < 25){
			// Get random block in the growing list of chosen blocks
			Block block = adjacentBlocks.get(RandomUtils.randomInteger(0, adjacentBlocks.size()-1));

			// RandomFace
			BlockFace face = RandomUtils.randomAdjacentFace();
			if (
				(face == BlockFace.DOWN && block.getY() <= UhcCore.getVersionAdapterLoader().getVersionAdapter(GetWorldMinHeightAdapter.class).getWorldMinHeight(block.getWorld()) + 1) ||
				(face == BlockFace.UP && block.getY() >= block.getWorld().getMaxHeight() - 1) ||
				// Make sure to stay within bounds of the source chunk to avoid infinite recursion
				// Using Math.floorMod instead of % (remainder) to handle negative coordinates correctly
				(face == BlockFace.NORTH && Math.floorMod(block.getZ(), 16) == 0) ||
				(face == BlockFace.EAST && Math.floorMod(block.getX(), 16) == 15) ||
				(face == BlockFace.SOUTH && Math.floorMod(block.getZ(), 16) == 15) ||
				(face == BlockFace.WEST && Math.floorMod(block.getX(), 16) == 0)
			) {
				failedAttempts++;
			} else {
				// Find random adjacent block to this block
				Block adjacent = block.getRelative(face);
				if (
					adjacentBlocks.contains(adjacent) ||
					!UniversalMaterial.isOreReplaceableBlock(adjacent)
				) {
					// We only want to find new discovered block inside stone/deepslate etc to avoid placing ores in mid-air in the caves.
					failedAttempts++;
				}else{
					adjacentBlocks.add(adjacent);
				}
			}


		}
		return adjacentBlocks;
	}
}
