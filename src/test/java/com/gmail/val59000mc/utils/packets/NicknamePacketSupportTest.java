package com.gmail.val59000mc.utils.packets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.gmail.val59000mc.utils.packets.NicknamePacketSupport.Mode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class NicknamePacketSupportTest {

	private final List<String> calls = new ArrayList<>();

	@ParameterizedTest
	@CsvSource({
			"auto, true, true, PacketEvents", "auto, true, false, PacketEvents",
			"auto, false, true, ProtocolLib", "auto, false, false, none",
			"PacketEvents, true, true, PacketEvents", "PacketEvents, true, false, PacketEvents",
			"PacketEvents, false, true, none", "PacketEvents, false, false, none",
			"ProtocolLib, true, true, ProtocolLib", "ProtocolLib, false, true, ProtocolLib",
			"ProtocolLib, true, false, none", "ProtocolLib, false, false, none"
	})
	void honorsConfiguredBackendAcrossInstalledPluginCombinations(String configured, boolean packetEvents,
			boolean protocolLib, String expected) {
		var support = support(backend("PacketEvents"));
		support.start(Mode.fromConfig(configured), packetEvents, protocolLib);
		if (expected.equals("none")) {
			assertFalse(support.isAvailable());
			assertTrue(calls.isEmpty());
		} else {
			assertEquals(expected, support.getBackend());
			assertEquals(List.of("register " + expected), calls);
		}
	}

	@Test
	void forcedPacketEventsNeverFallsBackWhenBroken() {
		var support = support(() -> { throw new NoClassDefFoundError("PacketEvents"); });
		support.start(Mode.PACKET_EVENTS, true, true);
		assertFalse(support.isAvailable());
		assertEquals(List.of("failed PacketEvents"), calls);
	}

	@Test
	void forcedProtocolLibNeverLoadsPacketEventsWhenBroken() {
		var support = new NicknamePacketSupport(backend("PacketEvents"),
				() -> { throw new NoSuchMethodError("ProtocolLib"); },
				(name, error) -> calls.add("failed " + name));
		support.start(Mode.PROTOCOL_LIB, true, true);
		assertFalse(support.isAvailable());
		assertEquals(List.of("failed ProtocolLib"), calls);
	}

	@Test
	void changingModeUnregistersPreviousBackendBeforeSelectingAnother() {
		var support = support(backend("PacketEvents"));
		support.start(Mode.AUTO, true, true);
		support.start(Mode.PROTOCOL_LIB, true, true);
		assertEquals("ProtocolLib", support.getBackend());
		support.start(Mode.PACKET_EVENTS, false, true);
		assertFalse(support.isAvailable());
		assertEquals(List.of("register PacketEvents", "unregister PacketEvents",
				"register ProtocolLib", "unregister ProtocolLib"), calls);
	}

	@Test
	void acceptsCaseInsensitiveValuesAndSurroundingWhitespace() {
		assertEquals(Mode.AUTO, Mode.fromConfig(" Auto "));
		assertEquals(Mode.PACKET_EVENTS, Mode.fromConfig(" packetevents "));
		assertEquals(Mode.PROTOCOL_LIB, Mode.fromConfig(" PROTOCOLLIB "));
		assertEquals(Mode.AUTO, Mode.fromConfig(null));
		assertThrows(IllegalArgumentException.class, () -> Mode.fromConfig("unknown"));
		assertThrows(IllegalArgumentException.class, () -> Mode.fromConfig(""));
	}

	private Supplier<Runnable> backend(String name) {
		return () -> {
			calls.add("register " + name);
			return () -> calls.add("unregister " + name);
		};
	}

	private NicknamePacketSupport support(Supplier<Runnable> packetEvents) {
		return new NicknamePacketSupport(packetEvents, backend("ProtocolLib"),
				(name, error) -> calls.add("failed " + name));
	}

	@Test
	void prefersPacketEventsWhenBothAreInstalled() {
		var support = support(backend("PacketEvents"));
		support.start(true, true);
		assertEquals("PacketEvents", support.getBackend());
		assertEquals(List.of("register PacketEvents"), calls);
	}

	@Test
	void packetEventsWorksWithoutProtocolLib() {
		var support = support(backend("PacketEvents"));
		support.start(true, false);
		assertTrue(support.isAvailable());
		assertEquals("PacketEvents", support.getBackend());
	}

	@Test
	void fallsBackWhenPacketEventsIsAbsent() {
		var support = support(backend("PacketEvents"));
		support.start(false, true);
		assertEquals("ProtocolLib", support.getBackend());
		assertEquals(List.of("register ProtocolLib"), calls);
	}

	@Test
	void fallsBackOnPacketEventsLinkageFailure() {
		var support = support(() -> { throw new NoClassDefFoundError("optional API"); });
		support.start(true, true);
		assertEquals("ProtocolLib", support.getBackend());
		assertEquals(List.of("failed PacketEvents", "register ProtocolLib"), calls);
	}

	@Test
	void fallsBackWhenPacketEventsIsNotReady() {
		var support = support(() -> { throw new IllegalStateException("not initialized"); });
		support.start(true, true);
		assertEquals("ProtocolLib", support.getBackend());
	}

	@Test
	void neitherInstalledDoesNotLoadEitherBackend() {
		var support = support(backend("PacketEvents"));
		support.start(false, false);
		assertFalse(support.isAvailable());
		assertTrue(calls.isEmpty());
	}

	@Test
	void bothBrokenLeaveSupportUnavailable() {
		var support = new NicknamePacketSupport(
				() -> { throw new NoSuchMethodError("PacketEvents"); },
				() -> { throw new NoSuchMethodError("ProtocolLib"); },
				(name, error) -> calls.add(name));
		support.start(true, true);
		assertFalse(support.isAvailable());
		assertEquals(List.of("PacketEvents", "ProtocolLib"), calls);
	}

	@Test
	void repeatedStartAndStopDoNotDuplicateListeners() {
		var support = support(backend("PacketEvents"));
		support.start(true, true);
		support.start(true, true);
		support.stop();
		support.stop();
		assertFalse(support.isAvailable());
		assertNull(support.getBackend());
		assertEquals(List.of("register PacketEvents", "unregister PacketEvents"), calls);
	}

	@Test
	void failedCleanupStillAllowsReinitialization() {
		var support = new NicknamePacketSupport(() -> () -> { throw new NoClassDefFoundError("disabled"); },
				backend("ProtocolLib"), (name, error) -> calls.add(name));
		support.start(true, false);
		support.stop();
		assertFalse(support.isAvailable());
		support.start(false, true);
		assertEquals("ProtocolLib", support.getBackend());
	}
}
