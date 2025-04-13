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

import io.papermc.lib.PaperLib;

import org.apache.commons.lang.Validate;
import org.bukkit.*;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VersionUtils_1_13 extends VersionUtils{

	private static final Logger LOGGER = Logger.getLogger(VersionUtils_1_13.class.getCanonicalName());

	@Override
	public ShapedRecipe createShapedRecipe(ItemStack craft, String craftKey) {
		NamespacedKey namespacedKey = new NamespacedKey(UhcCore.getPlugin(), craftKey);
		return new ShapedRecipe(namespacedKey, craft);
	}

	@Override
	public ItemStack createPlayerSkull(String name, UUID uuid) {
		ItemStack item = UniversalMaterial.PLAYER_HEAD_ITEM.getStack();
		SkullMeta im = (SkullMeta) item.getItemMeta();
		im.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
		item.setItemMeta(im);
		return item;
	}

	@Override
	public void setSkullOwner(Skull skull, UhcPlayer player) {
		skull.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUuid()));
	}

	@Override
	public Objective registerNewObjective(Scoreboard scoreboard, String name, String criteria, String displayName, String renderType) {
		if (renderType == null) {
			return scoreboard.registerNewObjective(name, criteria, displayName);
		} else {
			final RenderType renderTypeEnum = renderType.equals("hearts") ? RenderType.HEARTS : RenderType.INTEGER;
			return scoreboard.registerNewObjective(name, criteria, displayName, renderTypeEnum);
		}
	}

	@Override
	public void setPlayerMaxHealth(Player player, double maxHealth) {
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
	}

	@Override @SuppressWarnings("unchecked")
	public void setGameRuleValue(World world, String name, Object value){
		GameRule gameRule = GameRule.getByName(name);
		world.setGameRule(gameRule, value);
	}

	@Override
	public boolean hasEye(Block block) {
		EndPortalFrame portalFrame = (EndPortalFrame) block.getBlockData();
		return portalFrame.hasEye();
	}

	@Override
	public void setEye(Block block, boolean eye) {
		EndPortalFrame portalFrame = (EndPortalFrame) block.getBlockData();
		portalFrame.setEye(eye);
		block.setBlockData(portalFrame);
	}

	@Override
	public void setEndPortalFrameOrientation(Block block, BlockFace blockFace) {
		EndPortalFrame portalFrame = (EndPortalFrame) block.getBlockData();
		portalFrame.setFacing(blockFace);
		block.setBlockData(portalFrame);
	}

	@Override
	public void setTeamNameTagVisibility(Team team, boolean value){
		team.setOption(Team.Option.NAME_TAG_VISIBILITY, value?Team.OptionStatus.ALWAYS:Team.OptionStatus.NEVER);
	}

	@Override
	public void setChestName(Chest chest, String name){
		chest.setCustomName(name);
		chest.update();
	}

	@Override
	public JsonObject getBasePotionEffect(PotionMeta potionMeta) {
		PotionData potionData = potionMeta.getBasePotionData();
		// As of Minecraft 1.20.5 (with the introduction of data components), PotionData no longer exists,
		// and the replacement which Spigot has compatibility for is nullable.
		if (potionData == null) {
			return null;
		}
		JsonObject baseEffect = new JsonObject();
		baseEffect.addProperty("type", potionData.getType().name());

		if (potionData.isUpgraded()) {
			baseEffect.addProperty("upgraded", true);
		}
		if (potionData.isExtended()) {
			baseEffect.addProperty("extended", true);
		}
		return baseEffect;
	}

	@Override
	public PotionMeta setBasePotionEffect(PotionMeta potionMeta, PotionData potionData) {
		potionMeta.setBasePotionData(potionData);
		return potionMeta;
	}

	@Nullable
	@Override
	public Color getPotionColor(PotionMeta potionMeta){
		if (potionMeta.hasColor()){
			return potionMeta.getColor();
		}

		return null;
	}

	@Override
	public PotionMeta setPotionColor(PotionMeta potionMeta, Color color){
		potionMeta.setColor(color);
		return potionMeta;
	}

	@Override
	public void setChestSide(Chest chest, boolean left){
		org.bukkit.block.data.type.Chest chestData = (org.bukkit.block.data.type.Chest) chest.getBlockData();

		org.bukkit.block.data.type.Chest.Type side = left ? org.bukkit.block.data.type.Chest.Type.LEFT : org.bukkit.block.data.type.Chest.Type.RIGHT;

		chestData.setType(side);
		chest.getBlock().setBlockData(chestData, true);
	}

	@Override
	public void removeRecipe(ItemStack item, Recipe recipe){
		Iterator<Recipe> iterator = Bukkit.recipeIterator();

		while (iterator.hasNext()){
			if (iterator.next().getResult().isSimilar(item)){
				iterator.remove();
				LOGGER.fine(() -> "Removed recipe for item " + JsonItemUtils.getItemJson(item));
			}
		}
	}

	@Override
	public void handleNetherPortalEvent(PlayerPortalEvent event){
		if (event.getTo() != null){
			return;
		}

		GameManager gm = GameManager.getGameManager();
		MainConfig cfg = gm.getConfig();

		Location loc = event.getFrom();
		MapLoader mapLoader = GameManager.getGameManager().getMapLoader();
		double netherScale = cfg.get(MainConfig.NETHER_SCALE);

		try{
			Class<?> travelAgent = Class.forName("org.bukkit.TravelAgent");
			Method getPortalTravelAgent = NMSUtils.getMethod(event.getClass(), "getPortalTravelAgent");
			Method findOrCreate = NMSUtils.getMethod(travelAgent, "findOrCreate", Location.class);
			Object travelAgentInstance = getPortalTravelAgent.invoke(event);

			if (event.getFrom().getWorld().getEnvironment() == World.Environment.NETHER){
				loc.setWorld(mapLoader.getUhcWorld(World.Environment.NORMAL));
				loc.setX(loc.getX() * netherScale);
				loc.setZ(loc.getZ() * netherScale);
				Location to = (Location) findOrCreate.invoke(travelAgentInstance, loc);
				Validate.notNull(to, "TravelAgent returned null location!");
				event.setTo(to);
			}else{
				loc.setWorld(mapLoader.getUhcWorld(World.Environment.NETHER));
				loc.setX(loc.getX() / netherScale);
				loc.setZ(loc.getZ() / netherScale);
				Location to = (Location) findOrCreate.invoke(travelAgentInstance, loc);
				Validate.notNull(to, "TravelAgent returned null location!");
				event.setTo(to);
			}
		}catch (ReflectiveOperationException ex){
			LOGGER.log(Level.WARNING, "Unable to handle nether portal event", ex);
		}
	}

	@Nullable
	@Override
	public JsonObject getItemAttributes(ItemStack itemStack){
		ItemMeta meta = itemStack.getItemMeta();
		if (!meta.hasAttributeModifiers()){
			return null;
		}

		JsonObject attributesJson = new JsonObject();
		Multimap<Attribute, AttributeModifier> attributeModifiers = meta.getAttributeModifiers();

		for (Attribute attribute : attributeModifiers.keySet()){
			JsonArray modifiersJson = new JsonArray();
			Collection<AttributeModifier> modifiers = attributeModifiers.get(attribute);

			for (AttributeModifier modifier : modifiers){
				JsonObject modifierObject = new JsonObject();
				modifierObject.addProperty("name", modifier.getName());
				modifierObject.addProperty("amount", modifier.getAmount());
				modifierObject.addProperty("operation", modifier.getOperation().name());
				if (modifier.getSlot() != null){
					modifierObject.addProperty("slot", modifier.getSlot().name());
				}
				// AttributeModifier#getUniqueId() is broken (throws exception) on Minecraft 1.21+ because namespaced keys are now used instead
				if (!PaperLib.isVersion(21)) {
					modifierObject.addProperty("uuid", modifier.getUniqueId().toString());
				}
				modifiersJson.add(modifierObject);
			}

			attributesJson.add(attribute.name(), modifiersJson);
		}

		return attributesJson;
	}

	@Override
	public JsonItemStack applyItemAttributes(JsonItemStack itemStack, JsonObject attributes){
		ItemMeta meta = itemStack.getItemMeta();
		Set<Map.Entry<String, JsonElement>> entries = attributes.entrySet();

		for (Map.Entry<String, JsonElement> attributeEntry : entries){
			Attribute attribute = Attribute.valueOf(attributeEntry.getKey());

			for (JsonElement jsonElement : attributeEntry.getValue().getAsJsonArray()) {
				JsonObject modifier = jsonElement.getAsJsonObject();

				String name = modifier.get("name").getAsString();
				double amount = modifier.get("amount").getAsDouble();
				String operation = modifier.get("operation").getAsString();
				EquipmentSlot slot = null;

				if (modifier.has("slot")){
					slot = EquipmentSlot.valueOf(modifier.get("slot").getAsString());
				}

				final UUID uuid;
				if (modifier.has("uuid")) {
					uuid = UUID.fromString(modifier.get("uuid").getAsString());
				} else {
					uuid = UUID.randomUUID();
				}

				meta.addAttributeModifier(attribute, new AttributeModifier(
					uuid,
					name,
					amount,
					AttributeModifier.Operation.valueOf(operation),
					slot
				));
			}
		}
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	@Override
	public String getEnchantmentKey(Enchantment enchantment){
		return enchantment.getKey().toString();
	}

	@Nullable
	@Override
	public Enchantment getEnchantmentFromKey(String key){
		Enchantment enchantment = Enchantment.getByName(key);

		if (enchantment != null){
			LOGGER.warning("Using old deprecated enchantment names, replace: " + key + " with " + enchantment.getKey().toString());
			return enchantment;
		}

		NamespacedKey namespace;

		try{
			namespace = namespacedKeyFromString(key);
		} catch (IllegalArgumentException ignored) {
			return null;
		}

		return Enchantment.getByKey(namespace);
	}

	private NamespacedKey namespacedKeyFromString(String string) {
        String[] components = string.split(":", 3);
        if (components.length > 2) {
            return null;
        }

		// Key without namespace, use minecraft as default namespace
        if (components.length == 1) {
			String key = components[0];
			if (key.isEmpty()) {
				return null;
			}
            return NamespacedKey.minecraft(key);
        }

		// Namespace with key
        String namespace = components[0];
		String key = components[1];
        if (namespace.isEmpty()) {
            return NamespacedKey.minecraft(key);
        }
		if (key.isEmpty()) {
			return null;
		}

        return new NamespacedKey(namespace, key);
	}

	@Override
	public void setEntityAI(LivingEntity entity, boolean b){
		entity.setAI(b);
	}

	@Override
	public List<Material> getItemList(){
		List<Material> items = new ArrayList<>();

		for (Material material : Material.values()){
			if (material.isItem() && !isAir(material)) {
				items.add(material);
			}
		}

		return items;
	}

	@Nullable
	@Override
	public JsonArray getSuspiciousStewEffects(ItemMeta meta){
		return null;
	}

	@Override
	public ItemMeta applySuspiciousStewEffects(ItemMeta meta, JsonArray effects) throws ParseException{
		return meta;
	}

	@Override
	public void setItemUnbreakable(ItemMeta meta, boolean b){
		meta.setUnbreakable(b);
	}

	@Override
	public boolean getItemUnbreakable(ItemMeta meta) {
		return meta.isUnbreakable();
	}

	@Override
	public boolean isAir(Material material) {
		// Cave air and void air was added in 1.13, but
		// Material#isAir was not introduced until 1.14
		switch (material) {
			case AIR:
			case CAVE_AIR:
			case VOID_AIR: return true;
			default: return false;
		}
	}

	@Override
	public boolean leavesIsPersistent(Block leaves) {
		Leaves leavesData = (Leaves) leaves.getBlockData();
		return leavesData.isPersistent();
	}

}
