package com.gmail.val59000mc;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gmail.val59000mc.api.UhcCoreApi;
import com.gmail.val59000mc.api.internal.UhcCoreApiImpl;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.utils.PluginForwardingHandler;
import com.gmail.val59000mc.versionadapters.VersionAdapterLoader;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.val59000mc.events.UHCGameStopEvent;

import net.zerodind.uhccore.nms.CreateNmsAdapterException;
import net.zerodind.uhccore.nms.NmsAdapter;
import net.zerodind.uhccore.nms.NmsAdapterFactory;

public class UhcCore extends JavaPlugin{

	private static final Logger LOGGER = Logger.getLogger(UhcCore.class.getCanonicalName());

	private static UhcCore pl;
	private static Optional<NmsAdapter> nmsAdapter = Optional.empty();
	private static VersionAdapterLoader versionAdapterLoader;
	private Logger forwardingLogger;
	private GameManager gameManager;
	private UhcCoreApiImpl api;

	@Override
	public void onEnable(){
		pl = this;
		forwardingLogger = PluginForwardingHandler.createForwardingLogger(this);
		gameManager = new GameManager();

		gameManager.loadConfig();
		loadNmsAdapter();
		versionAdapterLoader = VersionAdapterLoader.loadAll(getClassLoader());
		registerApi();
		if (gameManager.getConfig().get(MainConfig.ENABLE_UHC)) {
			Bukkit.getScheduler().runTaskLater(this, () -> gameManager.loadNewGame(), 1);
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				new UhcCorePlaceholderExpansion(pl).register();
			}
		} else {
			LOGGER.warning("NOTE: UHC is disabled, by the enable-uhc option in plugins/UhcCore/config.yml");
		}
	}

	@Override
	public void onDisable() {
		if (api != null) {
			if (gameManager != null && gameManager.isGameRunning()) {
				getServer().getPluginManager().callEvent(new UHCGameStopEvent(api, "Plugin disabled"));
			}
			api.releaseAllWeatherControls(true);
			HandlerList.unregisterAll(api);
			getServer().getServicesManager().unregister(UhcCoreApi.class, api);
			api = null;
		}

		pl = null;
		nmsAdapter = Optional.empty();
		versionAdapterLoader = null;
	}

	private void registerApi() {
		api = new UhcCoreApiImpl(this, gameManager);
		getServer().getServicesManager().register(UhcCoreApi.class, api, this, ServicePriority.Normal);
		getServer().getPluginManager().registerEvents(api, this);
	}

	private void loadNmsAdapter() {
		try {
			final NmsAdapter adapter = NmsAdapterFactory.create();
			LOGGER.config(() -> "Loaded NMS adapter: " + adapter.getClass().getName());
			nmsAdapter = Optional.of(adapter);
		} catch (CreateNmsAdapterException e) {
			LOGGER.log(Level.CONFIG, "Unable to create NMS adapter", e);
			nmsAdapter = Optional.empty();
		}
	}

	public static UhcCore getPlugin(){
		return pl;
	}

	public Logger getForwardingLogger() {
		return forwardingLogger;
	}

	public UhcCoreApi getApi() {
		return api;
	}

	public static Optional<NmsAdapter> getNmsAdapter() {
		return nmsAdapter;
	}

	public static VersionAdapterLoader getVersionAdapterLoader() {
		return versionAdapterLoader;
	}

}
