package com.gmail.val59000mc.utils.snapshot;

import org.bukkit.inventory.ItemStack;

public class ItemStackSnapshot extends AbstractSnapshot<ItemStack> {

	private ItemStackSnapshot(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	protected ItemStack copyObject(ItemStack itemStack) {
		return itemStack.clone();
	}

	public static Snapshot<ItemStack> of(ItemStack itemStack) {
		return new ItemStackSnapshot(itemStack);
	}

}
