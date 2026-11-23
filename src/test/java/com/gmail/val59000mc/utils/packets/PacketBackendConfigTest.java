package com.gmail.val59000mc.utils.packets;

import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.utils.packets.NicknamePacketSupport.Mode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class PacketBackendConfigTest {

	@Test
	void existingConfigGetsAutoDefaultWithoutOverwritingOtherSettings() throws Exception {
		var config = new MainConfig();
		config.loadFromString("enable-uhc: false\nmin-players-to-start: 7\n");
		assertEquals("auto", config.get(MainConfig.PACKET_BACKEND));
		assertEquals("auto", config.getString("packet-backend"));
		assertFalse(config.getBoolean("enable-uhc"));
		assertEquals(7, config.getInt("min-players-to-start"));
	}

	@Test
	void bundledConfigContainsAutoDefault() throws Exception {
		try (var input = getClass().getResourceAsStream("/config.yml")) {
			assertNotNull(input);
			var config = new MainConfig();
			config.load(new InputStreamReader(input, StandardCharsets.UTF_8));
			assertEquals("auto", config.getString("packet-backend"));
			assertEquals(Mode.AUTO, Mode.fromConfig(config.get(MainConfig.PACKET_BACKEND)));
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"auto", "PacketEvents", "ProtocolLib"})
	void readsSelectedBackendFromYamlWithoutChangingIt(String selected) throws Exception {
		var config = new MainConfig();
		config.loadFromString("packet-backend: " + selected + "\n");
		assertEquals(selected, config.get(MainConfig.PACKET_BACKEND));
		assertEquals(Mode.fromConfig(selected), Mode.fromConfig(config.get(MainConfig.PACKET_BACKEND)));
		assertEquals(selected, config.getString("packet-backend"));
	}
}
