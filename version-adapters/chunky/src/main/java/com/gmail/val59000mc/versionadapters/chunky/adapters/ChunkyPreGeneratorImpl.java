package com.gmail.val59000mc.versionadapters.chunky.adapters;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

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
			if (world.equals(e.world())) {
				generationComplete.complete(null);
			}
		});

		try {
			if (!chunky.startTask(world, "square", 0, 0, size, size, "region")
					&& !chunky.isRunning(world)) {
				// Chunky rejects a task when there is nothing left to generate (or
				// when the requested task cannot be created). Do not leave UHC in
				// the loading state forever in either case.
				Bukkit.getLogger().log(Level.WARNING,
						"Chunky did not start pre-generation for world " + world
								+ "; continuing with the existing world data");
				generationComplete.complete(null);
			}
		} catch (RuntimeException ex) {
			Bukkit.getLogger().log(Level.WARNING,
						"Unable to start Chunky pre-generation for world " + world
								+ "; continuing with the existing world data", ex);
			generationComplete.complete(null);
		}

		return generationComplete;
	}

}
