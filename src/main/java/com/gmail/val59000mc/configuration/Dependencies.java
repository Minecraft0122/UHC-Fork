package com.gmail.val59000mc.configuration;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import com.gmail.val59000mc.utils.ProtocolUtils;
import com.gmail.val59000mc.utils.packets.NicknamePacketSupport.Mode;

public class Dependencies {

	private static final Logger LOGGER = Logger.getLogger(Dependencies.class.getCanonicalName());

	// dependencies
	private static boolean worldEditLoaded;
	private static boolean vaultLoaded;
	private static boolean protocolLibLoaded;

	public static void loadPacketSupport() {
		loadPacketSupport("auto");
	}

	public static void loadPacketSupport(String configuredBackend) {
		Mode mode;
		try {
			mode = Mode.fromConfig(configuredBackend);
		} catch (IllegalArgumentException e) {
			LOGGER.warning("Invalid packet-backend '" + configuredBackend
					+ "'; expected auto, PacketEvents or ProtocolLib. Using auto.");
			mode = Mode.AUTO;
		}
		// Do not inspect or load a backend excluded by the administrator's choice.
		ProtocolUtils.initializePacketSupport(mode, mode.allowsPacketEvents()
				&& Bukkit.getPluginManager().isPluginEnabled("packetevents"), false);
		if (!ProtocolUtils.hasNickNameSupport() && mode.allowsProtocolLib()) {
			loadProtocolLib();
			ProtocolUtils.initializePacketSupport(mode, false, protocolLibLoaded);
		}
		if (!ProtocolUtils.hasNickNameSupport()) {
			LOGGER.warning("Nickname scenarios unavailable with packet-backend=" + mode.getConfigValue()
					+ "; check the selected plugin is installed, enabled and compatible."
					+ (mode == Mode.AUTO ? "" : " Automatic fallback is disabled in this mode."));
		}
	}

	public static boolean getNicknameSupportLoaded() {
		return ProtocolUtils.hasNickNameSupport();
	}

	public static void loadWorldEdit() {
		Plugin wePlugin = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if(wePlugin == null || !wePlugin.isEnabled() || !wePlugin.getClass().getName().equals("com.sk89q.worldedit.bukkit.WorldEditPlugin")) {
			LOGGER.warning("WorldEdit plugin not found, there will be no support of schematics.");
			worldEditLoaded = false;
		}else {
			LOGGER.info("Hooked with WorldEdit plugin.");
			worldEditLoaded = true;
		}
	}

	public static void loadVault(){
		Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
		if(vault == null || !vault.isEnabled() || !vault.getClass().getName().equals("net.milkbowl.vault.Vault")) {
			LOGGER.warning("Vault plugin not found, there will be no support of economy rewards.");
			vaultLoaded = false;
			return;
		}

		LOGGER.info("Hooked with Vault plugin.");
		vaultLoaded = true;

		VaultManager.setupEconomy();
	}

	public static void loadProtocolLib(){
		Plugin protocolLib = Bukkit.getPluginManager().getPlugin("ProtocolLib");
		if(protocolLib == null || !protocolLib.isEnabled() || !protocolLib.getClass().getName().equals("com.comphenix.protocol.ProtocolLib")) {
			LOGGER.warning("ProtocolLib plugin not found");
			protocolLibLoaded = false;
			return;
		}

		Matcher version = Pattern.compile("^(\\d+)\\.(\\d+)").matcher(protocolLib.getDescription().getVersion());
		if (!version.find() || Integer.parseInt(version.group(1)) < 5
				|| (Integer.parseInt(version.group(1)) == 5 && Integer.parseInt(version.group(2)) < 4)) {
			LOGGER.warning("ProtocolLib 5.4.0 or newer is required for nickname support; integration disabled");
			protocolLibLoaded = false;
			return;
		}

		LOGGER.info("Hooked with ProtocolLib plugin");
		protocolLibLoaded = true;
	}

	public static boolean getWorldEditLoaded() {
		return worldEditLoaded;
	}

	public static boolean getVaultLoaded() {
		return vaultLoaded;
	}

	public static boolean getProtocolLibLoaded(){
		return protocolLibLoaded;
	}

}
