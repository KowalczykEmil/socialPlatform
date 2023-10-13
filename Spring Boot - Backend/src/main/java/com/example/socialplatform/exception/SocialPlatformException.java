package com.example.socialplatform.exception;

public class SocialPlatformException extends RuntimeException {
    public SocialPlatformException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SocialPlatformException(String exMessage) {
        super(exMessage);
    }
}
