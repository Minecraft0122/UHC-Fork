package com.gmail.val59000mc.utils;

import io.papermc.lib.PaperLib;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public enum UniversalMaterial {
	WHITE_WOOL("WOOL", 0, DataValueMask.EXCLUDE_NONE, "WHITE_WOOL"),
	ORANGE_WOOL("WOOL", 1, DataValueMask.EXCLUDE_NONE, "ORANGE_WOOL"),
	MAGENTA_WOOL("WOOL", 2, DataValueMask.EXCLUDE_NONE, "MAGENTA_WOOL"),
	LIGHT_BLUE_WOOL("WOOL", 3, DataValueMask.EXCLUDE_NONE, "LIGHT_BLUE_WOOL"),
	YELLOW_WOOL("WOOL", 4, DataValueMask.EXCLUDE_NONE, "YELLOW_WOOL"),
	LIME_WOOL("WOOL", 5, DataValueMask.EXCLUDE_NONE, "LIME_WOOL"),
	PINK_WOOL("WOOL", 6, DataValueMask.EXCLUDE_NONE, "PINK_WOOL"),
	GRAY_WOOL("WOOL", 7, DataValueMask.EXCLUDE_NONE, "GRAY_WOOL"),
	LIGHT_GRAY_WOOL("WOOL", 8, DataValueMask.EXCLUDE_NONE, "LIGHT_GRAY_WOOL"),
	CYAN_WOOL("WOOL", 9, DataValueMask.EXCLUDE_NONE, "CYAN_WOOL"),
	PURPLE_WOOL("WOOL", 10, DataValueMask.EXCLUDE_NONE, "PURPLE_WOOL"),
	BLUE_WOOL("WOOL", 11, DataValueMask.EXCLUDE_NONE, "BLUE_WOOL"),
	BROWN_WOOL("WOOL", 12, DataValueMask.EXCLUDE_NONE, "BROWN_WOOL"),
	GREEN_WOOL("WOOL", 13, DataValueMask.EXCLUDE_NONE, "GREEN_WOOL"),
	RED_WOOL("WOOL", 14, DataValueMask.EXCLUDE_NONE, "RED_WOOL"),
	BLACK_WOOL("WOOL", 15, DataValueMask.EXCLUDE_NONE, "BLACK_WOOL"),

	STATIONARY_WATER("STATIONARY_WATER", "WATER"),
	STATIONARY_LAVA("STATIONARY_LAVA", "LAVA"),
	SUGAR_CANE_BLOCK("SUGAR_CANE_BLOCK", "SUGAR_CANE"),
	CAVE_AIR("CAVE_AIR", "CAVE_AIR"),
	GRASS_BLOCK("GRASS", "GRASS_BLOCK"),

	/**
	 * The 1.8-1.12 skull block stores skull type as a data value in the tile entity rather than the block, so it is
	 * currently not handled here. Either way, the skull type will be set to "player" once you set the skull owner.
	 * But this means that this material cannot be used to check whether a block is a player head, for example.
	 *
	 * @deprecated Warning: you should only use this material to SET a player head, and only if you set the owner.
	 */
	@Deprecated
	PLAYER_HEAD_BLOCK("SKULL", "PLAYER_HEAD"),
	SKELETON_SKULL_ITEM("SKULL_ITEM", 0, DataValueMask.EXCLUDE_NONE, "SKELETON_SKULL"),
	WITHER_SKELETON_SKULL_ITEM("SKULL_ITEM", 1, DataValueMask.EXCLUDE_NONE, "WITHER_SKELETON_SKULL"),
	ZOMBIE_HEAD_ITEM("SKULL_ITEM", 2, DataValueMask.EXCLUDE_NONE, "ZOMBIE_HEAD"),
	PLAYER_HEAD_ITEM("SKULL_ITEM", 3, DataValueMask.EXCLUDE_NONE, "PLAYER_HEAD"),
	CREEPER_HEAD_ITEM("SKULL_ITEM", 4, DataValueMask.EXCLUDE_NONE, "CREEPER_HEAD"),
	DRAGON_HEAD_ITEM("SKULL_ITEM", 5, DataValueMask.EXCLUDE_NONE, "DRAGON_HEAD"),

	OAK_FENCE("FENCE", "OAK_FENCE"),

	PUFFERFISH("RAW_FISH", 3, DataValueMask.EXCLUDE_NONE, "PUFFERFISH"),

	WHITE_STAINED_GLASS_PANE("STAINED_GLASS_PANE", 0, DataValueMask.EXCLUDE_NONE, "WHITE_STAINED_GLASS_PANE"),
	BLACK_STAINED_GLASS_PANE("STAINED_GLASS_PANE", 15, DataValueMask.EXCLUDE_NONE, "BLACK_STAINED_GLASS_PANE"),

	IRON_INGOT,
	LAVA_BUCKET,
	BOW,
	FISHING_ROD,
	SHIELD,
	DIAMOND,
	DIAMOND_ORE,
	EMERALD,
	SADDLE,
	TRAPPED_CHEST,
	FEATHER,
	FURNACE,
	REDSTONE,
	REDSTONE_ORE,
	CHEST,
	QUARTZ,
	BOOK,
	GOLD_INGOT,
	GOLD_ORE,
	ARROW,
	COPPER_INGOT,
	NETHERITE_SCRAP,
	COAL,
	COAL_ORE,
	COPPER_ORE,
	NETHER_GOLD_ORE,
	ANCIENT_DEBRIS,
	DEEPSLATE_COAL_ORE,
	DEEPSLATE_COPPER_ORE,
	DEEPSLATE_REDSTONE_ORE,
	DEEPSLATE_LAPIS_ORE,
	DEEPSLATE_IRON_ORE,
	DEEPSLATE_GOLD_ORE,
	DEEPSLATE_EMERALD_ORE,
	DEEPSLATE_DIAMOND_ORE,
	GLOWING_REDSTONE_ORE("GLOWING_REDSTONE_ORE", "REDSTONE_ORE"),
	NETHER_QUARTZ_ORE("QUARTZ_ORE", "NETHER_QUARTZ_ORE"),
	LAPIS_LAZULI("INK_SACK", 4, DataValueMask.EXCLUDE_NONE, "LAPIS_LAZULI"),
	RED_DYE("INK_SACK", 1, DataValueMask.EXCLUDE_NONE, "RED_DYE"),
	DRAGON_EGG,
	END_PORTAL_FRAME("ENDER_PORTAL_FRAME", "END_PORTAL_FRAME"),
	END_PORTAL("ENDER_PORTAL", "END_PORTAL"),
	END_GATEWAY,
	NETHER_PORTAL("PORTAL", "NETHER_PORTAL"),
	NETHER_STAR,
	NAME_TAG,
	ENCHANTING_TABLE("ENCHANTMENT_TABLE", "ENCHANTING_TABLE"),
	WOLF_SPAWN_EGG("MONSTER_EGG", 95, DataValueMask.EXCLUDE_NONE, "WOLF_SPAWN_EGG"),
	CLOCK("WATCH", "CLOCK"),
	EGG,
	ENCHANTED_BOOK,
	PAPER,
	BARRIER,
	AIR,
	COMPASS,
	NETHER_BRICK,
	/**
	 * Note: On 1.8-1.12, even though there is a corresponding block with the same name, its data value is used for
	 * orientation rather than color, and the color is instead stored in the tile entity.
	 * Also note that on 1.8-1.12, the color may be stored in the tile entity NBT instead of the data value,
	 * so this material cannot reliably be used to tell if an item is a red banner.
	 *
	 * @deprecated Warning: Cannot reliably be used to tell if an item is a red banner.
	 */
	@Deprecated
	RED_BANNER_ITEM("BANNER", 1, DataValueMask.EXCLUDE_NONE, "RED_BANNER"),
	ELYTRA,
	CRAFTING_TABLE("WORKBENCH", "CRAFTING_TABLE"),
	EXPERIENCE_BOTTLE("EXP_BOTTLE", "EXPERIENCE_BOTTLE"),
	IRON_DOOR,
	LIGHT_GRAY_STAINED_GLASS_PANE("STAINED_GLASS_PANE", 8, DataValueMask.EXCLUDE_NONE, "LIGHT_GRAY_STAINED_GLASS_PANE"),
	ANVIL,
	MAGMA_BLOCK("MAGMA", "MAGMA_BLOCK"),
	CAMPFIRE,
	SOUL_CAMPFIRE,
	CACTUS,
	FIRE,
	SOUL_FIRE,
	POWDER_SNOW,

	// Flowers
	POPPY("RED_ROSE", 0, DataValueMask.EXCLUDE_NONE, "POPPY"),
	BLUE_ORCHID("RED_ROSE", 1, DataValueMask.EXCLUDE_NONE, "BLUE_ORCHID"),
	ALLIUM("RED_ROSE", 2, DataValueMask.EXCLUDE_NONE, "ALLIUM"),
	AZURE_BLUET("RED_ROSE", 3, DataValueMask.EXCLUDE_NONE, "AZURE_BLUET"),
	RED_TULIP("RED_ROSE", 4, DataValueMask.EXCLUDE_NONE, "RED_TULIP"),
	ORANGE_TULIP("RED_ROSE", 5, DataValueMask.EXCLUDE_NONE, "ORANGE_TULIP"),
	WHITE_TULIP("RED_ROSE", 6, DataValueMask.EXCLUDE_NONE, "WHITE_TULIP"),
	PINK_TULIP("RED_ROSE", 7, DataValueMask.EXCLUDE_NONE, "PINK_TULIP"),
	OXEYE_DAISY("RED_ROSE", 8, DataValueMask.EXCLUDE_NONE, "OXEYE_DAISY"),
	DANDELION("YELLOW_FLOWER", "DANDELION"),
	CORNFLOWER,
	LILY_OF_THE_VALLEY,
	WITHER_ROSE,
	TORCHFLOWER,
	PITCHER_PLANT,
	OPEN_EYEBLOSSOM,
	CLOSED_EYEBLOSSOM,
	CACTUS_FLOWER,

	/**
	 * @deprecated Warning: On 1.8-1.12, this is only valid for the item and bottom block. The top double_plant block does not store flower type at all.
	 */
	@Deprecated
	SUNFLOWER("DOUBLE_PLANT", 0, DataValueMask.EXCLUDE_NONE, "SUNFLOWER"),
	/**
	 * @deprecated Only relevant (with caveats) on older Minecraft versions, see:
	 * https://minecraft.wiki/w/Java_Edition_pre-flattening_data_values#Large_Flowers
	 */
	@Deprecated
	SUNFLOWER_TOP("DOUBLE_PLANT", 8, DataValueMask.EXCLUDE_DOUBLE_PLANT_TOP_ORIENTATION_BITS, "SUNFLOWER"),
	/**
	 * @deprecated Warning: On 1.8-1.12, this is only valid for the item and bottom block. The top double_plant block does not store flower type at all.
	 */
	@Deprecated
	LILAC("DOUBLE_PLANT", 1, DataValueMask.EXCLUDE_NONE, "LILAC"),
	/**
	 * @deprecated Only relevant (with caveats) on older Minecraft versions, see:
	 * https://minecraft.wiki/w/Java_Edition_pre-flattening_data_values#Large_Flowers
	 */
	@Deprecated
	LILAC_TOP("DOUBLE_PLANT", 8, DataValueMask.EXCLUDE_DOUBLE_PLANT_TOP_ORIENTATION_BITS, "LILAC"),
	/**
	 * @deprecated Warning: On 1.8-1.12, this is only valid for the item and bottom block. The top double_plant block does not store flower type at all.
	 */
	@Deprecated
	ROSE_BUSH("DOUBLE_PLANT", 4, DataValueMask.EXCLUDE_NONE, "ROSE_BUSH"),
	/**
	 * @deprecated Only relevant (with caveats) on older Minecraft versions, see:
	 * https://minecraft.wiki/w/Java_Edition_pre-flattening_data_values#Large_Flowers
	 */
	@Deprecated
	ROSE_BUSH_TOP("DOUBLE_PLANT", 8, DataValueMask.EXCLUDE_DOUBLE_PLANT_TOP_ORIENTATION_BITS, "ROSE_BUSH"),
	/**
	 * @deprecated Warning: On 1.8-1.12, this is only valid for the item and bottom block. The top double_plant block does not store flower type at all.
	 */
	@Deprecated
	PEONY("DOUBLE_PLANT", 5, DataValueMask.EXCLUDE_NONE, "PEONY"),
	/**
	 * @deprecated Only relevant (with caveats) on older Minecraft versions, see:
	 * https://minecraft.wiki/w/Java_Edition_pre-flattening_data_values#Large_Flowers
	 */
	@Deprecated
	PEONY_TOP("DOUBLE_PLANT", 8, DataValueMask.EXCLUDE_DOUBLE_PLANT_TOP_ORIENTATION_BITS, "PEONY"),

	DEAD_BUSH,

	SHEARS,

	WOODEN_AXE("WOOD_AXE", "WOODEN_AXE"),
	STONE_AXE,
	COPPER_AXE,
	IRON_AXE,
	GOLDEN_AXE("GOLD_AXE", "GOLDEN_AXE"),
	DIAMOND_AXE,
	NETHERITE_AXE,

	WOODEN_HOE("WOOD_HOE", "WOODEN_HOE"),
	STONE_HOE,
	COPPER_HOE,
	IRON_HOE,
	GOLDEN_HOE("GOLD_HOE", "GOLDEN_HOE"),
	DIAMOND_HOE,
	NETHERITE_HOE,

	WOODEN_PICKAXE("WOOD_PICKAXE", "WOODEN_PICKAXE"),
	STONE_PICKAXE,
	COPPER_PICKAXE,
	IRON_PICKAXE,
	GOLDEN_PICKAXE("GOLD_PICKAXE", "GOLDEN_PICKAXE"),
	DIAMOND_PICKAXE,
	NETHERITE_PICKAXE,

	WOODEN_SHOVEL("WOOD_SPADE", "WOODEN_SHOVEL"),
	STONE_SHOVEL("STONE_SPADE", "STONE_SHOVEL"),
	COPPER_SHOVEL,
	IRON_SHOVEL("IRON_SPADE", "IRON_SHOVEL"),
	GOLDEN_SHOVEL("GOLD_SPADE", "GOLDEN_SHOVEL"),
	DIAMOND_SHOVEL("DIAMOND_SPADE", "DIAMOND_SHOVEL"),
	NETHERITE_SHOVEL,

	WOODEN_SWORD("WOOD_SWORD", "WOODEN_SWORD"),
	STONE_SWORD,
	COPPER_SWORD,
	IRON_SWORD,
	GOLDEN_SWORD("GOLD_SWORD", "GOLDEN_SWORD"),
	DIAMOND_SWORD,
	NETHERITE_SWORD,

	TRIDENT,
	MACE,

	OAK_LEAVES("LEAVES", 0, DataValueMask.EXCLUDE_LEAVES_DECAY_BITS, "OAK_LEAVES"),
	SPRUCE_LEAVES("LEAVES", 1, DataValueMask.EXCLUDE_LEAVES_DECAY_BITS, "SPRUCE_LEAVES"),
	BIRCH_LEAVES("LEAVES", 2, DataValueMask.EXCLUDE_LEAVES_DECAY_BITS, "BIRCH_LEAVES"),
	JUNGLE_LEAVES("LEAVES", 3, DataValueMask.EXCLUDE_LEAVES_DECAY_BITS, "JUNGLE_LEAVES"),
	ACACIA_LEAVES("LEAVES_2", 0, DataValueMask.EXCLUDE_LEAVES_DECAY_BITS, "ACACIA_LEAVES"),
	DARK_OAK_LEAVES("LEAVES_2", 1, DataValueMask.EXCLUDE_LEAVES_DECAY_BITS, "DARK_OAK_LEAVES"),
	MANGROVE_LEAVES,
	AZALEA_LEAVES,
	FLOWERING_AZALEA_LEAVES,
	CHERRY_LEAVES,
	PALE_OAK_LEAVES,

	OAK_LOG("LOG", 0, DataValueMask.EXCLUDE_LOG_ORIENTATION_BITS, "OAK_LOG"),
	SPRUCE_LOG("LOG", 1, DataValueMask.EXCLUDE_LOG_ORIENTATION_BITS, "SPRUCE_LOG"),
	BIRCH_LOG("LOG", 2, DataValueMask.EXCLUDE_LOG_ORIENTATION_BITS, "BIRCH_LOG"),
	JUNGLE_LOG("LOG", 3, DataValueMask.EXCLUDE_LOG_ORIENTATION_BITS, "JUNGLE_LOG"),
	ACACIA_LOG("LOG_2", 0, DataValueMask.EXCLUDE_LOG_ORIENTATION_BITS, "ACACIA_LOG"),
	DARK_OAK_LOG("LOG_2", 1, DataValueMask.EXCLUDE_LOG_ORIENTATION_BITS, "DARK_OAK_LOG"),
	MANGROVE_LOG,
	CHERRY_LOG,
	PALE_OAK_LOG,
	STRIPPED_OAK_LOG,
	STRIPPED_SPRUCE_LOG,
	STRIPPED_BIRCH_LOG,
	STRIPPED_JUNGLE_LOG,
	STRIPPED_ACACIA_LOG,
	STRIPPED_DARK_OAK_LOG,
	STRIPPED_MANGROVE_LOG,
	STRIPPED_CHERRY_LOG,
	STRIPPED_PALE_OAK_LOG,

	COOKED_BEEF("COOKED_BEEF", "COOKED_BEEF"),
	COOKED_CHICKEN("COOKED_CHICKEN", "COOKED_CHICKEN"),
	COOKED_MUTTON("COOKED_MUTTON", "COOKED_MUTTON"),
	COOKED_RABBIT("COOKED_RABBIT", "COOKED_RABBIT"),
	COOKED_PORKCHOP("GRILLED_PORK", "COOKED_PORKCHOP"),

	RAW_BEEF("RAW_BEEF", "BEEF"),
	RAW_CHICKEN("RAW_CHICKEN", "CHICKEN"),
	RAW_MUTTON("MUTTON", "MUTTON"),
	RAW_RABBIT("RABBIT", "RABBIT"),
	RAW_PORK("PORK", "PORKCHOP"),

	OAK_PLANKS("WOOD", 0, DataValueMask.EXCLUDE_NONE, "OAK_PLANKS"),
	SPRUCE_PLANKS("WOOD", 1, DataValueMask.EXCLUDE_NONE, "SPRUCE_PLANKS"),
	BIRCH_PLANKS("WOOD", 2, DataValueMask.EXCLUDE_NONE, "BIRCH_PLANKS"),
	JUNGLE_PLANKS("WOOD", 3, DataValueMask.EXCLUDE_NONE, "JUNGLE_PLANKS"),
	ACACIA_PLANKS("WOOD", 4, DataValueMask.EXCLUDE_NONE, "ACACIA_PLANKS"),
	DARK_OAK_PLANKS("WOOD", 5, DataValueMask.EXCLUDE_NONE, "DARK_OAK_PLANKS"),

	STONE("STONE", 0, DataValueMask.EXCLUDE_NONE, "STONE"),
	GRANITE("STONE", 1, DataValueMask.EXCLUDE_NONE, "GRANITE"),
	DIORITE("STONE", 3, DataValueMask.EXCLUDE_NONE, "DIORITE"),
	ANDESITE("STONE", 5, DataValueMask.EXCLUDE_NONE, "ANDESITE"),
	DEEPSLATE,
	TUFF;

	private final String name8;
	private final int dataValue8;
	private final int dataValueMask8;
	private final String name13;

	private Material material;

	UniversalMaterial(String name8, Integer dataValue8, Integer dataValueMask8, String name13) {
		this.name8 = name8;
		this.dataValue8 = dataValue8;
		this.dataValueMask8 = dataValueMask8;
		this.name13 = name13;
	}

	UniversalMaterial(String name8, String name13) {
		this.name8 = name8;
		this.dataValue8 = 0;
		this.dataValueMask8 = DataValueMask.EXCLUDE_ALL;
		this.name13 = name13;
	}

	UniversalMaterial() {
		this.name8 = name();
		this.dataValue8 = 0;
		this.dataValueMask8 = DataValueMask.EXCLUDE_ALL;
		this.name13 = name();
	}

	/**
	 * Used to mask out the important bits in the data value (usually all bits) and as such exclude bits we don't care about.
	 */
	private static final class DataValueMask {
		// The default, don't care about any data value bits, e.g. ignore tool item damage and so on.
		private static final int EXCLUDE_ALL = 0;
		// This is usually a good choice, but sometimes we have to add an exclusion mask.
		private static final int EXCLUDE_NONE = ~0;
		private static final int EXCLUDE_LEAVES_DECAY_BITS = ~0b1100;
		private static final int EXCLUDE_LOG_ORIENTATION_BITS = ~0b1100;
		private static final int EXCLUDE_DOUBLE_PLANT_TOP_ORIENTATION_BITS = ~0b0111;
	}

	/**
	 * @deprecated Warning: Material alone is not enough to uniquely identify a block/item on all game versions.
	 */
	@Deprecated
	public Material getType() {
		if (material == null) {
			try {
				if (PaperLib.getMinecraftVersion() > 12) material = Material.valueOf(name13);
				else material = Material.valueOf(name8);
			} catch (IllegalArgumentException ignored) {

			}
		}

		return material;
	}

	public ItemStack getStack(int amount) {
		return new ItemStack(getType(), amount, (short) dataValue8);
	}

	public ItemStack getStack() {
		return getStack(1);
	}

	private static <T> void putIfSupported(Map<Material, T> map, T value, UniversalMaterial... materials) {
		for (UniversalMaterial material : materials) {
			Material key = material.getType();
			if (key != null) {
				map.put(key, value);
			}
		}
	}

	private static Map<Material, Integer> buildMiningToolDamage() {
		Map<Material, Integer> miningDamage = new HashMap<>();
		putIfSupported(miningDamage, 1,
			UniversalMaterial.WOODEN_AXE,
			UniversalMaterial.WOODEN_HOE,
			UniversalMaterial.WOODEN_PICKAXE,
			UniversalMaterial.WOODEN_SHOVEL,
			UniversalMaterial.STONE_AXE,
			UniversalMaterial.STONE_HOE,
			UniversalMaterial.STONE_PICKAXE,
			UniversalMaterial.STONE_SHOVEL,
			UniversalMaterial.COPPER_AXE,
			UniversalMaterial.COPPER_HOE,
			UniversalMaterial.COPPER_PICKAXE,
			UniversalMaterial.COPPER_SHOVEL,
			UniversalMaterial.IRON_AXE,
			UniversalMaterial.IRON_HOE,
			UniversalMaterial.IRON_PICKAXE,
			UniversalMaterial.IRON_SHOVEL,
			UniversalMaterial.GOLDEN_AXE,
			UniversalMaterial.GOLDEN_HOE,
			UniversalMaterial.GOLDEN_PICKAXE,
			UniversalMaterial.GOLDEN_SHOVEL,
			UniversalMaterial.DIAMOND_AXE,
			UniversalMaterial.DIAMOND_HOE,
			UniversalMaterial.DIAMOND_PICKAXE,
			UniversalMaterial.DIAMOND_SHOVEL,
			UniversalMaterial.NETHERITE_AXE,
			UniversalMaterial.NETHERITE_HOE,
			UniversalMaterial.NETHERITE_PICKAXE,
			UniversalMaterial.NETHERITE_SHOVEL,
			UniversalMaterial.SHEARS);
		putIfSupported(miningDamage, 2,
			UniversalMaterial.WOODEN_SWORD,
			UniversalMaterial.STONE_SWORD,
			UniversalMaterial.COPPER_SWORD,
			UniversalMaterial.IRON_SWORD,
			UniversalMaterial.GOLDEN_SWORD,
			UniversalMaterial.DIAMOND_SWORD,
			UniversalMaterial.NETHERITE_SWORD,
			UniversalMaterial.TRIDENT,
			UniversalMaterial.MACE);
		return Collections.unmodifiableMap(miningDamage);
	}

	private static final Map<Material, Integer> miningToolDamage = buildMiningToolDamage();

	public static int getMiningToolDamage(Material item) {
		// For now, we only provide reasonable defaults, which don't fully
		// reflect the vanilla behavior on all Minecraft versions.
		// For example, we ignore the fact that some tools only take damage
		// when mining a specific type of block, and that tools usually don't
		// take damage when mining a block with 0 hardness.
		return miningToolDamage.getOrDefault(item, 0);
	}

	public static boolean isLog(Material material) {
		// Should probably use .matches on a Block, but actually this is fine for now
		return material == UniversalMaterial.OAK_LOG.getType()
			|| material == UniversalMaterial.SPRUCE_LOG.getType()
			|| material == UniversalMaterial.BIRCH_LOG.getType()
			|| material == UniversalMaterial.JUNGLE_LOG.getType()
			|| material == UniversalMaterial.ACACIA_LOG.getType()
			|| material == UniversalMaterial.DARK_OAK_LOG.getType()
			|| material == UniversalMaterial.MANGROVE_LOG.getType()
			|| material == UniversalMaterial.CHERRY_LOG.getType()
			|| material == UniversalMaterial.PALE_OAK_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_OAK_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_SPRUCE_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_BIRCH_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_JUNGLE_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_ACACIA_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_DARK_OAK_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_MANGROVE_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_CHERRY_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_PALE_OAK_LOG.getType();
	}

	public static boolean isLeaves(Material material) {
		// Should probably use .matches on a Block, but actually this is fine for now
		return material == UniversalMaterial.OAK_LEAVES.getType()
			|| material == UniversalMaterial.SPRUCE_LEAVES.getType()
			|| material == UniversalMaterial.BIRCH_LEAVES.getType()
			|| material == UniversalMaterial.JUNGLE_LEAVES.getType()
			|| material == UniversalMaterial.ACACIA_LEAVES.getType()
			|| material == UniversalMaterial.DARK_OAK_LEAVES.getType()
			|| material == UniversalMaterial.MANGROVE_LEAVES.getType()
			|| material == UniversalMaterial.AZALEA_LEAVES.getType()
			|| material == UniversalMaterial.FLOWERING_AZALEA_LEAVES.getType()
			|| material == UniversalMaterial.CHERRY_LEAVES.getType()
			|| material == UniversalMaterial.PALE_OAK_LEAVES.getType();
	}

	public static boolean isAppleLeaves(Block block) {
		return UniversalMaterial.OAK_LEAVES.matches(block) || UniversalMaterial.DARK_OAK_LEAVES.matches(block);
	}

	public static boolean isFlowerOrDeadBush(Block block) {
		return UniversalMaterial.POPPY.matches(block)
			|| UniversalMaterial.BLUE_ORCHID.matches(block)
			|| UniversalMaterial.ALLIUM.matches(block)
			|| UniversalMaterial.AZURE_BLUET.matches(block)
			|| UniversalMaterial.RED_TULIP.matches(block)
			|| UniversalMaterial.ORANGE_TULIP.matches(block)
			|| UniversalMaterial.WHITE_TULIP.matches(block)
			|| UniversalMaterial.PINK_TULIP.matches(block)
			|| UniversalMaterial.OXEYE_DAISY.matches(block)
			|| UniversalMaterial.DANDELION.matches(block)
			|| UniversalMaterial.CORNFLOWER.matches(block)
			|| UniversalMaterial.LILY_OF_THE_VALLEY.matches(block)
			|| UniversalMaterial.WITHER_ROSE.matches(block)
			|| UniversalMaterial.TORCHFLOWER.matches(block)
			|| UniversalMaterial.PITCHER_PLANT.matches(block)
			|| UniversalMaterial.OPEN_EYEBLOSSOM.matches(block)
			|| UniversalMaterial.CLOSED_EYEBLOSSOM.matches(block)
			|| UniversalMaterial.CACTUS_FLOWER.matches(block)
			|| UniversalMaterial.SUNFLOWER.matches(block)
			|| UniversalMaterial.SUNFLOWER_TOP.matches(block)
			|| UniversalMaterial.LILAC.matches(block)
			|| UniversalMaterial.LILAC_TOP.matches(block)
			|| UniversalMaterial.ROSE_BUSH.matches(block)
			|| UniversalMaterial.ROSE_BUSH_TOP.matches(block)
			|| UniversalMaterial.PEONY.matches(block)
			|| UniversalMaterial.PEONY_TOP.matches(block)
			|| UniversalMaterial.DEAD_BUSH.matches(block);
	}

	public static boolean isOreReplaceableBlock(Block block) {
		return UniversalMaterial.STONE.matches(block)
			|| UniversalMaterial.GRANITE.matches(block)
			|| UniversalMaterial.DIORITE.matches(block)
			|| UniversalMaterial.ANDESITE.matches(block)
			|| UniversalMaterial.DEEPSLATE.matches(block)
			|| UniversalMaterial.TUFF.matches(block);
	}

	public static boolean isAxe(Material tool) {
		// Should probably use .matches on an ItemStack, but actually this is fine for now
		return tool == UniversalMaterial.WOODEN_AXE.getType()
			|| tool == Material.STONE_AXE
			|| tool == UniversalMaterial.COPPER_AXE.getType()
			|| tool == Material.IRON_AXE
			|| tool == UniversalMaterial.GOLDEN_AXE.getType()
			|| tool == Material.DIAMOND_AXE
			|| tool == UniversalMaterial.NETHERITE_AXE.getType();
	}

	public boolean matches(Block block) {
		if (PaperLib.isVersion(13)) {
			return block.getType() == getType();
		} else {
			return block.getType() == getType() && (block.getData() & dataValueMask8) == dataValue8;
		}
	}

	public boolean matches(ItemStack itemStack) {
		if (PaperLib.isVersion(13)) {
			return itemStack.getType() == getType();
		} else {
			return itemStack.getType() == getType() && (itemStack.getDurability() & dataValueMask8) == dataValue8;
		}
	}

}
