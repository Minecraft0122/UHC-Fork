package com.gmail.val59000mc.utils.packets;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/** Selects exactly one optional packet backend and owns its listener lifecycle. */
public final class NicknamePacketSupport {

	public enum Mode {
		AUTO("auto"),
		PACKET_EVENTS("PacketEvents"),
		PROTOCOL_LIB("ProtocolLib");

		private final String configValue;

		Mode(String configValue) {
			this.configValue = configValue;
		}

		public String getConfigValue() {
			return configValue;
		}

		public boolean allowsPacketEvents() {
			return this != PROTOCOL_LIB;
		}

		public boolean allowsProtocolLib() {
			return this != PACKET_EVENTS;
		}

		public static Mode fromConfig(String value) {
			if (value == null) {
				return AUTO;
			}
			for (Mode mode : values()) {
				if (mode.configValue.equalsIgnoreCase(value.trim())) {
					return mode;
				}
			}
			throw new IllegalArgumentException("Expected auto, PacketEvents or ProtocolLib");
		}
	}

	private final Supplier<Runnable> packetEvents;
	private final Supplier<Runnable> protocolLib;
	private final BiConsumer<String, Throwable> onFailure;
	private Runnable unregister;
	private String backend;
	private Mode mode;

	public NicknamePacketSupport(Supplier<Runnable> packetEvents, Supplier<Runnable> protocolLib,
			BiConsumer<String, Throwable> onFailure) {
		this.packetEvents = packetEvents;
		this.protocolLib = protocolLib;
		this.onFailure = onFailure;
	}

	public void start(boolean packetEventsEnabled, boolean protocolLibEnabled) {
		start(Mode.AUTO, packetEventsEnabled, protocolLibEnabled);
	}

	public void start(Mode selectedMode, boolean packetEventsEnabled, boolean protocolLibEnabled) {
		java.util.Objects.requireNonNull(selectedMode, "selectedMode");
		if (mode != selectedMode) {
			stop();
			mode = selectedMode;
		}
		if (isAvailable()) {
			return;
		}
		if (selectedMode.allowsPacketEvents() && packetEventsEnabled && tryRegister("PacketEvents", packetEvents)) {
			return;
		}
		if (selectedMode.allowsProtocolLib() && protocolLibEnabled) {
			tryRegister("ProtocolLib", protocolLib);
		}
	}

	private boolean tryRegister(String name, Supplier<Runnable> register) {
		try {
			Runnable cleanup = register.get();
			if (cleanup == null) {
				throw new IllegalStateException("Packet backend did not return a listener cleanup");
			}
			unregister = cleanup;
			backend = name;
			return true;
		} catch (RuntimeException | LinkageError e) {
			onFailure.accept(name, e);
			return false;
		}
	}

	public boolean isAvailable() {
		return unregister != null;
	}

	public String getBackend() {
		return backend;
	}

	public void stop() {
		Runnable cleanup = unregister;
		String name = backend;
		unregister = null;
		backend = null;
		mode = null;
		if (cleanup != null) {
			try {
				cleanup.run();
			} catch (RuntimeException | LinkageError e) {
				onFailure.accept(name, e);
			}
		}
	}
}
