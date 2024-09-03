package com.cts.digimagazine.dao;

import com.cts.digimagazine.model.Subscription;

public interface SubscriptionDAO {
	public abstract void addSubscription(Subscription subscription);
	public abstract void viewSubscription(Subscription subscription);
	public abstract void updateSubscription(Subscription subscription);
	public abstract void deleteSubscription(Subscription subscription);
	public abstract Subscription findSubscriptionById(int id);
}
