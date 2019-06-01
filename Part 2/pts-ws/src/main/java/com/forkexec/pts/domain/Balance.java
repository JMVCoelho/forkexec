package com.forkexec.pts.domain;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Balance {
	private AtomicInteger points;
	private AtomicLong tag;

	public Balance(long tag, int points) {
		this.points = new AtomicInteger(points);
		this.tag = new AtomicLong(tag);
	}

	public Balance(final Balance balance) {
		this.points = new AtomicInteger(balance.getPoints());
		this.tag = new AtomicLong(balance.getTag());
	}

	public void setTag(long tag) {
		this.tag.set(tag);
	}

	public void setPoints(int points) {
		this.points.set(points);
	}

	public int getPoints() {
		return this.points.get();
	}

	public long getTag() {
		return this.tag.get();
	}
}