package com.forkexec.pts.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Points
 * <p>
 * A points server.
 */
public class Points {

  /**
   * Constant representing the default initial balance for every new client
   */
  private static final int DEFAULT_INITIAL_BALANCE = 100;
  /**
   * Global with the current value for the initial balance of every new client
   */
  private final AtomicInteger initialBalance = new AtomicInteger(DEFAULT_INITIAL_BALANCE);

  private Map<String,Account> _accounts = new ConcurrentHashMap<>();

  // Singleton -------------------------------------------------------------

  /**
   * Private constructor prevents instantiation from other classes.
   */
  private Points() { }

  /**
   * SingletonHolder is loaded on the first execution of Singleton.getInstance()
   * or the first access to SingletonHolder.INSTANCE, not before.
   */
  private static class SingletonHolder {
    private static final Points INSTANCE = new Points();
  }

  public static synchronized Points getInstance() {
    return SingletonHolder.INSTANCE;
  }

  private boolean UserExists(final String userEmail){
    if(_accounts.get(userEmail) != null){
      return true;
    }
    return false;
  }

  public void activateUser(final String userEmail) throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
    if(UserExists(userEmail))
    	throw new EmailAlreadyExistsFault_Exception("A user with the given email already exists");
    _accounts.put( userEmail, new Account( userEmail, initialBalance.get() ) );
  }

  public int pointsBalance(final String userEmail) throws InvalidEmailFault_Exception {
  	if(!UserExists(userEmail))
  		throw new InvalidEmailFault_Exception("There is no user with the given email");
    return _accounts.get(userEmail).pointsBalance();
  }

  public int addPoints(final String userEmail, final int pointsToAdd)
    throws InvalidEmailFault_Exception, InvalidPointsFault_Exception {
    	if(!UserExists(userEmail))
  			throw new InvalidEmailFault_Exception("There is no user with the given email");
      return _accounts.get(userEmail).addPoints(pointsToAdd);
  }

  public int spendPoints(final String userEmail, final int pointsToSpend)
    throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {
      if(!UserExists(userEmail))
  			throw new InvalidEmailFault_Exception("There is no user with the given email");
      return _accounts.get(userEmail).spendPoints(pointsToSpend);
  }

  public void reset() {
		_accounts.clear();
		initialBalance.set(DEFAULT_INITIAL_BALANCE);
	}

	public void setInitialBalance(int startPoints) throws BadInitFault_Exception {
			if (startPoints<0)
				throw new BadInitFault_Exception("starting points cannot be negative");
    	initialBalance.set(startPoints);
    }

}
