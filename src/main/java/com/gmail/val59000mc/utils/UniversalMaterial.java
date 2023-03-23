package com.gmail.val59000mc.utils;

import io.papermc.lib.PaperLib;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public enum UniversalMaterial{
	WHITE_WOOL("WOOL", "WHITE_WOOL", (short) 0),
	ORANGE_WOOL("WOOL", "ORANGE_WOOL", (short) 1),
	MAGENTA_WOOL("WOOL", "MAGENTA_WOOL", (short) 2),
	LIGHT_BLUE_WOOL("WOOL", "LIGHT_BLUE_WOOL", (short) 3),
	YELLOW_WOOL("WOOL", "YELLOW_WOOL", (short) 4),
	LIME_WOOL("WOOL", "LIME_WOOL", (short) 5),
	PINK_WOOL("WOOL", "PINK_WOOL", (short) 6),
	GRAY_WOOL("WOOL", "GRAY_WOOL", (short) 7),
	LIGHT_GRAY_WOOL("WOOL", "LIGHT_GRAY_WOOL", (short) 8),
	CYAN_WOOL("WOOL", "CYAN_WOOL", (short) 9),
	PURPLE_WOOL("WOOL", "PURPLE_WOOL", (short) 10),
	BLUE_WOOL("WOOL", "BLUE_WOOL", (short) 11),
	BROWN_WOOL("WOOL", "BROWN_WOOL", (short) 12),
	GREEN_WOOL("WOOL", "GREEN_WOOL", (short) 13),
	RED_WOOL("WOOL", "RED_WOOL", (short) 14),
	BLACK_WOOL("WOOL", "BLACK_WOOL", (short) 15),

	STATIONARY_WATER("STATIONARY_WATER", "WATER"),
	STATIONARY_LAVA("STATIONARY_LAVA", "LAVA"),
	SUGAR_CANE_BLOCK("SUGAR_CANE_BLOCK", "SUGAR_CANE"),
	CAVE_AIR("CAVE_AIR", "CAVE_AIR"),
	GRASS_BLOCK("GRASS", "GRASS_BLOCK"),

	SKELETON_SKULL("SKULL_ITEM","SKELETON_SKULL", (short) 0),
	WITHER_SKELETON_SKULL("SKULL_ITEM","WITHER_SKELETON_SKULL", (short) 1),
	ZOMBIE_HEAD("SKULL_ITEM","ZOMBIE_HEAD", (short) 2),
	PLAYER_HEAD("SKULL_ITEM","PLAYER_HEAD", (short) 3),
	PLAYER_HEAD_BLOCK("SKULL","PLAYER_HEAD", (short) 3),
	CREEPER_HEAD("SKULL_ITEM", "CREEPER_HEAD", (short) 4),
	DRAGON_HEAD("SKULL_ITEM", "DRAGON_HEAD", (short) 5),

	OAK_FENCE("FENCE", "OAK_FENCE"),

	PUFFERFISH("RAW_FISH", "PUFFERFISH", (short) 3),

	WHITE_STAINED_GLASS_PANE("STAINED_GLASS_PANE", "WHITE_STAINED_GLASS_PANE", (short) 0),
	BLACK_STAINED_GLASS_PANE("STAINED_GLASS_PANE", "BLACK_STAINED_GLASS_PANE", (short) 15),

	IRON_INGOT,
	LAVA_BUCKET,
	BOW,
	FISHING_ROD,
	SHIELD("SHIELD", "SHIELD"),
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
	LAPIS_LAZULI("INK_SACK", "LAPIS_LAZULI", (short) 4),
	RED_DYE("INK_SACK", "RED_DYE", (short) 1),
	DRAGON_EGG,
	END_PORTAL_FRAME("ENDER_PORTAL_FRAME", "END_PORTAL_FRAME"),
	END_PORTAL("ENDER_PORTAL", "END_PORTAL"),
	END_GATEWAY,
	NETHER_PORTAL("PORTAL", "NETHER_PORTAL"),
	NETHER_STAR,
	NAME_TAG,
	ENCHANTING_TABLE("ENCHANTMENT_TABLE", "ENCHANTING_TABLE"),
	WOLF_SPAWN_EGG("MONSTER_EGG", "WOLF_SPAWN_EGG", (short) 95),
	CLOCK("WATCH", "CLOCK"),
	EGG,
	ENCHANTED_BOOK,
	PAPER,
	BARRIER,
	AIR,
	COMPASS,
	NETHER_BRICK,
	RED_BANNER("BANNER", "RED_BANNER", (short) 1),
	ELYTRA,
	CRAFTING_TABLE("WORKBENCH", "CRAFTING_TABLE"),
	EXPERIENCE_BOTTLE("EXP_BOTTLE", "EXPERIENCE_BOTTLE"),
	IRON_DOOR,
	LIGHT_GRAY_STAINED_GLASS_PANE("STAINED_GLASS_PANE", "LIGHT_GRAY_STAINED_GLASS_PANE", (short) 8),
	ANVIL,
	MAGMA_BLOCK("MAGMA", "MAGMA_BLOCK"),
	CAMPFIRE,
	SOUL_CAMPFIRE,
	CACTUS,
	FIRE,
	SOUL_FIRE,
	POWDER_SNOW,

	// Flowers
	POPPY("RED_ROSE", "POPPY", (short) 0),
	BLUE_ORCHID("RED_ROSE", "BLUE_ORCHID", (short) 1),
	ALLIUM("RED_ROSE", "ALLIUM", (short) 2),
	AZURE_BLUET("RED_ROSE", "AZURE_BLUET", (short) 3),
	RED_TULIP("RED_ROSE", "RED_TULIP", (short) 4),
	ORANGE_TULIP("RED_ROSE", "ORANGE_TULIP", (short) 5),
	WHITE_TULIP("RED_ROSE", "WHITE_TULIP", (short) 6),
	PINK_TULIP("RED_ROSE", "PINK_TULIP", (short) 7),
	OXEYE_DAISY("RED_ROSE", "OXEYE_DAISY", (short) 8),
	DANDELION("YELLOW_FLOWER", "DANDELION"),

	SUNFLOWER("DOUBLE_PLANT", "SUNFLOWER", (short) 0),
	LILAC("DOUBLE_PLANT", "LILAC", (short) 1),
	ROSE_BUSH("DOUBLE_PLANT", "ROSE_BUSH", (short) 4),
	PEONY("DOUBLE_PLANT", "PEONY", (short) 5),
	DEAD_BUSH,

	SHEARS,

	WOODEN_AXE("WOOD_AXE", "WOODEN_AXE"),
	STONE_AXE,
	IRON_AXE,
	GOLDEN_AXE("GOLD_AXE", "GOLDEN_AXE"),
	DIAMOND_AXE,
	NETHERITE_AXE,

	WOODEN_HOE("WOOD_HOE", "WOODEN_HOE"),
	STONE_HOE,
	IRON_HOE,
	GOLDEN_HOE("GOLD_HOE", "GOLDEN_HOE"),
	DIAMOND_HOE,
	NETHERITE_HOE,

	WOODEN_PICKAXE("WOOD_PICKAXE", "WOODEN_PICKAXE"),
	STONE_PICKAXE,
	IRON_PICKAXE,
	GOLDEN_PICKAXE("GOLD_PICKAXE", "GOLDEN_PICKAXE"),
	DIAMOND_PICKAXE,
	NETHERITE_PICKAXE,

	WOODEN_SHOVEL("WOOD_SPADE", "WOODEN_SHOVEL"),
	STONE_SHOVEL("STONE_SPADE", "STONE_SHOVEL"),
	IRON_SHOVEL("IRON_SPADE", "IRON_SHOVEL"),
	GOLDEN_SHOVEL("GOLD_SPADE", "GOLDEN_SHOVEL"),
	DIAMOND_SHOVEL("DIAMOND_SPADE", "DIAMOND_SHOVEL"),
	NETHERITE_SHOVEL,

	WOODEN_SWORD("WOOD_SWORD", "WOODEN_SWORD"),
	STONE_SWORD,
	IRON_SWORD,
	GOLDEN_SWORD("GOLD_SWORD", "GOLDEN_SWORD"),
	DIAMOND_SWORD,
	NETHERITE_SWORD,

	TRIDENT,

	OAK_LEAVES("LEAVES", "OAK_LEAVES", (short) 0),
	SPRUCE_LEAVES("LEAVES", "SPRUCE_LEAVES", (short) 1),
	BIRCH_LEAVES("LEAVES", "BIRCH_LEAVES", (short) 2),
	JUNGLE_LEAVES("LEAVES", "JUNGLE_LEAVES", (short) 3),
	ACACIA_LEAVES("LEAVES_2", "ACACIA_LEAVES", (short) 0),
	DARK_OAK_LEAVES("LEAVES_2", "DARK_OAK_LEAVES", (short) 1),
	MANGROVE_LEAVES,
	AZALEA_LEAVES,
	FLOWERING_AZALEA_LEAVES,

	OAK_LOG("LOG", "OAK_LOG", (short) 0),
	SPRUCE_LOG("LOG", "SPRUCE_LOG", (short) 1),
	BIRCH_LOG("LOG", "BIRCH_LOG", (short) 2),
	JUNGLE_LOG("LOG", "JUNGLE_LOG", (short) 3),
	ACACIA_LOG("LOG_2", "ACACIA_LOG", (short) 0),
	DARK_OAK_LOG("LOG_2", "DARK_OAK_LOG", (short) 1),
	MANGROVE_LOG,
	STRIPPED_OAK_LOG,
	STRIPPED_SPRUCE_LOG,
	STRIPPED_BIRCH_LOG,
	STRIPPED_JUNGLE_LOG,
	STRIPPED_ACACIA_LOG,
	STRIPPED_DARK_OAK_LOG,
	STRIPPED_MANGROVE_LOG,

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

	OAK_PLANKS("WOOD", "OAK_PLANKS", (short) 0),
	SPRUCE_PLANKS("WOOD", "SPRUCE_PLANKS", (short) 1),
	BIRCH_PLANKS("WOOD", "BIRCH_PLANKS", (short) 2),
	JUNGLE_PLANKS("WOOD", "JUNGLE_PLANKS", (short) 3),
	ACACIA_PLANKS("WOOD", "ACACIA_PLANKS", (short) 4),
	DARK_OAK_PLANKS("WOOD", "DARK_OAK_PLANKS", (short) 5);

	private final String name8, name13;
	private final short id8;

	private Material material;

	UniversalMaterial(String name8, String name13, short id8){
		this.name8 = name8;
		this.name13 = name13;
		this.id8 = id8;
	}

	UniversalMaterial(String name8, String name13){
		this.name8 = name8;
		this.name13 = name13;
		id8 = 0;
	}

	UniversalMaterial(){
		this.name8 = name();
		this.name13 = name();
		id8 = 0;
	}

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

	public short getData(){
		return PaperLib.getMinecraftVersion() < 13 ? id8 : 0;
	}

	@SuppressWarnings("deprecation")
	public ItemStack getStack(int amount) {
		return new ItemStack(getType(), amount, getData());
	}

	public ItemStack getStack(){
		return getStack(1);
	}

	public static UniversalMaterial ofType(Material material){
		for (UniversalMaterial universalMaterial : values()){
			if (universalMaterial.getType() == material){
				return universalMaterial;
			}
		}
		return null;
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
			UniversalMaterial.IRON_SWORD,
			UniversalMaterial.GOLDEN_SWORD,
			UniversalMaterial.DIAMOND_SWORD,
			UniversalMaterial.NETHERITE_SWORD,
			UniversalMaterial.TRIDENT);
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
		return material == UniversalMaterial.OAK_LOG.getType()
			|| material == UniversalMaterial.SPRUCE_LOG.getType()
			|| material == UniversalMaterial.BIRCH_LOG.getType()
			|| material == UniversalMaterial.JUNGLE_LOG.getType()
			|| material == UniversalMaterial.ACACIA_LOG.getType()
			|| material == UniversalMaterial.DARK_OAK_LOG.getType()
			|| material == UniversalMaterial.MANGROVE_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_OAK_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_SPRUCE_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_BIRCH_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_JUNGLE_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_ACACIA_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_DARK_OAK_LOG.getType()
			|| material == UniversalMaterial.STRIPPED_MANGROVE_LOG.getType();
	}

	public static boolean isLeaves(Material material) {
		return material == UniversalMaterial.OAK_LEAVES.getType()
			|| material == UniversalMaterial.SPRUCE_LEAVES.getType()
			|| material == UniversalMaterial.BIRCH_LEAVES.getType()
			|| material == UniversalMaterial.JUNGLE_LEAVES.getType()
			|| material == UniversalMaterial.ACACIA_LEAVES.getType()
			|| material == UniversalMaterial.DARK_OAK_LEAVES.getType()
			|| material == UniversalMaterial.MANGROVE_LEAVES.getType()
			|| material == UniversalMaterial.AZALEA_LEAVES.getType()
			|| material == UniversalMaterial.FLOWERING_AZALEA_LEAVES.getType();
	}

	public static boolean isAxe(Material tool) {
		return tool == UniversalMaterial.WOODEN_AXE.getType()
			|| tool == Material.STONE_AXE
			|| tool == Material.IRON_AXE
			|| tool == UniversalMaterial.GOLDEN_AXE.getType()
			|| tool == Material.DIAMOND_AXE
			|| tool == UniversalMaterial.NETHERITE_AXE.getType();
	}

	@SuppressWarnings("deprecation")
	public boolean equals(Block block){
		return block.getType() == getType() && block.getData() == id8;
	}

}
