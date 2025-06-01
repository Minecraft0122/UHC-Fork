package com.gmail.val59000mc.versionadapters.chunky.adapters;

import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.popcraft.chunky.api.ChunkyAPI;

import com.gmail.val59000mc.versionadapters.adapters.ChunkyPreGenerator;
import com.google.auto.service.AutoService;

@AutoService(ChunkyPreGenerator.class)
public class ChunkyPreGeneratorImpl implements ChunkyPreGenerator {

	@Override
	public boolean isCompatible() {
		return Bukkit.getPluginManager().isPluginEnabled("Chunky") && Bukkit.getServer().getServicesManager().load(ChunkyAPI.class) != null;
	}

	@Override
	public CompletableFuture<Void> preGenerateChunks(String world, int size) {
		final CompletableFuture<Void> generationComplete = new CompletableFuture<>();
		final ChunkyAPI chunky = Bukkit.getServer().getServicesManager().load(ChunkyAPI.class);

		chunky.onGenerationComplete(e -> {
			if (e.world().equals(world)) {
				generationComplete.complete(null);
			}
		});
		chunky.startTask(world, "square", 0, 0, size, size, "region");

		return generationComplete;
	}

}
