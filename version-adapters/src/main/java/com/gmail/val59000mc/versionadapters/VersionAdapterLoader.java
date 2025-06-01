package com.gmail.val59000mc.versionadapters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import com.gmail.val59000mc.versionadapters.adapters.ChunkyPreGenerator;
import com.gmail.val59000mc.versionadapters.adapters.GetWorldMinHeightAdapter;
import com.gmail.val59000mc.versionadapters.adapters.SetBiomeProviderAdapter;
import com.gmail.val59000mc.versionadapters.adapters.SetMaxStackSizeAdapter;
import com.gmail.val59000mc.versionadapters.adapters.SetTeamColorAdapter;

public class VersionAdapterLoader {

	private final Map<Class<?>, VersionAdapter> adapters;

	private VersionAdapterLoader(Map<Class<?>, VersionAdapter> adapters) {
		this.adapters = adapters;
	}

	public static VersionAdapterLoader loadAll(ClassLoader classLoader) {
		final HashMap<Class<?>, VersionAdapter> loadedAdapters = new HashMap<>();

		requireVersionAdapter(loadedAdapters, classLoader, SetMaxStackSizeAdapter.class, true);
		requireVersionAdapter(loadedAdapters, classLoader, SetBiomeProviderAdapter.class, true);
		requireVersionAdapter(loadedAdapters, classLoader, SetTeamColorAdapter.class, true);
		requireVersionAdapter(loadedAdapters, classLoader, GetWorldMinHeightAdapter.class, true);

		requireVersionAdapter(loadedAdapters, classLoader, ChunkyPreGenerator.class, false);

		return new VersionAdapterLoader(Collections.unmodifiableMap(loadedAdapters));
	}

	private static <T extends VersionAdapter> void requireVersionAdapter(Map<Class<?>, VersionAdapter> adapters, ClassLoader classLoader, Class<T> adapterClass, boolean required) {
		final Iterator<T> adapterIterator = ServiceLoader.load(adapterClass, classLoader).iterator();
		while (adapterIterator.hasNext()) {
			try {
				final T adapter = adapterIterator.next();
				if (adapter.isCompatible()) {
					adapters.put(adapterClass, adapter);
					return;
				}
			} catch (UnsupportedClassVersionError e) {
				// Ignore Commodore ASM errors on 1.13.2 and possibly other versions
				// Note that the exception is still logged to the console even if we catch it here
				continue;
			}
		}
		if (required) {
			throw new RuntimeException("Unable to find version adapter for class " + adapterClass.getCanonicalName());
		}
	}

	public <T extends VersionAdapter> T getVersionAdapter(Class<T> adapterClass) {
		@SuppressWarnings("unchecked")
		final T adapter = (T) adapters.get(adapterClass);
		return adapter;
	}

	public Map<Class<?>, VersionAdapter> getVersionAdapters() {
		return adapters;
	}

}
