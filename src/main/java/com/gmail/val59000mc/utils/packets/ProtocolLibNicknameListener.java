package com.gmail.val59000mc.utils.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.gmail.val59000mc.UhcCore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/** Optional compatibility fallback; never loaded when PacketEvents is selected. */
public final class ProtocolLibNicknameListener extends PacketAdapter {

	private final Function<UUID, String> nicknames;

	private ProtocolLibNicknameListener(Function<UUID, String> nicknames) {
		super(UhcCore.getPlugin(), PacketType.Play.Server.PLAYER_INFO);
		this.nicknames = nicknames;
	}

	public static Runnable register(Function<UUID, String> nicknames) {
		var manager = ProtocolLibrary.getProtocolManager();
		var listener = new ProtocolLibNicknameListener(nicknames);
		manager.addPacketListener(listener);
		return () -> manager.removePacketListener(listener);
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		if (event.isCancelled() || !event.getPacket().getPlayerInfoActions().read(0)
				.contains(EnumWrappers.PlayerInfoAction.ADD_PLAYER)) {
			return;
		}
		var packet = event.getPacket().shallowClone();
		List<PlayerInfoData> entries = new ArrayList<>();
		boolean changed = false;
		for (PlayerInfoData entry : packet.getPlayerInfoDataLists().read(1)) {
			String nickname = entry == null || entry.getProfile() == null
					? null : nicknames.apply(entry.getProfileId());
			if (nickname == null) {
				entries.add(entry);
				continue;
			}
			entries.add(new PlayerInfoData(entry.getProfileId(), entry.getLatency(), entry.isListed(),
					entry.getGameMode(), entry.getProfile().withName(nickname), entry.getDisplayName(),
					entry.isShowHat(), entry.getListOrder(), entry.getRemoteChatSessionData()));
			changed = true;
		}
		if (changed) {
			packet.getPlayerInfoDataLists().write(1, entries);
			event.setPacket(packet);
		}
	}
}
