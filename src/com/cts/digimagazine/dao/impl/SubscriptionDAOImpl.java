package com.cts.digimagazine.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.cts.digimagazine.dao.SubscriptionDAO;
import com.cts.digimagazine.model.Subscription;

public class SubscriptionDAOImpl implements SubscriptionDAO {

    private List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
        System.out.println("Subscription added successfully!");
    }

    @Override
    public void viewSubscription(Subscription subscription) {
        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions found.");
            return;
        }
        for (Subscription sub : subscriptions) {
            System.out.println(sub);
        }
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        for (Subscription sub : subscriptions) {
            if (sub.getSubscriptionId() == subscription.getSubscriptionId()) {
                sub.setUserId(subscription.getUserId());
                sub.setMagazineId(subscription.getMagazineId());
                sub.setSubscriptionDate(subscription.getSubscriptionDate());
                sub.setExpiryDate(subscription.getExpiryDate());
                sub.setStatus(subscription.getStatus());
                System.out.println("Subscription updated successfully!");
                return;
            }
        }
        System.out.println("Subscription with ID " + subscription.getSubscriptionId() + " not found.");
    }

    @Override
    public void deleteSubscription(Subscription subscription) {
        subscriptions.removeIf(sub -> sub.getSubscriptionId() == subscription.getSubscriptionId());
        System.out.println("Subscription deleted successfully!");
    }

    @Override
    public Subscription findSubscriptionById(int id) {
        for (Subscription sub : subscriptions) {
            if (sub.getSubscriptionId() == id) {
                return sub;
            }
        }
        return null;
    }
}