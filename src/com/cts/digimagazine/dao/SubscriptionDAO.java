package com.cts.digimagazine.dao;

import java.sql.SQLException;

import com.cts.digimagazine.model.Subscription;

public interface SubscriptionDAO {
	public abstract void addSubscription(Subscription subscription) throws SQLException;
	public abstract void viewSubscription(Subscription subscription) throws SQLException;
	public abstract void updateSubscription(Subscription subscription) throws SQLException;
	public abstract void deleteSubscription(Subscription subscription) throws SQLException;
	public abstract Subscription findSubscriptionById(int id)  throws SQLException;
	public abstract void updateSubscriptionStatus();
}
