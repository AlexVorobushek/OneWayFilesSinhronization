package com.example.onewayfilessinhronization.exceptions.VCExceptions.VCNotCorrectLinkExceptions;


public class NoSuchCommit extends NotCorrectVCLinkException {
    public NoSuchCommit(String link) {
        super(link);
    }
}
