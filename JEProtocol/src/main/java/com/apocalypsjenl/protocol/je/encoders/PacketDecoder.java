package com.apocalypsjenl.protocol.je.encoders;

import com.apocalypsjenl.protocol.je.exceptions.JEPacketReadException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketDecoder extends DataInputStream {

    public PacketDecoder(InputStream inputStream) {
        super(inputStream);
    }

    public int readVarInt() throws JEPacketReadException {
        int varInt = 0;
        int byteShift = 0;
        try {
            while(true) {
                byte readByte = readByte();
                varInt |= (readByte & 0b1111111) << (byteShift++ * 7);

                if(byteShift > 5) throw new JEPacketReadException("Invalid VarInt");

                System.out.println(byteShift);
                System.out.println(readByte & 0b10000000);
                if((readByte & 0b10000000) != 0b10000000) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return varInt;
    }

    public String readString() throws JEPacketReadException {
        try {
            int length = readVarInt();
            byte[] stringBytes = new byte[length];
            readFully(stringBytes);
            return new String(stringBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new JEPacketReadException("Failed to read String");
        }
    }
}
