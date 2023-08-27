package com.example.se13.controller.exception;

public class DisabledUserException extends Exception {
    @Override
    public String getMessage() {
        return "User is Disabled";
    }
}
