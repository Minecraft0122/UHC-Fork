package com.gmail.val59000mc.versionadapters;

/**
 * An adapter for some operation whose implementation differs depending on the version
 * of runtime libraries, for example the Minecraft version of the server platform.
 */
public interface VersionAdapter {

	/**
	 * Checks whether this version adapter is compatible with the current runtime.
	 *
	 * @return {@code true} if compatible, {@code false} otherwise.
	 */
	boolean isCompatible();

}
