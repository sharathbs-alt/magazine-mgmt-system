package com.cts.digimagazine.dao;

import com.cts.digimagazine.model.Subscription;

public interface SubscriptionDAO {
	public abstract void addSubscritpion(Subscription subscription);
	public abstract void viewSubscritpion(Subscription subscription);
	public abstract void updateSubscritpion(Subscription subscription);
	public abstract void deleteSubscritpion(Subscription subscription);
	
}
