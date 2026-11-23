package com.gmail.val59000mc.utils.packets;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OptionalPacketClassLoadingTest {

	/** Loads the real facade/bridges while hiding both optional packet libraries. */
	private static class WithoutPacketPlugins extends ClassLoader {
		WithoutPacketPlugins() {
			super(OptionalPacketClassLoadingTest.class.getClassLoader());
		}

		@Override
		protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
			synchronized (getClassLoadingLock(name)) {
				if (name.startsWith("com.comphenix.") || name.startsWith("com.github.retrooper.packetevents.")) {
					throw new ClassNotFoundException(name);
				}
				if (name.equals("com.gmail.val59000mc.utils.ProtocolUtils")
						|| name.startsWith("com.gmail.val59000mc.utils.packets.")) {
					Class<?> loaded = findLoadedClass(name);
					if (loaded == null) {
						try (var source = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
							if (source == null) {
								throw new ClassNotFoundException(name);
								}
							byte[] bytes = source.readAllBytes();
							loaded = defineClass(name, bytes, 0, bytes.length);
						} catch (IOException e) {
							throw new ClassNotFoundException(name, e);
						}
					}
					if (resolve) {
						resolveClass(loaded);
					}
					return loaded;
				}
				return super.loadClass(name, resolve);
			}
		}
	}

	@Test
	void neitherLibraryIsRequiredToLoadFacadeOrDisablePlugin() throws Exception {
		Class<?> facade = Class.forName("com.gmail.val59000mc.utils.ProtocolUtils", true, new WithoutPacketPlugins());
		facade.getMethod("initializePacketSupport", boolean.class, boolean.class).invoke(null, false, false);
		assertEquals(false, facade.getMethod("hasNickNameSupport").invoke(null));
		facade.getMethod("shutdownPacketSupport").invoke(null);
	}

	@Test
	void missingApiClassesDoNotEscapeOptionalIntegration() throws Exception {
		Class<?> facade = Class.forName("com.gmail.val59000mc.utils.ProtocolUtils", true, new WithoutPacketPlugins());
		facade.getMethod("initializePacketSupport", boolean.class, boolean.class).invoke(null, true, true);
		assertEquals(false, facade.getMethod("hasNickNameSupport").invoke(null));
		facade.getMethod("shutdownPacketSupport").invoke(null);
	}
}
