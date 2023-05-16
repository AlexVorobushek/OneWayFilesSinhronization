package com.example.onewayfilessinhronization.exceptions;

public class NotCorrectLinkException extends CanNotPushException {
    public NotCorrectLinkException(String link) {
        super("link "+link+" is not correct");
    }
}
