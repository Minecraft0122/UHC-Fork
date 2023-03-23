package com.gmail.val59000mc.utils;

import io.papermc.lib.PaperLib;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class LocationUtils {

	private static final Logger LOGGER = Logger.getLogger(LocationUtils.class.getCanonicalName());

	/**
	 * Returns a copy of the specified location, with the yaw and pitch
	 * set to face the same direction as the specified entity.
	 *
	 * @param location the location
	 * @param entity the entity
	 * @return the resulting location
	 */
	public static Location withSameDirection(Location location, Entity entity) {
		final Location withSameDirection = location.clone();
		withSameDirection.setYaw(entity.getLocation().getYaw());
		withSameDirection.setPitch(entity.getLocation().getPitch());
		return withSameDirection;
	}

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
	 * Loads and generates a 5x5 area of chunks around the given chunk.
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
	 * @param chunk the chunk to fully populate
	 */
	public static void fullyPopulateChunk(Chunk chunk) {
		final World world = chunk.getWorld();
		for (int cx = chunk.getX() - 2; cx <= chunk.getX() + 2; cx++) {
			for (int cz = chunk.getZ() - 2; cz <= chunk.getZ() + 2; cz++) {
				world.getChunkAt(cx, cz);
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

	/**
	 * Returns a random spawn location for the player in the specified world.
	 *
	 * <p>
	 *     The algorithm tries to pick a "safe" spawn location, but may fail to
	 *     do so in certain circumstances.
	 * </p>
	 *
	 * @param world the world to spawn in
	 * @param maxDistance the maximum allowed distance from the world origin in the X or Z direction
	 * @return the random spawn location
	 */
	public static Location getRandomSpawnLocation(World world, int maxDistance) {
		final int maxAttempts = 500;
		final int[] failedAttempts = new int[2 * maxAttempts];
		final int maxY = 200; // Workaround to stop people from spawning on top of lobby

		// Try to find safe spawn location at random
		for (int i = 0; i < maxAttempts; i++) {
			final int x = ThreadLocalRandom.current().nextInt(-maxDistance, maxDistance);
			final int z = ThreadLocalRandom.current().nextInt(-maxDistance, maxDistance);
			final Block safeSpawn = findSafeSpawnAt(world, x, z);
			if (safeSpawn != null && safeSpawn.getY() < maxY) {
				return safeSpawn.getLocation().add(0.5, 1, 0.5);
			}

			// Save location of failed attempt for debugging purposes
			failedAttempts[2*i] = x;
			failedAttempts[2*i + 1] = z;
		}

		// Otherwise, log failure and just pick a completely random location
		LOGGER.warning("Unable to find a safe spawn location");
		LOGGER.warning("World seed: " + world.getSeed());
		LOGGER.warning("failedAttempts: " + Arrays.toString(failedAttempts));

		final int x = ThreadLocalRandom.current().nextInt(-maxDistance, maxDistance);
		final int z = ThreadLocalRandom.current().nextInt(-maxDistance, maxDistance);
		if (world.getEnvironment() == Environment.NETHER) {
			return new Location(world, x, 64, z).add(0.5, 0, 0.5);
		} else {
			return new Location(world, x, getSurfaceLevelAt(world, x, z), z).add(0.5, 1, 0.5);
		}
	}

	private static Block findSafeSpawnAt(World world, int x, int z) {
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
			fullyPopulateChunk(world.getChunkAt(x / 16, z / 16));
		}

		if (world.getEnvironment() == Environment.NETHER) {
			// Search for a spawn location in the range [48, 112) on the Y-axis.
			// This range was picked based on experimentation. We don't want players
			// to spawn too low, because they might get stuck on an island in the
			// lava seas. We also don't need to search near the roof, and we
			// certainly don't want anyone to spawn on top of the roof.
			for (int y = 48; y < 112; y++) {
				final Block candidate = world.getBlockAt(x, y, z);
				if (isSafeToSpawnOn(candidate)) {
					return candidate;
				}
			}
			return null;
		} else {
			final Block candidate = getSurfaceBlockAt(world, x, z);
			if (isSafeToSpawnOn(candidate)) {
				return candidate;
			} else {
				return null;
			}
		}
	}

	private static boolean isSafeToSpawnOn(Block block) {
		Material type = block.getType();
		return type.isSolid()
			&& type != UniversalMaterial.CACTUS.getType()
			&& type != UniversalMaterial.MAGMA_BLOCK.getType()
			&& type != UniversalMaterial.CAMPFIRE.getType()
			&& type != UniversalMaterial.SOUL_CAMPFIRE.getType()
			&& isSafeToSpawnInside(block.getRelative(0, 1, 0))
			&& isSafeToSpawnInside(block.getRelative(0, 2, 0));
	}

	private static boolean isSafeToSpawnInside(Block block) {
		Material type = block.getType();
		return !type.isSolid()
			&& !type.isOccluding()
			&& !block.isLiquid()
			&& type != UniversalMaterial.FIRE.getType()
			&& type != UniversalMaterial.SOUL_FIRE.getType()
			&& type != UniversalMaterial.POWDER_SNOW.getType()
			&& type != UniversalMaterial.NETHER_PORTAL.getType()
			&& type != UniversalMaterial.END_PORTAL.getType()
			&& type != UniversalMaterial.END_GATEWAY.getType();
	}

}
