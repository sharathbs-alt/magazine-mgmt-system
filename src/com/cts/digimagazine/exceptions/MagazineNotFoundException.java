package com.cts.digimagazine.exceptions;

public class MagazineNotFoundException extends RuntimeException {
    public MagazineNotFoundException(String message) {
        super(message);
    }
}