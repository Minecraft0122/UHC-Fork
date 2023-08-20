package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.scenarios.Option;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TimebombListener extends ScenarioListener{

	@Option
	private long delay = 30;

	@Option(key = "explosion-power")
	private double explosionPower = 10;

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity().getPlayer();
		List<ItemStack> drops = new ArrayList<>(e.getDrops());
		e.getDrops().removeAll(e.getDrops());

		TimebombTask timebombTask = new TimebombTask(drops, p.getLocation().getBlock().getLocation(), p.getName(), delay, (float) explosionPower);
		Bukkit.getScheduler().scheduleSyncDelayedTask(UhcCore.getPlugin(), timebombTask,1L);
	}

	public static class TimebombTask implements Runnable{

		private ArmorStand armorStand;
		private Block block1, block2;
		private long timeLeft;
		private Location loc;
		private List<ItemStack> drops;
		private String name;
		private boolean spawned;
		private float explosionPower;

		public TimebombTask(List<ItemStack> drops, Location loc, String name, long delay, float explosionPower) {
			this.drops = drops;
			this.loc = loc;
			this.name = name;
			timeLeft = delay;
			spawned = false;
			this.explosionPower = explosionPower;
		}

		@Override
		public void run() {
			if (!spawned){
				spawnChest();
			}

			if (timeLeft > 0){
				armorStand.setCustomName(ChatColor.GOLD + "" + timeLeft);
				timeLeft--;
				Bukkit.getScheduler().scheduleSyncDelayedTask(UhcCore.getPlugin(),this,20L);
			}else{
				armorStand.remove();
				block1.setType(Material.AIR);
				block2.setType(Material.AIR);
				block1.getWorld().createExplosion(block1.getLocation(), explosionPower, false);
			}

		}

		private void spawnChest(){
			spawned = true;

			block1 = loc.getBlock();
			loc.add(-1, 0, 0);
			block2 = loc.getBlock();

			block1.setType(Material.CHEST);
			block2.setType(Material.CHEST);

			Chest chest1 = (Chest) block1.getState();
			Chest chest2 = (Chest) block2.getState();

			String chestName = Lang.SCENARIO_TIMEBOMB_CHEST.replace("%player%", name);
			VersionUtils.getVersionUtils().setChestName(chest1, chestName);
			VersionUtils.getVersionUtils().setChestName(chest2, chestName);

			// Make double chest for 1.13 and up
			VersionUtils.getVersionUtils().setChestSide(chest1, false);
			VersionUtils.getVersionUtils().setChestSide(chest2, true);

			for (int i = 0; i < drops.size(); i++) {
				ItemStack drop = drops.get(i);
				if (i < chest1.getInventory().getSize()) {
					chest1.getInventory().addItem(drop);
				} else {
					chest2.getInventory().addItem(drop);
				}
			}

			loc.add(1,-1,.5);

			armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
			armorStand.setCustomNameVisible(true);
			armorStand.setGravity(false);
			armorStand.setVisible(false);
			armorStand.setCustomName("");
		}
	}

}
