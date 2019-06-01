package com.forkexec.pts.ws.cli;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CashedBalance {
	private AtomicInteger points;
	private AtomicLong tag;
	private AtomicLong iteration;

	public CashedBalance(long tag, int points, final long iteration) {
		this.points = new AtomicInteger(points);
		this.tag = new AtomicLong(tag);
		this.iteration = new AtomicLong(iteration);
	}

	public void setTag(long tag) {
		this.tag.set(tag);
	}

	public void setPoints(int points) {
		this.points.set(points);
	}

	public void setIteration(long iteration) {
		this.iteration.set(iteration);
	}

	public int getPoints() {
		return this.points.get();
	}

	public long getTag() {
		return this.tag.get();
	}

	public long getIteration() {
		return this.iteration.get();
	}
}