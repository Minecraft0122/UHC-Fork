package com.gmail.val59000mc.utils.packets;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.chat.RemoteChatSession;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate.PlayerInfo;
import org.junit.jupiter.api.Test;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PacketEventsNicknameListenerTest {

	@Test
	void changesOnlyNameAndPreservesMetadataAndTextures() {
		UUID uuid = UUID.randomUUID();
		var texture = new TextureProperty("textures", "skin", "signature");
		var profile = new UserProfile(uuid, "RealName", new ArrayList<>(List.of(texture)));
		var chatSession = new RemoteChatSession(UUID.randomUUID(), null);
		var original = new PlayerInfo(profile, false, 123, GameMode.SPECTATOR,
				Component.text("Existing tab name"), chatSession, 42, true);
		var renamed = PacketEventsNicknameListener.rename(original, id -> id.equals(uuid) ? "HiddenName" : null);
		assertNotSame(original, renamed);
		assertNotSame(profile, renamed.getGameProfile());
		assertEquals("RealName", profile.getName());
		assertEquals("HiddenName", renamed.getGameProfile().getName());
		assertEquals(uuid, renamed.getProfileId());
		assertEquals(original.isListed(), renamed.isListed());
		assertEquals(original.getLatency(), renamed.getLatency());
		assertEquals(original.getGameMode(), renamed.getGameMode());
		assertSame(original.getDisplayName(), renamed.getDisplayName());
		assertSame(original.getChatSession(), renamed.getChatSession());
		assertEquals(42, renamed.getListOrder());
		assertTrue(renamed.isShowHat());
		assertEquals(List.of(texture), renamed.getGameProfile().getTextureProperties());
		renamed.getGameProfile().getTextureProperties().clear();
		assertEquals(List.of(texture), original.getGameProfile().getTextureProperties());
	}

	@Test
	void leavesUnknownPlayersUnchanged() {
		var entry = new PlayerInfo(new UserProfile(UUID.randomUUID(), "RealName"));
		assertSame(entry, PacketEventsNicknameListener.rename(entry, uuid -> null));
		assertNull(PacketEventsNicknameListener.rename(null, uuid -> "HiddenName"));
	}
}
