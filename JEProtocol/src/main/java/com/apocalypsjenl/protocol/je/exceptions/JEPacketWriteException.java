package com.apocalypsjenl.protocol.je.exceptions;

public class JEPacketWriteException extends Exception {

    public JEPacketWriteException(Throwable cause) {
        super(cause);
    }

    public JEPacketWriteException(String message) {

        super(message);
    }
}
