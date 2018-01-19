package com.apocalypsjenl.protocol.je.exceptions;

public class JEPacketReadException extends Exception {

    public JEPacketReadException(String message) {
        super(message);
    }

    public JEPacketReadException(Throwable cause) {
        super(cause);
    }
}
