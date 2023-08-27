package com.example.se13.controller.exception;

public class DuplicateUserNameException extends Exception{
    @Override
    public String getMessage() {
        return "Duplicate User Name";
    }
}
