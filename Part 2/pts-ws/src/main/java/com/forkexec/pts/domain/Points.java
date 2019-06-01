package com.forkexec.pts.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.forkexec.pts.domain.Balance;

/**
 * Points
 * <p>
 * A points server.
 */
public class Points {

	/**
	 * Constant representing the default initial balance for every new client
	 */
	private static final int DEFAULT_INITIAL_POINTS = 100;

	/**
	 * Global with the current value for the initial balance of every new client
	 */
	private final AtomicInteger initialPoints = new AtomicInteger(DEFAULT_INITIAL_POINTS);
	private final long INITIAL_TAG = 0;

	/**
	 * Accounts. Associates the user's email with a points balance. The collection
	 * uses a hash table supporting full concurrency of retrievals and updates. Each
	 * item is an AtomicInteger, a lock-free thread-safe single variable. This means
	 * that multiple threads can update this variable concurrently with correct
	 * synchronization.
	 */
	private Map<String, Balance> accounts = new ConcurrentHashMap<>();

	// Singleton -------------------------------------------------------------

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		private static final Points INSTANCE = new Points();
	}

	/**
	 * Retrieve single instance of class. Only method where 'synchronized' is used.
	 */
	public static synchronized Points getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * Private constructor prevents instantiation from other classes.
	 */
	private Points() {
		// initialization with default values
		reset();
	}

	/**
	 * Reset accounts. Synchronized is not required because we are using concurrent
	 * map and atomic integer.
	 */
	public void reset() {
		// clear current hash map
		accounts.clear();
		// set initial balance to default
		initialPoints.set(DEFAULT_INITIAL_POINTS);
	}

	/**
	 * Set initial Reset accounts. Synchronized is not required because we are using
	 * atomic integer.
	 */
	public void setInitialPoints(int newInitialPoints) {
		initialPoints.set(newInitialPoints);
	}

	public Balance read(final String accountId) {
		if (!accounts.containsKey(accountId))
			accounts.put(accountId, new Balance(INITIAL_TAG, initialPoints.get()));
		printAccounts();
		return accounts.get(accountId);
	}

	public void write(final String accountId, final Balance balance) {
		if (accounts.containsKey(accountId)) {
			if (balance.getTag() < accounts.get(accountId).getTag()) {
				return;
			}
		}
		accounts.put(accountId, new Balance(balance));
		printAccounts();
	}

	public boolean doesUserExist(String accountId) {
		if (accounts.containsKey(accountId)) {
			return true;
		}
		return false;
	}

	public void printAccounts() {
		for (String accountId : accounts.keySet()) {
			Balance b = accounts.get(accountId);
			System.out.println(accountId + " | " + b.getPoints() + " | " + b.getTag());
		}
	}

	public void softReset() {
		initialPoints.set(DEFAULT_INITIAL_POINTS);
		for (String accountId : accounts.keySet()) {
			Balance b = accounts.get(accountId);
			b.setPoints(DEFAULT_INITIAL_POINTS);
			b.setTag(b.getTag() + 1);
		}
		printAccounts();
	}

}
