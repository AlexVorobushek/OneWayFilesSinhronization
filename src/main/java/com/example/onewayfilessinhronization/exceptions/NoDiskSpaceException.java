package com.example.onewayfilessinhronization.exceptions;

public class NoDiskSpaceException extends CanNotPushException{
    public NoDiskSpaceException() {
        super("there is no space on the disk where you are trying to push the data");
    }
}
