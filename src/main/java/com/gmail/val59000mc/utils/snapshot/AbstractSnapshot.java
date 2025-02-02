package com.gmail.val59000mc.utils.snapshot;

/**
 * Abstract base class for creating {@link Snapshot} objects.
 * <p>
 * Creating a snapshot ordinarily involves creating a copy, in order to make sure
 * that the snapshot object behaves like a snapshot rather than a live object.
 * Currently, there is no way to create an AbstractSnapshot without making a copy.
 */
public abstract class AbstractSnapshot<T> implements Snapshot<T> {

	private T snapshotObject;

	protected AbstractSnapshot(T object) {
		this.snapshotObject = copyObject(object);
	}

	protected abstract T copyObject(T object);

	@Override
	public T makeCopy() {
		if (hasBeenReanimated()) {
			throw new IllegalStateException("This snapshot has been reanimated, copying now may cause bugs");
		}
		return copyObject(snapshotObject);
	}

	@Override
	public T reanimate() {
		if (hasBeenReanimated()) {
			throw new IllegalStateException("This snapshot has already been reanimated");
		}
		final T storedSnapshot = snapshotObject;
		snapshotObject = null;
		return storedSnapshot;
	}

	@Override
	public boolean hasBeenReanimated() {
		return snapshotObject == null;
	}

}
