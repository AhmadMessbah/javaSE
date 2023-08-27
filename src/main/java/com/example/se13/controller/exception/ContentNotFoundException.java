package com.example.se13.controller.exception;

public class ContentNotFoundException extends Exception{
    @Override
    public String getMessage() {
        return "Content Not Found";
    }
}
