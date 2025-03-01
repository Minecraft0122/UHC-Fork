package com.gmail.val59000mc.listeners;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.configuration.LootConfiguration;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.utils.UniversalMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class BlockListener implements Listener{

	private final PlayerManager playerManager;
	private final MainConfig configuration;
	private final Map<Material, LootConfiguration<Material>> blockLoots;
	private final int maxBuildingHeight;

	public BlockListener(GameManager gameManager){
		playerManager = gameManager.getPlayerManager();
		configuration = gameManager.getConfig();
		blockLoots = configuration.get(MainConfig.ENABLE_BLOCK_LOOT) ? configuration.get(MainConfig.BLOCK_LOOT) : new HashMap<>();
		maxBuildingHeight = configuration.get(MainConfig.MAX_BUILDING_HEIGHT);
	}

	// Low priority in order to override custom block drop scenarios
	@EventHandler(priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent event){
		handleFrozenPlayers(event);
		handleBlockLoot(event);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		handleFrozenPlayers(event);
		handleMaxBuildingHeight(event);
	}

	private void handleMaxBuildingHeight(BlockPlaceEvent e){
		if (e.isCancelled()) {
			return;
		}

		if (maxBuildingHeight < 0 || e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

		if (e.getBlock().getY() > maxBuildingHeight){
			e.setCancelled(true);
			e.getPlayer().sendMessage(Lang.PLAYERS_BUILD_HEIGHT);
		}
	}

	private void handleBlockLoot(BlockBreakEvent event){
		if (event.isCancelled()) {
			return;
		}

		Material material = event.getBlock().getType();
		if(blockLoots.containsKey(material)){
			LootConfiguration<Material> lootConfig = blockLoots.get(material);
			Location loc = event.getBlock().getLocation().add(.5,.5,.5);

			event.getBlock().setType(Material.AIR);

			lootConfig.getLoot().forEach(item -> loc.getWorld().dropItem(loc, item.rollStack()));

			if (lootConfig.getAddXp() > 0) {
				UhcItems.spawnExtraXp(loc, lootConfig.getAddXp());
			}
		}
	}

	// Low priority so that extra apples can still drop in custom block drop scenarios
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerLeavesBreak(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}

		if (!UniversalMaterial.isLeaves(e.getBlock().getType())) {
			return;
		}

		final ItemStack itemInMainHand = e.getPlayer().getItemInHand();
		if (itemInMainHand.getType() == Material.SHEARS && !configuration.get(MainConfig.APPLE_DROPS_FROM_SHEARING)) {
			return;
		}
		if (itemInMainHand.hasItemMeta() && itemInMainHand.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
			return;
		}

		handleAppleDrops(e);
	}

	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e) {
		if (e.isCancelled()) {
			return;
		}
		handleAppleDrops(e);
	}

	private void handleFrozenPlayers(BlockBreakEvent e){
		if (e.isCancelled()) {
			return;
		}

		UhcPlayer uhcPlayer = playerManager.getUhcPlayer(e.getPlayer());
		if (uhcPlayer.isFrozen()){
			e.setCancelled(true);
		}
	}

	private void handleFrozenPlayers(BlockPlaceEvent e){
		if (e.isCancelled()) {
			return;
		}

		UhcPlayer uhcPlayer = playerManager.getUhcPlayer(e.getPlayer());
		if (uhcPlayer.isFrozen()){
			e.setCancelled(true);
		}
	}

	private void handleAppleDrops(BlockEvent e) {
		final Block block = e.getBlock();
		final boolean shouldDropExtraApple = UniversalMaterial.isAppleLeaves(block) || configuration.get(MainConfig.APPLE_DROPS_FROM_ALL_TREES);

		if (!shouldDropExtraApple) {
			return;
		}

		final double dropPercentage = configuration.get(MainConfig.APPLE_DROP_PERCENTAGE);
		if (dropPercentage <= 0) {
			return;
		}

		final double random = ThreadLocalRandom.current().nextDouble(100);
		if (random < dropPercentage) {
			// Add apple to drops
			Bukkit.getScheduler().runTask(UhcCore.getPlugin(),
				() -> block.getWorld().dropItem(block.getLocation().add(.5, .5, .5),
			new ItemStack(Material.APPLE)));
		}
	}

}
