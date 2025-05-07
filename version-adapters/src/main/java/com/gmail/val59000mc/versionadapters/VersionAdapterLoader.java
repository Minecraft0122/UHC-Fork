package com.gmail.val59000mc.versionadapters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

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

		requireVersionAdapter(loadedAdapters, classLoader, SetMaxStackSizeAdapter.class);
		requireVersionAdapter(loadedAdapters, classLoader, SetBiomeProviderAdapter.class);
		requireVersionAdapter(loadedAdapters, classLoader, SetTeamColorAdapter.class);

		return new VersionAdapterLoader(Collections.unmodifiableMap(loadedAdapters));
	}

	private static <T extends VersionAdapter> void requireVersionAdapter(Map<Class<?>, VersionAdapter> adapters, ClassLoader classLoader, Class<T> adapterClass) {
		for (T adapter : ServiceLoader.load(adapterClass, classLoader)) {
			if (adapter.isCompatible()) {
				adapters.put(adapterClass, adapter);
				return;
			}
		}
		throw new RuntimeException("Unable to find version adapter for class " + adapterClass.getCanonicalName());
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
