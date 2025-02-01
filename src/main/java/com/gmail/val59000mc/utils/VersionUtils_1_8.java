package com.gmail.val59000mc.utils;

import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.maploader.MapLoader;
import com.gmail.val59000mc.players.UhcPlayer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.papermc.lib.PaperLib;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public class VersionUtils_1_8 extends VersionUtils{

	private static final Logger LOGGER = Logger.getLogger(VersionUtils_1_8.class.getCanonicalName());

	@Override
	public ShapedRecipe createShapedRecipe(ItemStack craft, String craftKey) {
		return new ShapedRecipe(craft);
	}

	@Override
	public ItemStack createPlayerSkull(String name, UUID uuid) {
		ItemStack item = UniversalMaterial.PLAYER_HEAD_ITEM.getStack();
		SkullMeta im = (SkullMeta) item.getItemMeta();
		im.setOwner(name);
		item.setItemMeta(im);
		return item;
	}

	@Override
	public void setSkullOwner(Skull skull, UhcPlayer player) {
		skull.setOwner(player.getName());
	}

	@Override
	public Objective registerNewObjective(Scoreboard scoreboard, String name, String criteria, String displayName, String renderType) {
		final Objective newObjective = scoreboard.registerNewObjective(name, criteria);
		newObjective.setDisplayName(displayName);
		return newObjective;
	}

	@Override
	public void setPlayerMaxHealth(Player player, double maxHealth) {
		player.setMaxHealth(maxHealth);
	}

	@Override
	public void setGameRuleValue(World world, String gameRule, Object value) {
		world.setGameRuleValue(gameRule, value.toString());
	}

	@Override
	public boolean hasEye(Block block) {
		return block.getData() > 3;
	}

	@Override
	public void setEye(Block block, boolean eye){
		byte data = block.getData();
		if (eye && data < 4){
			data += 4;
		}else if (!eye && data > 3){
			data -= 4;
		}

		setBlockData(block, data);
	}

	@Override
	public void setEndPortalFrameOrientation(Block block, BlockFace blockFace){
		byte data = -1;
		switch (blockFace){
			case NORTH:
				data = 2;
				break;
			case EAST:
				data = 3;
				break;
			case SOUTH:
				data = 0;
				break;
			case WEST:
				data = 1;
				break;
		}

		setBlockData(block, data);
	}

	private void setBlockData(Block block, byte data){
		try {
			Method setData = NMSUtils.getMethod(Block.class, "setData",1);
			setData.invoke(block, data);
		} catch (ReflectiveOperationException ex) {
			LOGGER.log(Level.WARNING, "Unable to set block data", ex);
		}
	}

	@Override
	public void setTeamNameTagVisibility(Team team, boolean value){
		team.setNameTagVisibility(value?NameTagVisibility.ALWAYS:NameTagVisibility.NEVER);
	}

	@Override
	public void setChestName(Chest chest, String name){
		try {
			Class craftChest = NMSUtils.getNMSClass("block.CraftChest");
			Method getTileEntity = NMSUtils.getMethod(craftChest, "getTileEntity");
			Object tileChest = getTileEntity.invoke(chest);
			Method a = NMSUtils.getMethod(tileChest.getClass(), "a", String.class);
			a.invoke(tileChest, name);
		} catch (ReflectiveOperationException e) {
			// TODO: find a way to change the chest name on other versions up to 1.11
			LOGGER.log(Level.WARNING, "Failed to rename chest", e);
		}
	}

	@Override
	public JsonObject getBasePotionEffect(PotionMeta potionMeta){
		return null;
	}

	@Override
	public PotionMeta setBasePotionEffect(PotionMeta potionMeta, PotionData potionData){
		return potionMeta;
	}

	@Nullable
	@Override
	public Color getPotionColor(PotionMeta potionMeta){
		return null;
	}

	@Override
	public PotionMeta setPotionColor(PotionMeta potionMeta, Color color){
		return potionMeta;
	}

	@Override
	public void setChestSide(Chest chest, boolean left) {
		// Not needed on 1.8
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
		if (PaperLib.isVersion(9)) {
			return null;
		}

		// The following code is tested to work on Minecraft 1.8.8
		try {
			// Get the NMS ItemStack by calling CraftItemStack.asNMSCopy(ItemStack). Note: The first argument to Method#invoke is null because it is a static method
			Object /* net.minecraft.server.ItemStack */ nmsItemStack = NMSUtils.getNMSClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
			// Get NBTTagCompound through NMSItemStack.getTag()
			Object /* NBTTagCompound */ nbtTag = NMSUtils.getMethod(nmsItemStack.getClass(), "getTag").invoke(nmsItemStack);

			// Check if the item has previous data, if not then it won't have attribute modifiers therefore return null
			if (nbtTag == null) return null;

			// Get NBTList of attribute modifiers with NBTTagCompound.getList("AttributeModifiers")
			Object /* NBTList */ attributeModifiers = NMSUtils.getMethod(nbtTag.getClass(), "getList").invoke(nbtTag, "AttributeModifiers", 10);
			int length = (int) NMSUtils.getMethod(attributeModifiers.getClass(), "size").invoke(attributeModifiers);

			// Ensure there is actually attribute modifiers to check for
			if (length == 0) return null;

			JsonObject attributesJson = new JsonObject();

			// All AttributeModifiers on the item. String is the name of the Attribute while JsonArray is all AttributeModifiers that are of that Attribute type
			Map<String, JsonArray> jsonAttributeModifiers = new HashMap<>();

			// Loop through each AttributeModifier
			for (int i = 0; i < length; i++) {
				JsonObject jsonModifier = new JsonObject();
				// NBTTagCompound using NBTTagList#get(int)
				Object /* NBTTagCompound */ attributeModifier = NMSUtils.getMethod(attributeModifiers.getClass(), "get").invoke(attributeModifiers, i);

				// Get the method: NBTTagCompound#get
				Method /* NBTTagCompound#get() */ getterMethod = NMSUtils.getMethod(attributeModifier.getClass(), "get");

				// Get the AttributeName. E.g. NBTTagCompound.get("AttributeName")
				Object /* NBTTagString */ attributeName = getterMethod.invoke(attributeModifier, "AttributeName");
				// a_ method is way of obtaining actual String object from NBTTagString
				String attributeNameValue = (String) NMSUtils.getMethod(attributeName.getClass(), "a_").invoke(attributeName);


				// Get the Name
				Object /* NBTTagString */ name = getterMethod.invoke(attributeModifier, "Name");
				// a_ method is way of obtaining actual String object from NBTTagString
				String nameValue = (String) NMSUtils.getMethod(name.getClass(), "a_").invoke(name);

				// Get the Amount
				Object /* NBTTagFloat */ amount = getterMethod.invoke(attributeModifier, "Amount");
				// h method is way of obtaining actual float from NBTTagFloat
				float amountValue = (float) NMSUtils.getMethod(amount.getClass(), "h").invoke(amount);

				// Get the Operation used
				Object /* NBTTagInt */ operation = getterMethod.invoke(attributeModifier, "Operation");
				// d method is way of obtaining actual int from NBTTagInt
				int operationValue = (int) NMSUtils.getMethod(operation.getClass(), "d").invoke(operation);

				// Get the UUIDLeast used
				Object /* NBTTagLong */ uuidLeast = getterMethod.invoke(attributeModifier, "UUIDLeast");
				// c method is way of obtaining actual long from NBTTagLong
				long uuidLeastValue = (long) NMSUtils.getMethod(uuidLeast.getClass(), "c").invoke(uuidLeast);

				// Get the UUIDMost used
				Object /* NBTTagLong */ uuidMost = getterMethod.invoke(attributeModifier, "UUIDMost");
				// c method is way of obtaining actual long from NBTTagLong
				long uuidMostValue = (long) NMSUtils.getMethod(uuidMost.getClass(), "c").invoke(uuidMost);

				UUID uuid = new UUID(uuidMostValue, uuidLeastValue);

				// Add all properties to the JsonObject
				jsonModifier.addProperty("name", nameValue);
				jsonModifier.addProperty("amount", amountValue);
				jsonModifier.addProperty("operation", operationValue);
				jsonModifier.addProperty("uuid", uuid.toString());

				// Add the AttributeModifier to the JsonArray that matches its Attribute.
				jsonAttributeModifiers.putIfAbsent(attributeNameValue, new JsonArray());
				JsonArray array = jsonAttributeModifiers.get(attributeNameValue);
				array.add(jsonModifier);
				jsonAttributeModifiers.put(attributeNameValue, array);
			}
			// Put the Map into the final JsonObject
			jsonAttributeModifiers.forEach(attributesJson::add);

			return attributesJson;
		} catch (ReflectiveOperationException ex) {
			LOGGER.log(Level.WARNING, "Unable to set item attributes", ex);
			return null;
		}
	}

	@Override
	public JsonItemStack applyItemAttributes(JsonItemStack itemStack, JsonObject attributes){
		if (PaperLib.isVersion(9)) {
			return itemStack;
		}

		// The following code is tested to work on Minecraft 1.8.8
		try {
			// Get the NMS ItemStack by calling CraftItemStack.asNMSCopy(ItemStack). Note: The first argument to Method#invoke is null because it is a static method
			Object /* net.minecraft.server.ItemStack */ nmsItemStack = NMSUtils.getNMSClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);

			// Attempt to get any existing NBTTagCompound from the NMSItemStack
			Object /* NBTTagCompound */ nbtTag = NMSUtils.getMethod(nmsItemStack.getClass(), "getTag").invoke(nmsItemStack);

			// If there is no pre-existing NBTTagCompound, then create a new one
			if (nbtTag == null)
				nbtTag = NMSUtils.getNMSClass("NBTTagCompound").getConstructor().newInstance();


			// Create a new list for the attribute modifiers
			Object /* NBTTagList */ attributeModifiers = NMSUtils.getNMSClass("NBTTagList").getConstructor().newInstance();

			// Loop through every entry for the attributes
			for (Map.Entry<String, JsonElement> entry : attributes.entrySet()) {
				String attributeName = entry.getKey();
				// Loop through every attribute modifier for the attribute
				for (JsonElement element : entry.getValue().getAsJsonArray()) {
					JsonObject modifier = element.getAsJsonObject();

					// Get relevant attribute modifier data from JsonObject
					String name = modifier.get("name").getAsString();
					float amount = modifier.get("amount").getAsFloat();
					int operation = modifier.get("operation").getAsInt();
					UUID uuid = UUID.fromString(modifier.get("uuid").getAsString());

					// Create a new NBTTagCompound Object to write these values into
					Object /* NBTTagCompound */ attributeModifier = NMSUtils.getNMSClass("NBTTagCompound").getConstructor().newInstance();

					// Get the method: NBTTagCompound#set
					Method /* NBTTagCompound#set() */ setterMethod = NMSUtils.getMethod(attributeModifier.getClass(), "set");

					// Set all the values for each AttributeModifier field.
					// E.g. NBTTagCompound#set("AttributeName", new NBTTagString(attributeName))
					setterMethod.invoke(attributeModifier, "AttributeName", NMSUtils.getNMSClass("NBTTagString").getConstructor(String.class).newInstance(attributeName));
					setterMethod.invoke(attributeModifier, "Name", NMSUtils.getNMSClass("NBTTagString").getConstructor(String.class).newInstance(name));
					setterMethod.invoke(attributeModifier, "Amount", NMSUtils.getNMSClass("NBTTagFloat").getConstructor(float.class).newInstance(amount));
					setterMethod.invoke(attributeModifier, "Operation", NMSUtils.getNMSClass("NBTTagInt").getConstructor(int.class).newInstance(operation));
					setterMethod.invoke(attributeModifier, "UUIDLeast", NMSUtils.getNMSClass("NBTTagLong").getConstructor(long.class).newInstance(uuid.getLeastSignificantBits()));
					setterMethod.invoke(attributeModifier, "UUIDMost", NMSUtils.getNMSClass("NBTTagLong").getConstructor(long.class).newInstance(uuid.getMostSignificantBits()));

					// Finally, add the NBTTagCompound object to the initial NBTTagList which stores all the Attribute Modifiers
					NMSUtils.getMethod(attributeModifiers.getClass(), "add").invoke(attributeModifiers, attributeModifier);
				}
			}

			// Adds the AttributeModifiers into the data of the item
			NMSUtils.getMethod(nbtTag.getClass(), "set").invoke(nbtTag, "AttributeModifiers", attributeModifiers);
			// Sets the item's data
			NMSUtils.getMethod(nmsItemStack.getClass(), "setTag").invoke(nmsItemStack, nbtTag);

			// Changes the NMS ItemStack object back into a Bukkit ItemStack object, so it can be outputted, using CraftItemStack.asCraftMirror()
			itemStack = new JsonItemStack((ItemStack) NMSUtils.getNMSClass("inventory.CraftItemStack").getMethod("asCraftMirror",  NMSUtils.getNMSClass("ItemStack")).invoke(null, nmsItemStack));
		} catch (ReflectiveOperationException ex) {
			LOGGER.log(Level.WARNING, "Unable to set item attributes", ex);
			return null;
		}

		return itemStack;
	}

	@Override
	public String getEnchantmentKey(Enchantment enchantment){
		return enchantment.getName();
	}

	@Nullable
	@Override
	public Enchantment getEnchantmentFromKey(String key){
		return Enchantment.getByName(key);
	}

	@Override
	public void setEntityAI(LivingEntity entity, boolean b){
		try{
			// Get Minecraft entity class
			Object mcEntity = NMSUtils.getHandle(entity);
			Method getNBTTag = NMSUtils.getMethod(mcEntity.getClass(), "getNBTTag");
			Class NBTTagCompound = NMSUtils.getNMSClass("NBTTagCompound");
			// Get NBT tag of zombie
			Object tag = getNBTTag.invoke(mcEntity);

			if (tag == null){
				tag = NBTTagCompound.newInstance();
			}

			// Methods to apply NBT data to the zombie
			Method c = NMSUtils.getMethod(mcEntity.getClass(), "c", NBTTagCompound);
			Method f = NMSUtils.getMethod(mcEntity.getClass(), "f", NBTTagCompound);

			// Method to set NBT values
			Method setInt = NMSUtils.getMethod(NBTTagCompound, "setInt", String.class, int.class);

			c.invoke(mcEntity, tag);
			setInt.invoke(tag, "NoAI", b?0:1);
			f.invoke(mcEntity, tag);
		} catch (ReflectiveOperationException ex) {
			// This will only work on 1.8 (Not 1.9-1.11, 0.5% of servers)
			LOGGER.log(Level.WARNING, "Unable to set entity AI", ex);
		}
	}

	@Override
	public List<Material> getItemList() {
		// Arrays.asList() returns a AbstractList where no objects can be removed from.
		return new ArrayList<>(Arrays.asList(Material.values()));
	}

	@Nullable
	@Override
	public JsonArray getSuspiciousStewEffects(ItemMeta meta){
		return null;
	}

	@Override
	public ItemMeta applySuspiciousStewEffects(ItemMeta meta, JsonArray effects){
		return meta;
	}

	@Override
	public void setItemUnbreakable(ItemMeta meta, boolean b) {
		try {
			Method spigot = NMSUtils.getMethod(meta.getClass(), "spigot");
			Object spigotInstance = spigot.invoke(meta);
			Method setUnbreakable = NMSUtils.getMethod(spigotInstance.getClass(), "setUnbreakable", boolean.class);
			setUnbreakable.invoke(spigotInstance, b);
		} catch (ReflectiveOperationException ex) {
			LOGGER.log(Level.WARNING, "Unable to make item unbreakable", ex);
		}
	}

	@Override
	public boolean getItemUnbreakable(ItemMeta meta) {
		try {
			Method spigot = NMSUtils.getMethod(meta.getClass(), "spigot");
			Object spigotInstance = spigot.invoke(meta);
			Method isUnbreakable = NMSUtils.getMethod(spigotInstance.getClass(), "isUnbreakable");
			return (boolean) isUnbreakable.invoke(spigotInstance);
		} catch (ReflectiveOperationException ex) {
			LOGGER.log(Level.WARNING, "Unable to get item unbreakable", ex);
			return false;
		}
	}

	@Override
	public boolean isAir(Material material) {
		return material == Material.AIR;
	}

	@Override
	public boolean leavesIsPersistent(Block leaves) {
		// See https://minecraft.wiki/w/Java_Edition_data_values/Pre-flattening#Leaves
		return (leaves.getData() & 0x4) != 0;
	}

}
