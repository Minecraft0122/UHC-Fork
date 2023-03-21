package com.gmail.val59000mc.utils;

import io.papermc.lib.PaperLib;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class LocationUtils {

	/**
	 * Gets the Y-coordinate of the surface block at the given coordinates in a world.
	 *
	 * <p>
	 *     The exact implementation and behavior depends on the Minecraft
	 *     version, but some form of heightmap is used. The surface block will
	 *     generally be either a solid block, or a fluid, such as water or lava.
	 * </p>
	 *
	 * @param world the world for the coordinates
	 * @param x the X-coordinate
	 * @param z the Z-coordinate
	 * @return the Y-coordinate of the surface block at the X and Z coordinates
	 */
	public static int getSurfaceLevelAt(World world, int x, int z) {
		// Spigot API behavior changed in 1.14, see:
		// https://hub.spigotmc.org/jira/browse/SPIGOT-2595
		// https://hub.spigotmc.org/jira/browse/SPIGOT-5523

		if (PaperLib.isVersion(14)) {
			// MOTION_BLOCKING heightmap, 1.14+
			return world.getHighestBlockYAt(x, z);
		} else {
			// Legacy light heightmap, 1.8 - 1.12
			// LIGHT_BLOCKING heightmap, 1.13
			return world.getHighestBlockYAt(x, z) - 1;
		}
	}

	/**
	 * Gets the Y-coordinate of the surface block at the given location.
	 *
	 * <p>
	 *     The exact implementation and behavior depends on the Minecraft
	 *     version, but some form of heightmap is used. The surface block will
	 *     generally be either a solid block, or a fluid, such as water or lava.
	 * </p>
	 *
	 * @param location the location to find the surface for
	 * @return the Y-coordinate of the surface block at the X and Z coordinates
	 */
	public static int getSurfaceLevelAt(Location location) {
		return getSurfaceLevelAt(location.getWorld(), location.getBlockX(), location.getBlockZ());
	}

	/**
	 * Gets the surface block at the given coordinates in a world.
	 *
	 * <p>
	 *     The exact implementation and behavior depends on the Minecraft
	 *     version, but some form of heightmap is used. The returned block will
	 *     generally be either a solid block, or a fluid, such as water or lava.
	 * </p>
	 *
	 * @param world the world for the coordinates
	 * @param x the X-coordinate
	 * @param z the Z-coordinate
	 * @return the surface block at the X and Z coordinates
	 */
	public static Block getSurfaceBlockAt(World world, int x, int z) {
		return world.getBlockAt(x, getSurfaceLevelAt(world, x, z), z);
	}

	/**
	 * Gets the surface block at the X and Z coordinates of the given location.
	 *
	 * <p>
	 *     The exact implementation and behavior depends on the Minecraft
	 *     version, but some form of heightmap is used. The returned block will
	 *     generally be either a solid block, or a fluid, such as water or lava.
	 * </p>
	 *
	 * @param location the location to find the surface for
	 * @return the surface block at the X and Z coordinates of the location
	 */
	public static Block getSurfaceBlockAt(Location location) {
		return getSurfaceBlockAt(location.getWorld(), location.getBlockX(), location.getBlockZ());
	}

	/**
	 * Loads and generates a 5x5 area of chunks around the given location.
	 *
	 * <p>
	 *     This has to be done on Minecraft 1.8 - 1.12 in order to fully populate
	 *     the chunk at the given location. The center chunk will only be
	 *     fully populated (with trees etc.) once its neighboring chunks in the
	 *     3x3 area have run their populators (since populators may place blocks
	 *     in neighboring chunks), which will only happen once the neighbors'
	 *     neighbors in the 5x5 area have been loaded/generated.
	 * </p>
	 *
	 * @param location the location for which to fully populate the chunk
	 */
	public static void fullyPopulateChunk(Location location) {
		final Chunk chunk = location.getChunk();
		for (int cx = chunk.getX() - 2; cx <= chunk.getX() + 2; cx++) {
			for (int cz = chunk.getZ() - 2; cz <= chunk.getZ() + 2; cz++) {
				location.getWorld().getChunkAt(cx, cz);
			}
		}
	}

	public static boolean isWithinBorder(Location loc){
		double border = loc.getWorld().getWorldBorder().getSize()/2;

		int x = loc.getBlockX();
		int z = loc.getBlockZ();

		if (x < 0) x = -x;
		if (z < 0) z = -z;

		return x < border && z < border;
	}

	/***
	 * This method will try found a safe location.
	 * @param world The world you want to find a location in.
	 * @param maxDistance Max distance from 0 0 you want the location to be.
	 * @return Returns save ground location. (When no location can be found a random location in the sky will be returned.)
	 */
	public static Location findRandomSafeLocation(World world, double maxDistance) {
		// 35 is the range findSafeLocationAround() will look for a spawn block
		maxDistance-=10;
		Location randomLoc;
		Location location = null;

		int i = 0;
		while (location == null){
			i++;
			randomLoc = RandomUtils.newRandomLocation(world, maxDistance);
			location = findSafeLocationAround(randomLoc, 10);
			if (i > 20){
				return randomLoc;
			}
		}

		return location;
	}

	/***
	 * Finds a ground block that is not water or lava 35 blocks around the given location.
	 * @param loc The location a ground block should be searched around.
	 * @param searchRadius The radius used to find a safe location.
	 * @return Returns ground location. Can be null when no safe ground location can be found!
	 */
	@Nullable
	private static Location findSafeLocationAround(Location loc, int searchRadius) {
		boolean nether = loc.getWorld().getEnvironment() == World.Environment.NETHER;
		Material material;
		Location betterLocation;

		for(int i = -searchRadius ; i <= searchRadius ; i +=3){
			for(int j = -searchRadius ; j <= searchRadius ; j+=3){
				betterLocation = getGroundLocation(loc.clone().add(new Vector(i,0,j)), nether);

				// Check if location is on the nether roof.
				if (nether && betterLocation.getBlockY() > 120){
					continue;
				}

				// Check if the block below is lava / water
				material = betterLocation.clone().add(0, -1, 0).getBlock().getType();
				if(material.equals(UniversalMaterial.STATIONARY_LAVA.getType()) || material.equals(UniversalMaterial.STATIONARY_WATER.getType())){
					continue;
				}

				// Stop players from spawning on top of the lobby.
				if (betterLocation.getBlockY() > 160) {
					continue;
				}

				return betterLocation;
			}
		}

		return null;
	}

	/**
	 * Returns location of ground.
	 * @param loc Location to look for ground.
	 * @param allowCaves When set to true, the first location on the y axis is returned. This will include caves.
	 * @return Ground location.
	 */
	private static Location getGroundLocation(Location loc, boolean allowCaves){
		loc.setY(0);

		// On Minecraft 1.8 - 1.12, World#getHighestBlockYAt and
		// Location#getBlock does not wait for the chunk to become fully
		// populated, which means that the returned surface block might
		// cause the player to spawn inside a tree or a similar feature
		// generated by a populator.
		// This might in turn cause the player to fall down through a few
		// blocks (on these older versions), most likely due to some kind of
		// bug in the vanilla movement/collision handling logic.
		// Therefore, it is VERY important to fully populate the target
		// chunk on these Minecraft versions, in order to find a safe
		// surface position ON TOP of any eventual tree.
		// See also: https://gitlab.com/uhccore/uhccore/-/issues/56
		if (!PaperLib.isVersion(13)) {
			fullyPopulateChunk(loc);
		}

		if (allowCaves){
			while (!VersionUtils.getVersionUtils().isAir(loc.getBlock().getType())){
				loc = loc.add(0, 1, 0);
			}
		}else {
			loc = getSurfaceBlockAt(loc).getLocation().add(0, 1, 0);
		}

		loc = loc.add(.5, 0, .5);
		return loc;
	}

}
