package com.gmail.val59000mc.utils.snapshot;

/**
 * A snapshot object is an immutable view of an underlying live object,
 * which may be turned into live objects either via copying or via reanimation.
 */
public interface Snapshot<T> {

	/**
	 * Makes a copy of the underlying live object.
	 *
	 * @return the copy
	 */
	T makeCopy();

	/**
	 * Returns the underlying live object as-is, which invalidates this snapshot object.
	 *
	 * @return the live object
	 */
	T reanimate();

	/**
	 * Tells wether this snapshot object has been reanimated before (and as such, has become invalid).
	 *
	 * @return {@code true} if it has been reanimated before, {@code false} otherwise
	 */
	boolean hasBeenReanimated();

}
