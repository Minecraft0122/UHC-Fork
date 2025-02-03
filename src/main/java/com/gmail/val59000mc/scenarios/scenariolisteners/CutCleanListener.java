package com.gmail.val59000mc.scenarios.scenariolisteners;

import java.util.Iterator;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.scenarios.Option;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.OreType;
import com.gmail.val59000mc.utils.UniversalMaterial;

import io.papermc.lib.PaperLib;

public class CutCleanListener extends ScenarioListener{

	private final ItemStack lapis;

	@Option(key = "unlimited-lapis")
	private boolean unlimitedLapis = true;
	@Option(key = "check-correct-tool")
	private boolean checkTool = false;

	public CutCleanListener(){
		lapis = UniversalMaterial.LAPIS_LAZULI.getStack(64);
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		// Hmm, this means that Donkey/Mule chest drops will smelt too...
		// Then again, not the biggest issue, we can just say it's "intended".
		for(int i = 0 ; i < e.getDrops().size(); i++) {
			// Cloned because we may end up having to mutate it, see below
			final ItemStack drop = e.getDrops().get(i).clone();
			// Note: On modern Minecraft versions, we could probably use Server#craftItem,
			// but it doesn't exist on older game versions such as 1.8.8.
			for (Iterator<Recipe> recipes = Bukkit.recipeIterator(); recipes.hasNext();) {
				final Recipe recipe = recipes.next();
				if (recipe instanceof FurnaceRecipe) {
					// Note: getInputChoice would be more future-proof, but it doesn't exist on
					// older Minecraft versions such as 1.8.8. Should be fine to ignore it for now.
					final ItemStack smeltInput = ((FurnaceRecipe) recipe).getInput();
					// Note	: On older game versions such as 1.8.8, the recipe input ItemStack
					// may have a damage value of 32767, i.e. Short.MAX_VALUE (for a special reason),
					// so ItemStack#isSimilar will always return false.
					// For reference, see: https://www.spigotmc.org/threads/malformed-itemstack.60990/#post-677215
					if (smeltInput.getDurability() == Short.MAX_VALUE) {
						drop.setDurability(Short.MAX_VALUE);
					}
					if (smeltInput.isSimilar(drop)) {
						final ItemStack smeltResult = recipe.getResult();
						smeltResult.setAmount(drop.getAmount());
						e.getDrops().set(i, smeltResult);
					}
				}
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if (e.isCancelled()) {
			return;
		}

		final Player player = e.getPlayer();
		final Block block = e.getBlock();

		if (getScenarioManager().getActiveBlockDropScenario(player, block) != Scenario.CUTCLEAN) {
			return;
		}

		Material tool = player.getItemInHand().getType();
		Location loc = block.getLocation().add(0.5, 0, 0.5);
		Material type = block.getType();
		ItemStack drop = null;

		Optional<OreType> oreType = OreType.valueOf(type);

		if (
			oreType.isPresent() &&
			oreType.get().needsSmelting() &&
			(!checkTool || oreType.get().isCorrectTool(tool))
		) {
			int xp = oreType.get().getXpPerBlock();
			int count = 1;

			if (OreType.isGold(type) && isEnabled(Scenario.DOUBLE_GOLD)) {
				count *= 2;
			}

			drop = oreType.get().getDrop().getStack(count);
			UhcItems.spawnExtraXp(loc,xp);
		}

		if (type == Material.SAND) {
			drop = new ItemStack(Material.GLASS);
		} else if (type == Material.GRAVEL) {
			drop = new ItemStack(Material.FLINT);
		}

		if (drop != null) {
			block.setType(Material.AIR);
			loc.getWorld().dropItem(loc, drop);
		}
	}

	@EventHandler
	public void openInventoryEvent(InventoryOpenEvent e){
		if (e.isCancelled()) {
			return;
		}

		if (!unlimitedLapis) return;

		if (e.getInventory() instanceof EnchantingInventory){
			e.getInventory().setItem(1, lapis);
		}
	}

	@EventHandler
	public void closeInventoryEvent(InventoryCloseEvent e){
		if (!unlimitedLapis) return;

		if (e.getInventory() instanceof EnchantingInventory){
			e.getInventory().setItem(1, null);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if (e.isCancelled()) {
			return;
		}

		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		if (!unlimitedLapis) return;
		if (inv == null || item == null) return;

		if (inv instanceof EnchantingInventory){
			// Lapis slot was introduced in Minecraft 1.8. For raw/protocol slot numbers,
			// see https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Inventory
			if (PaperLib.isVersion(8) && e.getRawSlot() == 1) {
				e.setCancelled(true);
			}
		}
	}

}
