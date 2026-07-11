package com.gmail.val59000mc.utils;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.exceptions.ParseException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.maploader.MapLoader;
import com.gmail.val59000mc.players.UhcPlayer;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameRule;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Skull;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class VersionUtils {

	private static final Logger LOGGER = Logger.getLogger(VersionUtils.class.getCanonicalName());
	private static final VersionUtils VERSION_UTILS = new VersionUtils();

	public static VersionUtils getVersionUtils() {
		return VERSION_UTILS;
	}

	public ShapedRecipe createShapedRecipe(ItemStack craft, String craftKey) {
		NamespacedKey namespacedKey = new NamespacedKey(UhcCore.getPlugin(), craftKey);
		return new ShapedRecipe(namespacedKey, craft);
	}

	public ItemStack createPlayerSkull(String name, UUID uuid) {
		ItemStack item = UniversalMaterial.PLAYER_HEAD_ITEM.getStack();
		SkullMeta im = (SkullMeta) item.getItemMeta();
		im.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
		item.setItemMeta(im);
		return item;
	}

	public void setSkullOwner(Skull skull, UhcPlayer player) {
		skull.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUuid()));
	}

	public Objective registerNewObjective(Scoreboard scoreboard, String name, String criteria, String displayName, String renderType) {
		if (renderType == null) {
			return scoreboard.registerNewObjective(name, criteria, displayName);
		}

		final RenderType renderTypeEnum = renderType.equals("hearts") ? RenderType.HEARTS : RenderType.INTEGER;
		return scoreboard.registerNewObjective(name, criteria, displayName, renderTypeEnum);
	}

	public Objective registerNewObjective(Scoreboard scoreboard, String name, String criteria, String renderType) {
		return registerNewObjective(scoreboard, name, criteria, name, renderType);
	}

	public Objective registerNewObjective(Scoreboard scoreboard, String name, String criteria) {
		return registerNewObjective(scoreboard, name, criteria, name, null);
	}

	public void setPlayerMaxHealth(Player player, double maxHealth) {
		player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(maxHealth);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setGameRuleValue(World world, String name, Object value) {
		GameRule gameRule = GameRule.getByName(name);
		if (gameRule != null) {
			world.setGameRule(gameRule, value);
		}
	}

	public boolean hasEye(Block block) {
		EndPortalFrame portalFrame = (EndPortalFrame) block.getBlockData();
		return portalFrame.hasEye();
	}

	public void setEye(Block block, boolean eye) {
		EndPortalFrame portalFrame = (EndPortalFrame) block.getBlockData();
		portalFrame.setEye(eye);
		block.setBlockData(portalFrame);
	}

	public void setEndPortalFrameOrientation(Block block, BlockFace blockFace) {
		EndPortalFrame portalFrame = (EndPortalFrame) block.getBlockData();
		portalFrame.setFacing(blockFace);
		block.setBlockData(portalFrame);
	}

	public void setTeamNameTagVisibility(Team team, boolean value) {
		team.setOption(Team.Option.NAME_TAG_VISIBILITY, value ? Team.OptionStatus.ALWAYS : Team.OptionStatus.NEVER);
	}

	public void setChestName(Chest chest, String name) {
		chest.setCustomName(name);
		chest.update();
	}

	@Nullable
	public JsonObject getBasePotionEffect(PotionMeta potionMeta) {
		if (!potionMeta.hasBasePotionType()) {
			return null;
		}

		PotionType potionType = potionMeta.getBasePotionType();
		String type = potionType.name();
		JsonObject baseEffect = new JsonObject();

		if (type.startsWith("LONG_")) {
			baseEffect.addProperty("type", type.substring("LONG_".length()));
			baseEffect.addProperty("extended", true);
		} else if (type.startsWith("STRONG_")) {
			baseEffect.addProperty("type", type.substring("STRONG_".length()));
			baseEffect.addProperty("upgraded", true);
		} else {
			baseEffect.addProperty("type", type);
		}
		return baseEffect;
	}

	@Nullable
	public Color getPotionColor(PotionMeta potionMeta) {
		if (potionMeta.hasColor()) {
			return potionMeta.getColor();
		}

		return null;
	}

	public PotionMeta setPotionColor(PotionMeta potionMeta, Color color) {
		potionMeta.setColor(color);
		return potionMeta;
	}

	public void setChestSide(Chest chest, boolean left) {
		org.bukkit.block.data.type.Chest chestData = (org.bukkit.block.data.type.Chest) chest.getBlockData();
		org.bukkit.block.data.type.Chest.Type side = left ? org.bukkit.block.data.type.Chest.Type.LEFT : org.bukkit.block.data.type.Chest.Type.RIGHT;

		chestData.setType(side);
		chest.getBlock().setBlockData(chestData, true);
	}

	public void removeRecipe(ItemStack item, Recipe recipe) {
		NamespacedKey key;

		if (recipe instanceof Keyed) {
			key = ((Keyed) recipe).getKey();
		} else {
			key = item.getType().getKey();
		}

		boolean removed = Bukkit.removeRecipe(key);

		if (removed) {
			LOGGER.fine(() -> "Removed recipe for " + key);
		} else {
			LOGGER.warning("Failed to remove recipe for " + key);
		}
	}

	public void handleNetherPortalEvent(PlayerPortalEvent event) {
		GameManager gm = GameManager.getGameManager();
		MainConfig cfg = gm.getConfig();

		Location loc = event.getFrom();
		MapLoader mapLoader = GameManager.getGameManager().getMapLoader();
		double netherScale = cfg.get(MainConfig.NETHER_SCALE);

		if (event.getFrom().getWorld().getEnvironment() == World.Environment.NETHER) {
			loc.setWorld(mapLoader.getUhcWorld(World.Environment.NORMAL));
			loc.setX(loc.getX() * netherScale);
			loc.setZ(loc.getZ() * netherScale);
			event.setTo(loc);
		} else {
			loc.setWorld(mapLoader.getUhcWorld(World.Environment.NETHER));
			loc.setX(loc.getX() / netherScale);
			loc.setZ(loc.getZ() / netherScale);
			event.setTo(loc);
		}
	}

	@Nullable
	public JsonObject getItemAttributes(ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		if (!meta.hasAttributeModifiers()) {
			return null;
		}

		JsonObject attributesJson = new JsonObject();
		Multimap<Attribute, AttributeModifier> attributeModifiers = meta.getAttributeModifiers();
		if (attributeModifiers == null || attributeModifiers.isEmpty()) {
			return null;
		}

		for (Attribute attribute : attributeModifiers.keySet()) {
			JsonArray modifiersJson = new JsonArray();
			Collection<AttributeModifier> modifiers = attributeModifiers.get(attribute);

			for (AttributeModifier modifier : modifiers) {
				JsonObject modifierObject = new JsonObject();
				modifierObject.addProperty("key", modifier.getKey().toString());
				modifierObject.addProperty("name", modifier.getName());
				modifierObject.addProperty("amount", modifier.getAmount());
				modifierObject.addProperty("operation", modifier.getOperation().name());
				if (modifier.getSlot() != null) {
					modifierObject.addProperty("slot", modifier.getSlot().name());
				}
				modifiersJson.add(modifierObject);
			}

			attributesJson.add(attribute.name(), modifiersJson);
		}

		return attributesJson;
	}

	public JsonItemStack applyItemAttributes(JsonItemStack itemStack, JsonObject attributes) {
		ItemMeta meta = itemStack.getItemMeta();
		Set<Map.Entry<String, JsonElement>> entries = attributes.entrySet();

		for (Map.Entry<String, JsonElement> attributeEntry : entries) {
			Attribute attribute = getAttributeFromKey(attributeEntry.getKey());
			if (attribute == null) {
				LOGGER.warning("Unknown attribute: " + attributeEntry.getKey());
				continue;
			}

			for (JsonElement jsonElement : attributeEntry.getValue().getAsJsonArray()) {
				JsonObject modifier = jsonElement.getAsJsonObject();

				String name = modifier.get("name").getAsString();
				double amount = modifier.get("amount").getAsDouble();
				String operation = modifier.get("operation").getAsString();
				EquipmentSlot slot = null;

				if (modifier.has("slot")) {
					slot = EquipmentSlot.valueOf(modifier.get("slot").getAsString());
				}

				final UUID uuid;
				if (modifier.has("uuid")) {
					uuid = UUID.fromString(modifier.get("uuid").getAsString());
				} else {
					uuid = UUID.randomUUID();
				}
				final NamespacedKey modifierKey = getModifierKey(modifier, name, uuid);
				final EquipmentSlotGroup slotGroup = getEquipmentSlotGroup(slot);

				meta.addAttributeModifier(attribute, new AttributeModifier(
					modifierKey,
					amount,
					AttributeModifier.Operation.valueOf(operation),
					slotGroup
				));
			}
		}
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	private NamespacedKey getModifierKey(JsonObject modifier, String name, UUID uuid) {
		if (modifier.has("key")) {
			NamespacedKey key = namespacedKeyFromString(modifier.get("key").getAsString());
			if (key != null) {
				return key;
			}
		}

		return new NamespacedKey(UhcCore.getPlugin(), sanitizeKey(name) + "_" + uuid.toString().toLowerCase(Locale.ROOT));
	}

	private String sanitizeKey(String value) {
		return value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9._/-]", "_");
	}

	private EquipmentSlotGroup getEquipmentSlotGroup(@Nullable EquipmentSlot slot) {
		if (slot == null) {
			return EquipmentSlotGroup.ANY;
		}

		switch (slot) {
			case HAND:
				return EquipmentSlotGroup.MAINHAND;
			case OFF_HAND:
				return EquipmentSlotGroup.OFFHAND;
			case FEET:
				return EquipmentSlotGroup.FEET;
			case LEGS:
				return EquipmentSlotGroup.LEGS;
			case CHEST:
				return EquipmentSlotGroup.CHEST;
			case HEAD:
				return EquipmentSlotGroup.HEAD;
			default:
				return EquipmentSlotGroup.ANY;
		}
	}

	@Nullable
	private Attribute getAttributeFromKey(String key) {
		try {
			return Attribute.valueOf(key);
		} catch (IllegalArgumentException ignored) {
			// Continue and try normalized legacy names/namespaced keys.
		}

		if (key.startsWith("GENERIC_")) {
			try {
				return Attribute.valueOf(key.substring("GENERIC_".length()));
			} catch (IllegalArgumentException ignored) {
				// Continue and try namespaced keys.
			}
		}

		NamespacedKey namespacedKey = namespacedKeyFromString(key.toLowerCase(Locale.ROOT));
		if (namespacedKey == null) {
			return null;
		}
		return org.bukkit.Registry.ATTRIBUTE.get(namespacedKey);
	}

	public String getEnchantmentKey(Enchantment enchantment) {
		return enchantment.getKey().toString();
	}

	@Nullable
	public Enchantment getEnchantmentFromKey(String key) {
		Enchantment enchantment = Enchantment.getByName(key);

		if (enchantment != null) {
			LOGGER.warning("Using old deprecated enchantment names, replace: " + key + " with " + enchantment.getKey());
			return enchantment;
		}

		NamespacedKey namespace = namespacedKeyFromString(key);
		if (namespace == null) {
			return null;
		}

		return Enchantment.getByKey(namespace);
	}

	@Nullable
	private NamespacedKey namespacedKeyFromString(String string) {
		if (!string.contains(":")) {
			string = "minecraft:" + string;
		}
		return NamespacedKey.fromString(string);
	}

	public void setEntityAI(LivingEntity entity, boolean b) {
		entity.setAI(b);
	}

	public List<Material> getItemList() {
		List<Material> items = new ArrayList<>();

		for (Material material : Material.values()) {
			if (material.isItem() && !isAir(material)) {
				items.add(material);
			}
		}

		return items;
	}

	@Nullable
	public JsonArray getSuspiciousStewEffects(ItemMeta meta) {
		if (!(meta instanceof SuspiciousStewMeta)) {
			return null;
		}

		SuspiciousStewMeta stewMeta = (SuspiciousStewMeta) meta;

		JsonArray customEffects = new JsonArray();
		for (PotionEffect effect : stewMeta.getCustomEffects()) {
			customEffects.add(JsonItemUtils.getPotionEffectJson(effect));
		}

		return customEffects;
	}

	public ItemMeta applySuspiciousStewEffects(ItemMeta meta, JsonArray effects) throws ParseException {
		SuspiciousStewMeta stewMeta = (SuspiciousStewMeta) meta;

		for (JsonElement jsonElement : effects) {
			JsonObject effect = jsonElement.getAsJsonObject();
			stewMeta.addCustomEffect(JsonItemUtils.parsePotionEffect(effect), true);
		}

		return stewMeta;
	}

	public void setItemUnbreakable(ItemMeta meta, boolean b) {
		meta.setUnbreakable(b);
	}

	public boolean getItemUnbreakable(ItemMeta meta) {
		return meta.isUnbreakable();
	}

	public boolean isAir(Material material) {
		return material.isAir();
	}

	public boolean leavesIsPersistent(Block leaves) {
		Leaves leavesData = (Leaves) leaves.getBlockData();
		return leavesData.isPersistent();
	}

}
