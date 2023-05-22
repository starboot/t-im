package cn.starboot.tim.common.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class SetWithLimitedCapacity<E> extends HashSet<E> {

	private final AtomicInteger capacity;

	public SetWithLimitedCapacity(int initialCapacity) {
		super(initialCapacity);
		this.capacity = new AtomicInteger(initialCapacity);
	}

	@Override
	public boolean add(E e) {
		if (this.capacity.intValue() == 0) {
			return false;
		}
		// 容量减去1
		this.capacity.getAndDecrement();
		return super.add(e);
	}

	@Override
	public boolean remove(Object o) {
		// 容量增加1
		this.capacity.getAndIncrement();
		return super.remove(o);
	}

	@Override
	public int size() {
		return capacity.get();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (this.capacity.get() < c.size()) {
			return false;
		}
		return super.addAll(c);
	}
}
