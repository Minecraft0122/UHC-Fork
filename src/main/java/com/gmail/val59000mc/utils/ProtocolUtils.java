package com.gmail.val59000mc.utils;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.utils.packets.NicknamePacketSupport;
import com.gmail.val59000mc.utils.packets.NicknamePacketSupport.Mode;
import com.gmail.val59000mc.utils.packets.PacketEventsNicknameListener;
import com.gmail.val59000mc.utils.packets.ProtocolLibNicknameListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProtocolUtils {

	private static final Logger LOGGER = Logger.getLogger(ProtocolUtils.class.getCanonicalName());
	// Packet callbacks can run on Netty threads; do not read Bukkit/game state there.
	private static final Map<UUID, String> NICKNAMES = new ConcurrentHashMap<>();
	private static final NicknamePacketSupport PACKET_SUPPORT = new NicknamePacketSupport(
			() -> PacketEventsNicknameListener.register(NICKNAMES::get),
			() -> ProtocolLibNicknameListener.register(NICKNAMES::get),
			(name, error) -> LOGGER.log(Level.WARNING, name + " nickname integration failed", error));

	public static void initializePacketSupport(boolean packetEventsEnabled, boolean protocolLibEnabled) {
		initializePacketSupport(Mode.AUTO, packetEventsEnabled, protocolLibEnabled);
	}

	public static void initializePacketSupport(Mode mode, boolean packetEventsEnabled, boolean protocolLibEnabled) {
		PACKET_SUPPORT.start(mode, packetEventsEnabled, protocolLibEnabled);
		if (PACKET_SUPPORT.isAvailable()) {
			LOGGER.info("Nickname packet backend: " + PACKET_SUPPORT.getBackend());
		}
	}

	public static boolean hasNickNameSupport() {
		return PACKET_SUPPORT.isAvailable();
	}

	public static void shutdownPacketSupport() {
		clearNickNames();
		PACKET_SUPPORT.stop();
	}

	public static void clearNickNames() {
		NICKNAMES.clear();
	}

	/** Changes the profile name; null resets it. Maximum length is 16 characters. */
	public static void setPlayerNickName(UhcPlayer uhcPlayer, String nickName) {
		uhcPlayer.setNickName(nickName);
		if (nickName == null) {
			NICKNAMES.remove(uhcPlayer.getUuid());
		} else {
			NICKNAMES.put(uhcPlayer.getUuid(), nickName);
		}
		try {
			updatePlayer(uhcPlayer.getPlayer());
		} catch (UhcPlayerNotOnlineException ignored) {
			// Reconnecting players receive the current nickname in their add-player packet.
		}
	}

	public static void setPlayerHeaderFooter(Player player, String header, String footer) {
		player.setPlayerListHeaderFooter(header, footer);
	}

	private static void updatePlayer(Player player) {
		for (Player viewer : player.getWorld().getPlayers()) {
			viewer.hidePlayer(player);
		}
		Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> {
			for (Player viewer : player.getWorld().getPlayers()) {
				viewer.showPlayer(player);
			}
		}, 1);
	}
}
