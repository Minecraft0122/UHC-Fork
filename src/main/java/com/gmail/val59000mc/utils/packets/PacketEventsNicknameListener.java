package com.gmail.val59000mc.utils.packets;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.util.PEVersion;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate.PlayerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/** Only loaded when the standalone PacketEvents plugin is enabled. */
public final class PacketEventsNicknameListener extends PacketListenerAbstract {

	private final Function<UUID, String> nicknames;

	private PacketEventsNicknameListener(Function<UUID, String> nicknames) {
		super(PacketListenerPriority.NORMAL);
		this.nicknames = nicknames;
	}

	public static Runnable register(Function<UUID, String> nicknames) {
		var api = PacketEvents.getAPI();
		if (api == null || !api.isInitialized() || api.isTerminated()) {
			throw new IllegalStateException("PacketEvents is not initialized");
		}
		if (api.getVersion().isOlderThan(new PEVersion(2, 14, 0))) {
			throw new IllegalStateException("PacketEvents 2.14.0 or newer is required for nickname support");
		}
		var manager = api.getEventManager();
		var listener = new PacketEventsNicknameListener(nicknames);
		manager.registerListener(listener);
		return () -> manager.unregisterListener(listener);
	}

	@Override
	public void onPacketSend(PacketSendEvent event) {
		if (event.isCancelled() || event.getPacketType() != PacketType.Play.Server.PLAYER_INFO_UPDATE) {
			return;
		}
		var packet = new WrapperPlayServerPlayerInfoUpdate(event);
		if (!packet.getActions().contains(WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER)) {
			return;
		}
		List<PlayerInfo> entries = new ArrayList<>(packet.getEntries().size());
		boolean changed = false;
		for (PlayerInfo entry : packet.getEntries()) {
			PlayerInfo replacement = rename(entry, nicknames);
			entries.add(replacement);
			changed |= replacement != entry;
		}
		if (changed) {
			packet.setEntries(entries);
			event.markForReEncode(true);
		}
	}

	static PlayerInfo rename(PlayerInfo entry, Function<UUID, String> nicknames) {
		if (entry == null || entry.getGameProfile() == null) {
			return entry;
		}
		UserProfile profile = entry.getGameProfile();
		String nickname = nicknames.apply(profile.getUUID());
		if (nickname == null) {
			return entry;
		}
		// Copy both objects: wrappers may be reused for another recipient by other plugins.
		PlayerInfo renamed = new PlayerInfo(entry);
		renamed.setGameProfile(new UserProfile(profile.getUUID(), nickname,
				new ArrayList<>(profile.getTextureProperties())));
		return renamed;
	}
}
