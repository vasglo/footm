package com.example.futm.exception;

public class PlayerNotFoundException extends RuntimeException {
    private static final String MASSAGE = "This player doesnt exist";
    public PlayerNotFoundException() {
        super(MASSAGE);
    }
}
