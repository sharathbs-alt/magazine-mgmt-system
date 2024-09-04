package com.cts.digimagazine.exceptions;

public class SubscriptionNotFoundException extends RuntimeException{
	public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
