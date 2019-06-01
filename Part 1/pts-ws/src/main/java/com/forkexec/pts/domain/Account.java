package com.forkexec.pts.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Account{
	private final String _email;
	private AtomicInteger _points;

	private boolean isValidEmail(String email){
		String[] parts=email.split("@");
		if(parts.length !=2)
			return false;
		for(String part : parts)
			for (String subpart : part.split("\\.",-1))
				if (subpart.isEmpty())
					return false;
		return true;
	}

	public Account(String email,int points) throws InvalidEmailFault_Exception{
		if(!isValidEmail(email))
			throw new InvalidEmailFault_Exception("given email is not valid");
		_email=email;
		_points=new AtomicInteger(points);
	}

	public int addPoints(int points) throws InvalidPointsFault_Exception{
		if(points <=0)
			throw new InvalidPointsFault_Exception("can only add a positive ammount of points");
		if((long)_points.get() + (long)points > Integer.MAX_VALUE)
			throw new InvalidPointsFault_Exception("account point limit exceeded- too many points");
		return _points.addAndGet(points);
	}

	public int spendPoints(int points) throws InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception{
		if(points<=0)
			throw new InvalidPointsFault_Exception("can only spend a positive ammount of points");
		if(_points.get()<points)
			throw new NotEnoughBalanceFault_Exception("cannot spend more points than what the user has");
		return _points.addAndGet(-points);
	}

	public int pointsBalance(){
		return _points.get();
	}
}