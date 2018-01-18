package com.apocalypsjenl.protocol.je.encoders;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketEncoder extends DataOutputStream {

    public PacketEncoder(OutputStream out) {
        super(out);
    }

    public void writeVarInt(int varInt) {
        try {
            while(true) {
                int writeByte = varInt & 0b1111111;
                varInt >>>= 7;

                if(varInt != 0) writeByte |= 0b100000000;
                writeByte(writeByte);

                if(varInt == 0) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeString(String value) {
        try {
            byte[] stringBytes = value.getBytes("UTF-8");
            writeVarInt(stringBytes.length);
            write(stringBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
