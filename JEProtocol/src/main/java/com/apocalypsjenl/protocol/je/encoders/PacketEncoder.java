package com.apocalypsjenl.protocol.je.encoders;

import com.apocalypsjenl.protocol.je.exceptions.JEPacketWriteException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketEncoder extends DataOutputStream {

    public PacketEncoder(OutputStream out) {
        super(out);
    }

    public void writeVarInt(int varInt) throws JEPacketWriteException {
        try {
            while(true) {
                int writeByte = varInt & 0b1111111;
                varInt >>>= 7;

                if(varInt != 0) writeByte |= 0b100000000;
                writeByte(writeByte);

                if(varInt == 0) break;
            }
        } catch (IOException e) {
            throw new JEPacketWriteException(e);
        }
    }

    public void writeString(String value) throws JEPacketWriteException {
        try {
            byte[] stringBytes = value.getBytes("UTF-8");
            writeVarInt(stringBytes.length);
            write(stringBytes);
        } catch (IOException e) {
            throw new JEPacketWriteException(e);
        }
    }
}
