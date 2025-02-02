package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.customitems.CraftsManager;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.scenarios.Option;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.UniversalMaterial;
import io.papermc.lib.PaperLib;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class HasteyBoysListener extends ScenarioListener{
	@Option(key = "efficiency")
	private int efficiency = 3;

	@Option(key = "durability")
	private int durability = 1;

	@Option(key = "stone-tools")
	private boolean stone_tools = false;

	@Option(key = "allow-disenchanting")
	private boolean allow_disenchanting = false;

	@EventHandler
	public void onPlayerCraft(PrepareItemCraftEvent e) {
		ItemStack craftResult = e.getInventory().getResult();
		if (craftResult == null) {
			return;
		}

		// Don't apply hastey boy effects to custom crafted items.
		if (CraftsManager.isCraftItem(craftResult)){
			return;
		}

		if (stone_tools){
			if (craftResult.getType() == UniversalMaterial.WOODEN_SWORD.getType()){
				craftResult.setType(Material.STONE_SWORD);
			}else if (craftResult.getType() == UniversalMaterial.WOODEN_PICKAXE.getType()){
				craftResult.setType(Material.STONE_PICKAXE);
			}else if (craftResult.getType() == UniversalMaterial.WOODEN_SHOVEL.getType()){
				craftResult.setType(UniversalMaterial.STONE_SHOVEL.getType());
			}else if (craftResult.getType() == UniversalMaterial.WOODEN_AXE.getType()){
				craftResult.setType(Material.STONE_AXE);
			}else if (craftResult.getType() == UniversalMaterial.WOODEN_HOE.getType()){
				craftResult.setType(Material.STONE_HOE);
			}
		}

		// Try to enchant the item, give up if it's not an enchantable tool
		try {
			craftResult.addEnchantment(Enchantment.DIG_SPEED, efficiency);
			craftResult.addEnchantment(Enchantment.DURABILITY, durability);

			// Check if config option is on AND if the MC version is at least 1.14 (Grindstone were only introduced in 1.14)
			if (!allow_disenchanting && PaperLib.getMinecraftVersion() >= 14) {
				ItemMeta meta = craftResult.getItemMeta();
				meta.getPersistentDataContainer().set(new NamespacedKey(UhcCore.getPlugin(), "isHasteyBoysTool"), PersistentDataType.STRING, "true");
				craftResult.setItemMeta(meta);
			}
		} catch (IllegalArgumentException ignored) {}

	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.isCancelled()) return;
		if (allow_disenchanting || PaperLib.getMinecraftVersion() < 14) return; // If the config is not toggled or if the MC version is under 1.14 then exit
		if (e.getClickedInventory() == null || e.getView().getTopInventory().getType() != InventoryType.GRINDSTONE) return;

		ItemStack item = null;
		if (e.getCurrentItem() != null && e.isShiftClick())
			item = e.getCurrentItem();
		else if (e.getSlot() <= 1 && e.getClickedInventory().getType() == InventoryType.GRINDSTONE) item = e.getCursor();

		if (item == null || item.getType() == Material.AIR) return;

		if (checkHasteyBoysTool((Player) e.getWhoClicked(), item)) e.setCancelled(true);

	}

	@EventHandler
	public void onInvDrag(InventoryDragEvent e) {
		if (e.isCancelled()) return;
		if (allow_disenchanting || PaperLib.getMinecraftVersion() < 14) return; // If the config is not toggled or if the MC version is under 1.14 then exit
		if (e.getInventory().getType() != InventoryType.GRINDSTONE
				|| !(e.getRawSlots().contains(0) || e.getRawSlots().contains(1))) return; // Ensure the slot that is being clicked is within the grindstone input slots

		if (checkHasteyBoysTool((Player) e.getWhoClicked(), e.getOldCursor())) e.setCancelled(true);
	}


	private boolean checkHasteyBoysTool(Player player, ItemStack item) {
		ItemMeta meta = item.getItemMeta();

		// Check if the item has the Hastey Boys entry
		if (!meta.getPersistentDataContainer().has(new NamespacedKey(UhcCore.getPlugin(), "isHasteyBoysTool"), PersistentDataType.STRING)) return false;

		player.sendMessage(Lang.SCENARIO_HASTEY_BOYS_GRINDSTONE_ERROR);
		return true;
	}

}
