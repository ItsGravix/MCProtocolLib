package com.apocalypsjenl.protocol.je.exceptions;

public class JEDisconnectException extends Exception {

    public JEDisconnectException(String message) {

        super(message);
    }

    public JEDisconnectException(Throwable cause) {
        super(cause);
    }
}
